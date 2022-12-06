package birdsnail.example.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * 遍历文件树
 * <p>要遍历一个文件树，首先要实现{@link java.nio.file.FileVisitor}，如果我们并不想要全部的四种方法，可以扩展{@linkplain java.nio.file.SimpleFileVisitor}</p>
 *
 * @see Copy
 * @see Find
 * @author huadong.yang
 * @date 2022-12-05 10:24
 */
public class WalkFileTree {

    public static void main(String[] args) {
        pathMatcher();
    }

    /**
     * 遍历
     */
    public static void walk() throws IOException {
        // 1. 只需要一个path和一个FileVisitor实例
        Path startingDir = Paths.get("g:/");
        FileVisitor<Path> fileVisitor = new WalkFileTree.PrintFiles();
        Files.walkFileTree(startingDir, fileVisitor);

        // 2. 指定额外的参数：遍历的最大深度，遍历行为枚举
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(startingDir, opts, Integer.MAX_VALUE, fileVisitor);
    }

    /**
     * 使用匹配器进行匹配
     */
    public static void pathMatcher() {
        String pattern = "*.{java,xml}";
        // 创建了一个glob语法的匹配器
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        Path targetPath = Paths.get("d:/server.xml");
        if (matcher.matches(targetPath.getFileName())) {
            System.out.println(targetPath);
        }else {
            System.out.println("not found");
        }
    }


    /**
     * 实现打印文件树中所有条目
     * <p>注意事项：
     * <ol><li>递归删除时，要先删除目录下的文件再删除目录，因此应该在{@code postVisitorDirectory}中删除目录</li>
     * <li>递归复制时，如果想保留源目录的属性，则应该在{@code postVisitorDirectory}中同步源目录的属性到新目录</li>
     * <li>在文件树中查找时如果像找出符合条件的文件或者目录，除了在{@code visitFile}中进行比较文件，还需要{@code preVisitorDirectory}或者{@code postVisitorDirectory}进行比较目录操作</li>
     * <li>默认情况下是不开启的符号链接的FOLLOW_LINKS，visitFile访问到的是链接实际对应的文件。当允许符号链接时，要考虑循环遍历的场景，循环发生时会在{@code visitFileFailed}中抛出FileSystemLoopException</li></ol></p>
     */
    public static class PrintFiles extends SimpleFileVisitor<Path> {
        // 打印有关信息
        // 打印文件的类型
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attr) {
            if (attr.isSymbolicLink()) {
                System.out.format("Symbolic link: %s ", file);
            } else if (attr.isRegularFile()) {
                System.out.format("Regular（常规） file: %s ", file);
            } else {
                System.out.format("Other: %s ", file);
            }
            System.out.println("(" + attr.size() + "bytes)");
            return FileVisitResult.CONTINUE;
        }

        // 打印所访问的每个目录
        @Override
        public FileVisitResult postVisitDirectory(Path dir,
                                                  IOException exc) {
            System.out.format("Directory: %s%n", dir);
            return FileVisitResult.CONTINUE;
        }

        // 如果发生错误应该覆盖此方法，让用户知道
        // 如果不用覆盖此访问将抛出IO异常
        @Override
        public FileVisitResult visitFileFailed(Path file,
                                               IOException exc) {
            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }
}
