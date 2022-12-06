package birdsnail.example.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Find {
    /**
     * A {@code FileVisitor} that finds
     * 查找匹配的所有文件
     */
    public static class Finder
            extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private int numMatches = 0;

        Finder(String pattern) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern); // 使用 glob 模式创建匹配器
        }

        /**
         * 使用比较器进行匹配 文件或则目录的名称
         */
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                System.out.println(file);
            }
        }

        /**
         * 打印匹配到的总个数
         */
        void done() {
            System.out.println("Matched: "
                    + numMatches);
        }

        /**
         * 在访问文件的时候，调用模式匹配文件
         */
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs) {
            find(file);
            return FileVisitResult.CONTINUE;
        }

        /**
         * 在访问目录前调用调用模式匹配文件
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                                                 BasicFileAttributes attrs) {
            find(dir);
            return FileVisitResult.CONTINUE;
        }

        /**
         * 访问文件失败时 打印错误信息
         */
        @Override
        public FileVisitResult visitFileFailed(Path file,
                                               IOException exc) {
            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * 使用方式
     */
    static void usage() {
        System.err.println("java Find <path>" +
                " -name \"<glob_pattern>\"");
        System.exit(-1);
    }

    public static void main(String[] args)
            throws IOException {
        // 我们在这里模拟命令行方式进行调试
        // 该命令含义：在d盘中查找所有以.xml 结尾的文件
        args = new String[]{"D:\\", "-name", "*.{xml}"};
        // 支持 在 指定路径 按-name 匹配的方式
        if (args.length < 3 || !args[1].equals("-name"))
            usage();

        Path startingDir = Paths.get(args[0]);
        String pattern = args[2];

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}