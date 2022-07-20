package birdsnail.example.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class FilesTest {


    public static void main(String[] args) throws IOException {
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
}
