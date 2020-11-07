import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Application {
    final static String EXT = ".txt";
    final static String DEFAULT_TEST_FOLDER = "testFolders";

    public static void main(String... args){
        Path path = Paths.get(args.length != 0 ? args[0] : DEFAULT_TEST_FOLDER);

        List<Path> files = null;
        try {
            files = getTextFileListFrom(path, EXT);
        } catch (IOException e) {
            System.err.println("Directory not found");
        }

        files.sort((p1,p2) -> p1.getFileName().compareTo(p2.getFileName()));
        files.forEach(v -> { System.out.println(v.getFileName() + " -> " + v); });

    }


    static List<Path> getTextFileListFrom(Path folder, String ext) throws IOException {
        List<Path> files = new ArrayList<>();
        Path res = Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(ext))
                    files.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}
