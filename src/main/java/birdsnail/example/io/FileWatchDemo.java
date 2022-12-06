package birdsnail.example.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件监听，可以监听我们感兴趣的事件类型：文件创建，文件删除 或 文件修改
 * <p><ol><li>创建一个WatchService实例</li>
 * <li>为目录注册这个监听实例并且指定要监听的事件</li>
 * <li>循环获取WatchKey, key中绑定了监听事件信息，可以在另一个线程中处理事件</li></ol></p>
 * @date 2022-12-05 11:42
 */
public class FileWatchDemo {

    public static void main(String[] args) throws IOException {
        // 还是模拟命令行的传参
        args = new String[]{"-r", "E:\\temp"};

        // 参数解析
        if (args.length == 0 || args.length > 2)
            usage();
        boolean recursive = false;  // 递归处理
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                usage();
            recursive = true;
            dirArg++;
        }

        // 注册目录和处理过程的事件
        Path dir = Paths.get(args[dirArg]);
        new WatchDir(dir, recursive).processEvents();
    }

    /**
     * 命令行的使用方法
     */
    static void usage() {
        System.err.println("usage: java WatchDir [-r] dir");
        System.exit(-1);
    }

    /**
     * 监听目录变化
     */
    public static class WatchDir {
        private final WatchService watcher;
        private final Map<WatchKey, Path> keys;
        private final boolean recursive;
        private boolean trace = false;

        /**
         * 创建watch服务，并注册目录
         */
        WatchDir(Path dir, boolean recursive) throws IOException {
            // 1. 获取监听实例
            this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<WatchKey, Path>();
            this.recursive = recursive;

            if (recursive) {
                System.out.format("Scanning %s ...\n", dir);
                registerAll(dir);
                System.out.println("Done.");
            } else {
                register(dir);
            }

            // 是否启用跟踪日志信息
            this.trace = true;
        }

        @SuppressWarnings("unchecked")
        static <T> WatchEvent<T> cast(WatchEvent<?> event) {
            return (WatchEvent<T>) event;
        }

        /**
         * 在服务中注册目录
         */
        private void register(Path dir) throws IOException {
            // 2. 为目录注册监听服务
            WatchKey key = dir.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            // 日志打印跟踪，是新注册还是更新
            if (trace) {
                Path prev = keys.get(key);
                if (prev == null) {
                    System.out.format("register: %s\n", dir);
                } else {
                    if (!dir.equals(prev)) {
                        System.out.format("update: %s -> %s\n", prev, dir);
                    }
                }
            }
            keys.put(key, dir);
        }

        /**
         * 在给定的目录中注册所有的子目录
         */
        private void registerAll(final Path start) throws IOException {
            // 注册目录和子目录
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    register(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }


        /**
         * 处理队列中的所有观察者的事件
         */
        void processEvents() {
            for (; ; ) {
                // 等待事件被触发
                WatchKey key;
                try {
                    // 3. 获取key并处理监听事件
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }

                // 这里的key 和 path 的关联 是咱们自己设计的程序关联，不是api的强制要求
                // 这里方便拿到path对象
                Path dir = keys.get(key);
                if (dir == null) {
                    System.err.println("WatchKey 不认可!!");
                    continue;
                }

                // 获取当次key的所有事件
                for (WatchEvent<?> event : key.pollEvents()) {
                    // 返回该事件的事件类型
                    WatchEvent.Kind<?> kind = event.kind();

                    // 提供如何处理 逸出事件的列子（难道说是没有被注册的事件 就称为溢出事件吗？）
                    // 经过测试，如果是没有注册事件的话， key.pollEvents() 是获取不到事件的
                    // 在后面的学习中，我找到了答案，原来表示该事件可能已丢失或被丢弃
                    // 但是 watcher.take(); 会被触发
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    // event 对象是一个 sun.nio.fs.AbstractWatchKey.Event 对象，实现了WatchEvent，所以强转成WatchEvent
                    WatchEvent<Path> ev = cast(event);
                    Path name = ev.context(); // 获得引起该事件目标上下文条目：也就是文件名的 path对象
                    Path child = dir.resolve(name); // 使用该watchKey对应的注册目录，就能转换成绝对路径的文件对象

                    // 打印事件
                    System.out.format("%s: %s\n", event.kind().name(), child);

                    // 如果是递归模式，且 目录是创建事件，那么递归的注册所有子目录
                    if (recursive && (kind == StandardWatchEventKinds.ENTRY_CREATE)) {
                        try {
                            if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                                registerAll(child);
                            }
                        } catch (IOException x) {
                            // ignore to keep sample readable
                        }
                    }
                }

                // 如果目录不再访问，复位key和删除key
                boolean valid = key.reset();
                // 如果复位无效就删除该key
                if (!valid) {
                    keys.remove(key);

                    // 当次所有目录都无法访问的时候，就退出
                    if (keys.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

}
