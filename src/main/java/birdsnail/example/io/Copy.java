package birdsnail.example.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * 以递归的方式复制整个目录以及目录下的文件
 */
public class Copy {
    /**
     * Returns {@code true} if okay to overwrite a  file ("cp -i")
     * 当 prompt = true 的时候，如果目标文件又存在，就需要用户手动选择是否覆盖
     */
    static boolean okayToOverwrite(Path file) {
        String answer = System.console().readLine("overwrite %s (yes/no)? ", file);
        return (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"));
    }

    /**
     * 如果 prompt = true，则源文件复制到目标位置。
     *
     * @param source   源文件
     * @param target   目标文件
     * @param prompt   是否提示用户，如果文件存在，是否覆盖
     * @param preserve 保持文件属性，且文件如果存在则覆盖
     */
    static void copyFile(Path source, Path target, boolean prompt, boolean preserve) {
        CopyOption[] options = (preserve) ?
                new CopyOption[]{COPY_ATTRIBUTES, REPLACE_EXISTING} : //   将属性复制到新文件。  替换现有的文件，如果它存在。
                new CopyOption[]{REPLACE_EXISTING};
        // 如果prompt=true，目标文件又存在,那么希望用户手动输入：该文件是否覆盖
        if (!prompt || Files.notExists(target) || okayToOverwrite(target)) {
            try {
                Files.copy(source, target, options);
            } catch (IOException x) {
                System.err.format("Unable to copy: %s: %s%n", source, x);
            }
        }
    }

    /**
     * {@code FileVisitor} ("cp -r") 递归拷贝文件树
     */
    static class TreeCopier implements FileVisitor<Path> {
        private final Path source;
        private final Path target;
        private final boolean prompt;
        private final boolean preserve;

        TreeCopier(Path source, Path target, boolean prompt, boolean preserve) {
            this.source = source;
            this.target = target;
            this.prompt = prompt;
            this.preserve = preserve;
        }

        /**
         * 访问目录之前
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            // before visiting entries in a directory we copy the directory
            // (okay if directory already exists).
            CopyOption[] options = (preserve) ?
                    new CopyOption[]{COPY_ATTRIBUTES} : new CopyOption[0];
            // 1. 在原目录中创建要访问的目录的相对路径
            // 2. 然后用目标目录链接相对路径。得到目标目录的地址
            Path newdir = target.resolve(source.relativize(dir));
            try {
                Files.copy(dir, newdir, options);
            } catch (FileAlreadyExistsException x) {
                // ignore
            } catch (IOException x) {
                System.err.format("Unable to create: %s: %s%n", newdir, x);
                return SKIP_SUBTREE; // 该目录子目录包括子文件则跳过
            }
            return CONTINUE; // 子目录包括子文件继续访问
        }

        /**
         * 访问目录之后
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            // 目录复制完成后，如果选择了 保持属性，需要修复 最后修改时间。
            // 因为先copy目录，然后再copy文件，就在修改这个目录了。所以在访问目录完成之后，再把时间修复过来
            if (exc == null && preserve) {
                Path newdir = target.resolve(source.relativize(dir));
                try {
                    FileTime time = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newdir, time);
                } catch (IOException x) {
                    System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
                }
            }
            return CONTINUE;
        }

        /**
         * 访问一个文件
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            copyFile(file, target.resolve(source.relativize(file)),
                    prompt, preserve);
            return CONTINUE;
        }


        /**
         * 访问文件失败
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("cycle detected: " + file);
            } else {
                System.err.format("Unable to copy: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }
    }

    /**
     * 如果参数不对，则打印错误信息，并退出系统
     */
    static void usage() {
        System.err.println("java Copy [-ip] source... target");
        System.err.println("java Copy -r [-ip] source-dir... target");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
        // 在这里 我们用ide运行，所以模拟了在控制台的输入
        // 但是 -i 选项（如果目标文件存在，提示用户是否需要覆盖） 就不能使用了，因为获取不到控制台流
        args = new String[]{"-rp", "E:\\edmweb\\file\\rcmd_data\\rcmd_1", "E:\\edmwebTest"};
        boolean recursive = false;
        boolean prompt = false;
        boolean preserve = false;

        // 处理可选操作参数
        int argi = 0;
        while (argi < args.length) {
            String arg = args[argi];
            if (!arg.startsWith("-"))
                break;
            if (arg.length() < 2)
                usage();
            for (int i = 1; i < arg.length(); i++) {
                char c = arg.charAt(i);
                switch (c) {
                    case 'r':  // 是否递归copy文件目录的所有后代
                        recursive = true;
                        break;
                    case 'i':
                        prompt = true;
                        break;
                    case 'p':
                        preserve = true;
                        break;
                    default:
                        usage();
                }
            }
            argi++;
        }

        // 去掉可选参数
        int remaining = args.length - argi;
        if (remaining < 2)
            usage();
        // 再减去最后的目标路径，得到1 - n 个源
        Path[] source = new Path[remaining - 1];
        int i = 0;
        while (remaining > 1) {
            source[i++] = Paths.get(args[argi++]);
            remaining--;
        }
        Path target = Paths.get(args[argi]);

        // 检查目标文件是否是一个目录
        boolean isDir = Files.isDirectory(target);

        // 复制源文件/目录 到目标目录
        for (i = 0; i < source.length; i++) {
            Path dest = (isDir) ? target.resolve(source[i].getFileName()) : target;
            // 是否递归copy该目录/文件的所有后代
            // 这里需要注意的是，由于使用了Files.walkFileTree 结合 FileVisitor 来实现。所以不管源是一个目录还是一个文件，
            // 框架已经处理好了，如果是文件就直接调用 visitFile 方法。如果是目录也会调用相应的方法
            // 所以这里是先判断是否需要递归。 这里编写代码流程感觉还是有一点不太完美。
            if (recursive) {
                // 设置处理链接文件的模式：copy 链接的内容而不是链接文件
                EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                // 自定义对文件目录中的目录或则文件设置操作
                TreeCopier tc = new TreeCopier(source[i], dest, prompt, preserve);
                Files.walkFileTree(source[i], opts, Integer.MAX_VALUE, tc);
            } else {
                // 如果不递归copy，那么就只能copy文件。
                if (Files.isDirectory(source[i])) {
                    System.err.format("%s: is a directory%n", source[i]);
                    continue;
                }
                copyFile(source[i], dest, prompt, preserve);
            }
        }
    }
}