package com.cshisan.qqsg;

import com.cshisan.qqsg.file.FileHeader;
import com.cshisan.qqsg.file.FileTypeEnum;
import com.cshisan.qqsg.parse.ParseFactory;
import com.cshisan.qqsg.parse.ParseStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Batch decoder for QQSG image assets.
 */
public class Decoder {
    private static final Set<String> SUPPORTED_EXTENSIONS = Set.of("gso", "gsn", "gsa");

    public static void decode(Path inputRoot, Path outputRoot) throws IOException {
        if (!Files.isDirectory(inputRoot)) {
            throw new IllegalArgumentException("input root must be an existing directory: " + inputRoot);
        }
        Files.createDirectories(outputRoot);
        try (Stream<Path> walk = Files.walk(inputRoot)) {
            walk.filter(Files::isRegularFile).filter(Decoder::isSupported).forEach(
                    source -> process(source, inputRoot, outputRoot)
            );
        }
    }

    private static void process(Path source, Path inputRoot, Path outputRoot) {
        try {
            Path relative = inputRoot.relativize(source);
            String fileName = relative.getFileName().toString();
            String ext = extension(fileName).toLowerCase();
            byte[] data = Files.readAllBytes(source);
            FileHeader fileHeader = new FileHeader(data, FileTypeEnum.fromExt(ext));
            ParseStrategy strategy = ParseFactory.typeOf(fileHeader.type);
            if (strategy == null) {
                throw new IllegalStateException("unsupported file type: " + fileHeader.type);
            }
            List<BufferedImage> frames = strategy.parse(fileHeader, data);
            Path destinationDir = relative.getParent() == null ? outputRoot : outputRoot.resolve(relative.getParent());
            Files.createDirectories(destinationDir);
            String baseName = baseName(fileName);
            for (int index = 0; index < frames.size(); index++) {
                String suffix = frames.size() == 1 ? "" : "-" + index;
                Path outputFile = destinationDir.resolve(baseName + suffix + ".png");
                ImageIO.write(frames.get(index), "png", outputFile.toFile());
//                System.out.printf("saved %s%n", outputFile);
            }
        } catch (Exception e) {
            System.out.println("error parsing file: " + source);
            throw new RuntimeException(e);
        }
    }

    private static boolean isSupported(Path path) {
        String ext = extension(path.getFileName().toString()).toLowerCase();
        return SUPPORTED_EXTENSIONS.contains(ext);
    }

    private static String extension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot < 0 ? "" : fileName.substring(dot + 1);
    }

    private static String baseName(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot < 0 ? fileName : fileName.substring(0, dot);
    }

    public static void main(String[] args) throws IOException {
        // 读取电脑当前用户名称
        String currentUser = System.getProperty("user.name");
        decode(
//                Paths.get("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-raw".formatted(currentUser)),
                Paths.get("C:\\Users\\%s\\Desktop\\QQSG\\data\\output-test".formatted(currentUser)),
                Paths.get("C:\\Users\\%s\\Desktop\\QQSG\\data\\output")
        );
    }
}