package de.mrtnxyr.openai.demo.utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.mrtnxyr.openai.demo.utils.Utils.PYTHON_INTERPRETER_PATH;
import static de.mrtnxyr.openai.demo.utils.Utils.PYTHON_SCRIPTS_PATH;

public class PythonInvoker {

    private static final Logger logger = Logger.getLogger(PythonInvoker.class.getName());

    public void invokeTtsViaPython(String input, String output) {
        String pythonPath = Path.of(PYTHON_INTERPRETER_PATH).toString();
        String scriptPath = Path.of(PYTHON_SCRIPTS_PATH,"tts.py").toString();
        String line = pythonPath + " " + scriptPath;

        CommandLine cmdLine = CommandLine.parse(line);
        cmdLine.addArgument(input);
        cmdLine.addArgument(output);

        long startTime = System.currentTimeMillis();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(streamHandler);

            ExecuteWatchdog watchdog = new ExecuteWatchdog(60000); // 60 seconds timeout
            executor.setWatchdog(watchdog);

            int exitCode = executor.execute(cmdLine);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            logger.info("Python script executed with exit code: " + exitCode);
            logger.info("Output: " + outputStream.toString());
            logger.info("Execution duration: " + duration + " ms");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error executing Python script.", e);
            throw new RuntimeException("Error executing Python script", e);
        }
    }
}