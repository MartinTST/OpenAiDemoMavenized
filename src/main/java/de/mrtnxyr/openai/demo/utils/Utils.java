package de.mrtnxyr.openai.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static final String FILES_DIR = "output/generated/";
    public static final String PYTHON_INTERPRETER_PATH = "/Users/mrtn/IdeaProjects/OpenAiDemoMavenized/venv/bin/python3";
    public static final String PYTHON_SCRIPTS_PATH = "/Users/mrtn/IdeaProjects/OpenAiDemoMavenized/src/main/python/";


    public static String getCurrentTimestamp() {
        Date currentDate = new Date();
        String pattern = "yyyyMMdd_HHmmss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(currentDate);
    }

    public static void prepareFilesystem() {
        Path inputDir = Path.of(FILES_DIR);
        Path inputDirSub1 = inputDir.resolve("tts");
        Path inputDirSub2 = inputDir.resolve("images");

        try {
            Files.createDirectories(inputDir);
            Files.createDirectories(inputDirSub1);
            Files.createDirectories(inputDirSub2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getTestPath() {
        return Path.of(FILES_DIR);
    }

    public static void log(String message, Level level) {
        logger.log(level, message);
    }

    public static void downloadFileFromUrl(String fileUrl, String destinationPath) {
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            logger.info("File downloaded successfully to " + destinationPath);
        } catch (IOException e) {
            logger.severe("Error downloading file: " + e.getMessage());
        }
    }
}