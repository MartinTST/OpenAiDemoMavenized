package de.mrtnxyr.openai.demo.simulator;

import de.mrtnxyr.openai.demo.interfaces.Content;
import de.mrtnxyr.openai.demo.utils.Utils;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dev.ai4j.openai4j.image.ImageModel.*;
import static dev.ai4j.openai4j.image.ImageModel.DALL_E_QUALITY_STANDARD;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;
import static java.time.Duration.ofSeconds;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhilosophySimulatorTest {

    private static final Logger logger = Logger.getLogger(PhilosophySimulatorTest.class.getName());

    String apiKey = System.getenv("openaikey");

    OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .modelName(GPT_4_O)
            .temperature(0.9)
            .timeout(ofSeconds(120))
            .logRequests(true)
            .logResponses(true)
            .build();
    ImageModel imageModel = OpenAiImageModel.builder()
            .apiKey(apiKey)
            .style(DALL_E_STYLE_NATURAL)
            .size(DALL_E_SIZE_1024_x_1024)
            .modelName(String.valueOf(DALL_E_3))
            .quality(DALL_E_QUALITY_STANDARD)
            .build();
    PhilosophySimulator philosophySimulator = new PhilosophySimulator(model, imageModel);


    @Test
    public void t1_simulateEssay() {
        String output;
        ArrayList<String> personList = new ArrayList<>();
        ArrayList<Content> contentList = new ArrayList<>();

        logger.info("\n### Essay ###\n");

        personList.addAll(List.of("fromm", "freud", "frankl", "camus", "jung", "adorno"));
        contentList.addAll(List.of(
                        new Content("Einleitung", 16),
                new Content("der autoritäre Charakter", 32),
                new Content("schuld, scham und schande und der autoritäre Charakter", 64),
                new Content("Traumatologie", 64),
                        new Content("Existenzielle Fragestellungen", 32),
                        new Content("Integration und Überwindung", 32),
                        new Content("Schlussbetrachtungen", 16)
                )
        );

        output = philosophySimulator.simulateEssay("Deutsch", "schuld, scham und schande", personList, contentList);

        logger.info(output);

        try {
            Path dir = Utils.getTestPath();
            Path fileLatest = Paths.get(dir.toString(), "essay_latest.txt");
            Path fileToBeArchived = Paths.get(dir.toString(), "essay_" +
                    Utils.getCurrentTimestamp() + ".txt");

            byte[] strToBytes = output.getBytes();

            Files.createDirectories(dir);
            if (Files.exists(fileLatest)) {
                Files.move(fileLatest, fileToBeArchived);
            }
            Files.write(fileLatest, strToBytes);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing files", e);
            throw new RuntimeException(e);
        }
    }


    @Test
    public void t2_simulateConversation() {
        String output;
        ArrayList<String> personList = new ArrayList<>();

        logger.info("\n### Dialogue ###\n");

        personList.addAll(List.of("Quine", "Kant", "Feyerabend"));

        output = philosophySimulator.simulateDialogue(
                "German",
                "Wahrheit im Spannungsfeld von Autorität und Freiheit",
                personList,
                64,
                3,
                personList.size() * 8,
                "concise and clear");

        logger.info(output);

        try {
            Path dir = Utils.getTestPath();
            Path fileLatest = Paths.get(dir.toString(), "conversation_latest.txt");
            Path fileToBeArchived = Paths.get(dir.toString(), "conversation_" +
                    Utils.getCurrentTimestamp() + ".txt");

            byte[] strToBytes = output.getBytes();

            Files.createDirectories(dir);
            if (Files.exists(fileLatest)) {
                Files.move(fileLatest, fileToBeArchived);
            }
            Files.write(fileLatest, strToBytes);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing files", e);
            throw new RuntimeException(e);
        }
    }

    @Ignore
    @Test
    public void t3_generateTtsFromText() {
        philosophySimulator.generateTtsFromText("/conversation_latest.txt");
    }

    @Ignore
    @Test
    public void t4_generateVisualization() {
        String filePath = Path.of(Utils.getTestPath().toString(), "conversation_latest.txt").toString();
        String inputText = "null";
        try {
            inputText = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageOutput = philosophySimulator.generateVisualization(inputText, 1024);
        logger.info(imageOutput);
    }
}