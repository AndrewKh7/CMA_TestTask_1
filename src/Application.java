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
    final static String RESULT_FILE_NAME = "resultFile.txt";

    public static void main(String... args){
        Path path = Paths.get(args.length != 0 ? args[0] : DEFAULT_TEST_FOLDER);
        
        List<Path> files = null;
        try {
            files = getTextFilesList(path, EXT);
        } catch (IOException e) {
            System.out.println((args.length != 0 ? args[0] : DEFAULT_TEST_FOLDER) + ": directory not found");
            System.exit(1);
        }

        files.sort((p1,p2) -> p1.getFileName().compareTo(p2.getFileName()));
        System.out.println("Sorted filenames:");
        files.forEach(v -> { System.out.println(v.getFileName() + " -> " + v); });

        try {
            concatenateFilesContent(files, RESULT_FILE_NAME);
        } catch (IOException e) {
            System.out.println(RESULT_FILE_NAME + ": failed to create result file.");
            System.exit(1);
        }
    }

    private static void concatenateFilesContent(List<Path> files, String resultFileName) throws IOException {
        Path resultFile = Paths.get(resultFileName);
        try( OutputStream out = Files.newOutputStream(resultFile, StandardOpenOption.CREATE)) {
            files.forEach(v -> {
                try (InputStream in = Files.newInputStream(v, StandardOpenOption.READ)) {
                    in.transferTo(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } 
    }

    static List<Path> getTextFilesList(Path folder, String fileExtension) throws IOException {
        List<Path> files = new ArrayList<>();
        Path res = Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(fileExtension))
                    files.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return files;
    }
}
