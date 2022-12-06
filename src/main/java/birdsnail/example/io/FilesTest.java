package birdsnail.example.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilesTest {


    public static void main(String[] args) throws IOException {
        // walkAndWrite();

        // fileCheckTest();
        // operationFileTest();
        // fileMeteDataTest();
        // readWriteTest();
        // positionFileTest();
        // listRootDirectory();
        // createDirectory();
        // listDirectory();
        // listDirectoryWithGlobbing();
        // otherMethod();
        System.out.println(findUsageNum('a', "src\\main\\resources\\path\\d1\\f1.txt"));
    }


    private static void walkAndWrite() throws IOException {
        Path path = Paths.get("");
        System.out.println(path);
        Set<String> fsf = Files.walk(path)
                .filter(f -> Files.isRegularFile(f) && f.toString().endsWith("Mapper.xml") && !f.endsWith("CustomerMapper.xml"))
                .parallel().filter(FilesTest::isContains)
                .map(Path::toString)
                .collect(Collectors.toSet());

        System.out.println("结果数量：" + fsf.size());
        Path target = Path.of("result.txt");
        Files.write(target, fsf);
    }

    private static boolean isContains(Path file) {
        try {
            return Files.readAllLines(file).stream().map(String::strip).anyMatch(it -> it.contains("c"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 文件检查
     */
    public static void fileCheckTest() throws IOException {
        // 1. 检查是否存在
        System.out.println("==============检查是否存在============");
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        System.out.println("path1是否存在：" + Files.exists(path1));

        // 2. 检查是否有访问权限
        System.out.println("===========检查是否有访问权限============");
        System.out.println("path1是否为文件：" + Files.isRegularFile(path1));
        Path path2 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1.txt");
        System.out.println("path2是否为文件：" + Files.isRegularFile(path2));
        System.out.println("path1是否可读：" + Files.isReadable(path1));
        System.out.println("path1是否可写：" + Files.isWritable(path1));

        // 3. 检查是否为相同文件
        System.out.println("============检查是否为相同文件==========");
        Path path3 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1.txt");
        Path path4 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d2\\f2.txt");
        System.out.println("p2 p3 是否为同一文件：" + Files.isSameFile(path2, path3));
        System.out.println("p3 p4 是否为同一文件：" + Files.isSameFile(path3, path4));
    }

    /**
     * 操作文件：删除，复制，移动
     */
    public static void operationFileTest() throws IOException {
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f2.txt");
        // Files.delete(path1);
        Files.deleteIfExists(path1); // 如果是目录，要求目录不为空

        // 复制文件
        Path path2 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1.txt");
        Files.copy(path2, path1, StandardCopyOption.REPLACE_EXISTING);
        List<String> lines = Files.readAllLines(path1);
        System.out.println("copy path2 to path1: " + lines);
        Files.deleteIfExists(path1);

        // 复制目录，不会复制目录下的文件
        Path path3 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1");
        Path path4 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path2");
        Files.deleteIfExists(path4);
        Files.copy(path3, path4);

        // 移动文件
        Path path5 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1_1.txt");
        Path path6 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d2");
        Path newDir = path6.resolve(path5.getFileName());
        Files.move(path5, newDir);
        Files.move(newDir, path5, StandardCopyOption.ATOMIC_MOVE); // 原子移动
    }

    /**
     * 文件元数据访问：大小，创建时间，是否为目录/文件，所有者，上次修改时间
     */
    public static void fileMeteDataTest() throws IOException {
        // 1. 直接使用Files方法
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1.txt");
        System.out.println("文件字节大小：" + Files.size(path1)); // 	以字节为单位返回指定文件的大小。
        Files.isRegularFile(path1); //	如果指定 Path 的文件是常规文件，则返回 true 。
        Files.isSymbolicLink(path1); //	如果指定的 Path 位置是一个符号链接的文件，则返回 true 。
        Files.isHidden(path1); // 如果指定 Path 的文件系统被视为隐藏的文件，则返回 true 。

        Files.getLastModifiedTime(path1); // 返回指定文件的上次修改时间。
        FileTime now = FileTime.fromMillis(System.currentTimeMillis());
        // Files.setLastModifiedTime(path1, now); //	设置

        Files.getOwner(path1); // 返回或设置文件的所有者。 Files. setOwner(path1, UserPrincipal)
        // Files.getPosixFilePermissions(path1); // setPosixFilePermissions(Path, Set < PosixFilePermission >) 返回或设置文件的 POSIX 文件权限。

        // Files.getAttribute(path1, "acl:creationTime"); // setAttribute(Path, String, Object, LinkOption...) 返回或设置文件属性的值。

        System.out.println("==============批量获取==============");
        // 2. 一次性批量获取文件属性。单个获取每次都要访问文件系统，若同一时间内要获取多个文件属性，上面的方法效率低下
        /*
        不同的文件系统对于哪些文件属性可以被查看有不同的定义，因此将文件属性划分到不同的文件属性view中，每一种FileAttributeView可以访问不同的文件属性
         */
        BasicFileAttributes attr = Files.readAttributes(path1, BasicFileAttributes.class);

        System.out.println("creationTime: " + attr.creationTime());
        System.out.println("lastAccessTime: " + attr.lastAccessTime());
        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
        System.out.println("isDirectory: " + attr.isDirectory());
        System.out.println("isOther: " + attr.isOther());
        System.out.println("isRegularFile: " + attr.isRegularFile());
        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
        System.out.println("size: " + attr.size());

        System.out.println("==============文件存储===========");
        // 3. 文件存储属性
        Path file = Paths.get("D:/");
        //        Path file = Paths.get("D:/server.xml");  // 就算给定一个文件，也只会使用根目录驱动器来作为基准
        FileStore store = Files.getFileStore(file);

        // 打印特定文件所在的文件存储区的空间使用情况：
        long total = store.getTotalSpace() / 1024 / 1024 / 1024;
        long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024 / 1024 / 1024;
        long avail = store.getUsableSpace() / 1024 / 1024 / 1024;
        System.out.println("总容量:" + total + "G");
        System.out.println("已使用:" + used);
        System.out.println("可用:" + avail);
    }

    /**
     * 文件读写方法：以下方法从上往下复杂性依次提高
     * <p> 1. 用于常规小文件的读写 </p>
     * <ul>
     *   <li>readAllBytes</li>
     * <li>readAllLines</li>
     * <li>write 方法</li>
     * </ul>
     *
     * <p>
     *     2. 用于迭代流或者文本行
     * </p>
     * <ul>
     *     <li>newBufferedReader</li>
     * <li>newBufferedWriter</li>
     * </ul>
     * <p>3. 无缓冲流，与 java.io 包兼容</p>
     * <ul><li>nweInputStream</li><li>newOutputStream</li></ul>
     * <p>4. nio channel</p>
     * <ul><li>newByteChannels</li>
     * <li>SeekableByteChannels</li><li>ByteBuffers</li><li>ByteChannel</li></ul>
     */
    public static void readWriteTest() throws IOException {
        // readAllLine(), write()
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d1\\f1.txt");
        byte[] bytes = Files.readAllBytes(path1);
        Path path2 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d2\\f2.txt");
        byte[] newBytes = "\n第二行".getBytes();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        arrayOutputStream.write(bytes);
        arrayOutputStream.write(newBytes);
        Files.write(path2, arrayOutputStream.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING);
        List<String> strings = Files.readAllLines(path2);
        System.out.println(strings);

        // 使用缓冲流-读文件
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(path2, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        // 使用缓冲流-写文件
        String s = "今晚吃火锅。。嘿嘿";
        Path path3 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path\\d2\\f3.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path3, charset)) {
            writer.write(s, 0, s.length());
            writer.newLine();
            writer.write("--end");
        } catch (IOException x) {
            System.err.format("IOException：%s%n", x);
        }
        List<String> strings3 = Files.readAllLines(path3);
        System.out.println(strings3);

        // 使用无缓冲流--兼容java之前的IO操作，不做讨论
        try (InputStream inputStream = Files.newInputStream(path1);
             OutputStream outputStream = Files.newOutputStream(path2)) {
        }

        // 使用nio channel，直接进行内存映射
        try (SeekableByteChannel sbc = Files.newByteChannel(path3)) { // 默认为READ
            ByteBuffer buf = ByteBuffer.allocate(30);
            // 读取具有该平台正确编码的字节
            // 如果你跳过这个步骤，你可能会看到类似乱码的字符
            String encoding = System.getProperty("file.encoding");
            while (sbc.read(buf) > 0) {
                buf.rewind(); // 限制位置不变，当前位置重置为0？ 因为上面读取后，位置已经改变
                System.out.print("channel读取：" + Charset.forName(encoding).decode(buf));
                buf.flip(); // 相当于清空内容？位置重置？
            }
        } catch (IOException x) {
            System.out.println("caught exception: " + x);
        }
    }

    /**
     * 随机访问文件，在一个特定的位置读取或者写入文件
     */
    public static void positionFileTest() {
        // 把这一句文字包装成 buffer
        String s = "I was here14!\n";
        byte data[] = s.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        // 开辟一个12字节的buffer对象用来存储从文件读取的内容
        /*
         * 有以下三个最主要的参数
         * int pos : 当前buffer指针所在的位置
         * int lim ：当次有多少内容
         * int cap : 当前buffer的存储能力是多少
         */
        ByteBuffer copy = ByteBuffer.allocate(34);

        Path file = Paths.get("src\\main\\resources\\path\\d2\\f3.txt");
        try (FileChannel fc = (FileChannel.open(file, StandardOpenOption.READ, StandardOpenOption.WRITE))) {
            System.out.println("file.size=" + fc.size());
            int nread = fc.read(copy); // 读取内容到buffer中，并且返回读到的字节数,这里由于内容足够，就一次性读到了12个字节

            // 启用随机访问的能力，把指针定位到文件的开头
            fc.position(0);
            // 如果该buffer有值（也就是pos ！= lim )
            while (out.hasRemaining()) {
                fc.write(out); // 把指定内容写入到文件中
            }
            // 两次fc.size()相同，因为写入的s这句话byte也是14字节，把源文件从0-14的字节覆盖了
            System.out.println("file.size=" + fc.size());
            // 上面读取了字节后，这里还原一下，这样再次执行out.hasRemaining()就还有内容了
            // 因为还要在文件末尾写入这一句话
            out.rewind(); // 想重用此byteBuffer时调用
            fc.position(fc.size()); // 把位置定位到文件最后
            while (out.hasRemaining()) {
                fc.write(ByteBuffer.wrap("\n".getBytes()));
                fc.write(out);
            }
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }

    /**
     * 获取文件系统根目录
     */
    public static void listRootDirectory() {
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name : dirs) {
            // 在windows系统中的话就是打印各个盘符路径
            System.err.println(name);
        }
    }

    /**
     * 获取目录下所有的内容：文件，链接，子目录和隐藏文件。不会递归统计子目录
     */
    public static void listDirectory() {
        Path dir = Paths.get("d:/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            int i = 0;
            for (Path file : stream) {
                System.out.println(file.getFileName());
                i++;
            }
            System.out.println("该目录下内容数量：" + i);
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }

    public static void createDirectory() throws IOException {
        Path path = Paths.get("src/main/resources/test/a/b");
        Files.createDirectories(path); // 父目录不存在会自动创建
        Files.createDirectory(path.resolve("..").resolve("c")); // 父目录不存在会报错
    }

    /**
     * 使用Globbing过滤目录列表，经过滤当前单个目录，不查找文件树中的子目录
     * <p>Globbing是模式行为匹配，类似与通配符，<a href="https://zq99299.github.io/java-tutorial/essential/io/fileOps.html#%E6%96%B9%E6%B3%95%E9%93%BE">glob语法</a></p>
     */
    public static void listDirectoryWithGlobbing() throws IOException {
        Path dir = Paths.get("d:/");
        // 匹配以java,class, jar结尾的元素
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{java,class,jar}")) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }

        // 使用自定义的过滤器: 是目录或者是yhd开头的文件
        DirectoryStream.Filter<Path> filter = entry -> entry.toFile().isDirectory() || "yhd".startsWith(entry.getFileName().toString());
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        }
    }

    /**
     * 其他方法
     */
    public static void otherMethod() throws IOException {
        // 获取文件MIME类型
        try {
            Path filename = Paths.get("D:/server.xml");
            String type = Files.probeContentType(filename);
            if (type == null) {
                System.err.format("'%s' has an" + " unknown filetype.%n", filename);
            } else if (!type.equals("text/plain")) {
                System.err.format("'%s' is not" + " a plain text file.%n", filename);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        // 获取默认文件系统
        FileSystem fileSystem = FileSystems.getDefault();
        // 获取路径分隔符, 下面两个方法都可用，getSeparator 方法还用于检索任何可用文件系统的路径分隔符。
        String separator1 = File.separator;
        String separator2 = fileSystem.getSeparator();

        // 遍历当前机器上的文件存储区
        for (FileStore store : FileSystems.getDefault().getFileStores()) {
            System.out.println(store);
        }

        // 查找 指定文件存储在哪一个存储区
        Path file = Paths.get("src/main/resources/test/a/b");
        FileStore store = Files.getFileStore(file);
        System.out.println(store);

    }

    /**
     * 查询字符在文件中出现的次数
     *
     * @param chr 出现的字符
     * @return 出现次数
     */
    public static int findUsageNum(char chr, String strPath) {
        Path path = Paths.get(strPath);
        int result = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    return result;
                }
                for (char c : line.toCharArray()) {
                    if (c == chr) {
                        result++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
