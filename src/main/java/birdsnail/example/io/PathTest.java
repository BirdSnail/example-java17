package birdsnail.example.io;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * java path使用
 * PATH: 用于标识文件系统中的一个文件或者路径 path与系统无关，操作path并不会访问文件系统
 */
public class PathTest {
    public static void main(String[] args) throws IOException {
        // searchPath();
        // normalizeTest();
        // transformPath();
        // resolvePathTest();
        // relativeTest();
        equalsAndIteratorTest();
    }

    public static Path createPath() {
        Path p1 = Paths.get("/tmp/foo");
        Path p3 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        // 在windows下使用这样的路径，绝对路径
        Path p4 = Paths.get("C:\\Users\\Administrator\\Desktop\\局域网屏幕分享");
        // 与上面的写法等价
        p4 = FileSystems.getDefault().getPath("/users/sally");
        Path p5 = Paths.get(URI.create("file:/C:/Users/Administrator/Desktop/局域网屏幕分享/使用说明.txt"));


        /*
        相对路径
         */
        // Solaris syntax
        Path path6 = Paths.get("sally/bar");
        // Microsoft Windows syntax
        Path path7 = Paths.get("sally\\bar");

        return p4;
    }

    public static void searchPath() {
        // 以下操作都不需要有真实的文件与之对应
        // Microsoft Windows syntax
        Path path = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");

        // Solaris syntax
        // Path path = Paths.get("/home/joe/foo");
        System.out.println("============绝对路径==============");
        System.out.format("toString: %s%n", path.toString());
        // path表示的最后一级文件名称或者目录
        System.out.format("getFileName: %s%n", path.getFileName());
        // path路径中最接近根元素的第一个值作为name，返回一个新的path /root/p1/p2/p3 --> p1
        System.out.format("getName(0): %s%n", path.getName(0));
        // path中的以”/“符号分割的元素数量
        System.out.format("getNameCount: %d%n", path.getNameCount());
        // path中由start index到end index的子序列，不含root
        System.out.format("subpath(0,2): %s%n", path.subpath(0, 2));
        // 获取父级的path，path中有多个元素时等价于subpath(0，getNameCount()-1);
        System.out.format("getParent: %s%n", path.getParent());
        // path中根元素
        System.out.format("getRoot: %s%n", path.getRoot());

        /*
        相对路径
         */
        System.out.println("=============相对路径===========");
        Path path2 = Paths.get("path\\name1");
        System.out.format("toString: %s%n", path2.toString());
        // path表示的最后一级文件名称或者目录
        System.out.format("getFileName: %s%n", path2.getFileName());
        // path路径中最接近根元素的第一个值作为name，返回一个新的path /root/p1/p2/p3 --> p1
        System.out.format("getName(0): %s%n", path2.getName(0));
        // path中的以”/“符号分割的元素数量
        System.out.format("getNameCount: %d%n", path2.getNameCount());
        // path中由start index到end index的子序列，不含root
        System.out.format("subpath(0,2): %s%n", path2.subpath(0, 1));
        // 获取父级的path，path中有多个元素时等价于subpath(0，getNameCount()-1);
        System.out.format("getParent: %s%n", path2.getParent());
        // path中根元素
        System.out.format("getRoot: %s%n", path2.getRoot());
    }

    /**
     * 删除path冗余的元素
     */
    public static void normalizeTest() {
        Path path1 = Paths.get("/home/./joe/foo");
        Path path2 = Paths.get("/home/sally/../joe/foo");

        System.out.println("path1删除冗余的元素：" + path1.normalize()); // \home\joe\foo
        System.out.println("path2删除冗余的元素：" + path2.normalize()); // \home\joe\foo
    }

    /**
     * path转换
     * <ol>
     *     <li>
     * 转为URI
     *     </li>
     * <li>
     *     变成绝对路径
     * </li>
     * <li>
     *     变成真实路径
     * </li>
     * </ol>
     */
    public static void transformPath() throws IOException {
        // 转换为浏览器可以访问的字符串
        Path path = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        System.out.format("uri: %s%n", path.toUri());

        // 转为绝对路径,path对象表示的文件可以不存在
        Path path2 = Paths.get("src");
        System.out.println("绝对路径：" + path2.toAbsolutePath());

        // 转为真实路径，表示的文件或者目录必须存在
        Path path3 = Paths.get("src/main");
        System.out.println("真实路径：" + path3.toRealPath());
    }

    /**
     * 链接两个路径
     */
    public static void resolvePathTest() {
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        Path path2 = Paths.get("d1");
        System.out.println("连接后的path: " + path1.resolve(path2));
    }

    /**
     * 构建两个路径的相对路径，如果一个路径有根节点，另一个路径没有根节点，则不能创建
     */
    public static void relativeTest() {
        // 不能构建p1对p2的相对路径
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        Path path2 = Paths.get("d1");
        // System.out.println("p1_to_p2相对路径：" + path1.relativize(path2));

        Path path3 = Paths.get("src\\main\\resources\\path");
        System.out.println("path3绝对路径：" + path3.toAbsolutePath());
        Path path4 = Paths.get("test");
        System.out.println("path4绝对路径：" + path4.toAbsolutePath());
        Path p3_to_p4 = path3.relativize(path4);
        System.out.println("p3_to_p4相对路径：" + p3_to_p4);
        Path p4_to_p3 = path4.relativize(path3);
        System.out.println("p4_to_p3相对路径：" + p4_to_p3);
        System.out.println("p3_to_p3相对路径：" + path3.relativize(path3));

        // 从p3到p4
        System.out.println("p3到p4的路径：" + path3.resolve(p3_to_p4).toAbsolutePath().normalize());
        // 从p4到p3
        System.out.println("p4到p3的路径：" + path4.resolve(p4_to_p3).toAbsolutePath().normalize());
    }

    /**
     * 比较两个path.遍历path中的name, 不含根节点name
     */
    public static void equalsAndIteratorTest() {
        Path path1 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        Path path2 = Paths.get("D:\\qizhidao-project\\example-java17\\src\\main\\resources\\path");
        System.out.println("path1.equals(path2): " + path1.equals(path2));
        Path path3 = Paths.get("src");
        System.out.println("path1.equals(path3): "  + path1.equals(path3));

        System.out.println("=============遍历path中的name=========");
        int i = 1;
        for (Path p : path1) {
            System.out.println("path—" + (i++) + "-name: " + p);
        }

    }

}
