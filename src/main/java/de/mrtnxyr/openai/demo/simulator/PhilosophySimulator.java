package de.mrtnxyr.openai.demo.simulator;

import de.mrtnxyr.openai.demo.interfaces.Content;
import de.mrtnxyr.openai.demo.interfaces.ImageGeneratorPreprocessingAssistant;
import de.mrtnxyr.openai.demo.interfaces.PhilosopherConversationAssistant;
import de.mrtnxyr.openai.demo.interfaces.PhilosophicalEssayAssistant;
import de.mrtnxyr.openai.demo.utils.PythonInvoker;
import de.mrtnxyr.openai.demo.utils.Utils;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PhilosophySimulator {

    private static final Logger logger = Logger.getLogger(PhilosophySimulator.class.getName());

    OpenAiChatModel model;
    ImageModel imageModel;

    public PhilosophySimulator(OpenAiChatModel model, ImageModel imageModel) {
        this.model = model;
        this.imageModel = imageModel;
        Utils.prepareFilesystem();
    }

    public String simulateDialogue(String lang, String query, ArrayList<String> personList,
                                   Integer numberOfWordsPerSentence, Integer numberOfParts,
                                   Integer numberOfSentencesPerPart,
                                   String mode) {
        PhilosopherConversationAssistant assistant = AiServices.create(PhilosopherConversationAssistant.class, model);
        String answer = assistant.chat(lang, query, personList, mode, numberOfWordsPerSentence,
                numberOfParts, numberOfSentencesPerPart);
        return answer;
    }

    public String simulateEssay(String lang, String query, ArrayList<String> personList, ArrayList<Content> contentList) {
        PhilosophicalEssayAssistant assistant = AiServices.create(PhilosophicalEssayAssistant.class, model);
        String answer = assistant.chat(lang, query, personList, contentList);
        return answer;
    }

    public String generateVisualization(String input, Integer numberOfKeywords) {
        ImageGeneratorPreprocessingAssistant assistant = AiServices.create(ImageGeneratorPreprocessingAssistant.class, model);
        String additionalPreInfo = "Rules for Image Generation: \n" +
                "- Focus on specific, visually representable elements. \n" +
                //"- The image should look like a painting from the early 20th century. \n" +
                "- Avoid ambiguous language that could be interpreted as including text. \n" +
                "---------\n" +
                "Description of the image: \n";
        String additionalPostInfo = "\nEnd of Description.";

        String compressed = additionalPreInfo + assistant.chat(numberOfKeywords, input) + additionalPostInfo;

        logger.info(compressed);

        Response<Image> response = imageModel.generate(compressed);

        Path targetDir = Utils.getTestPath();
        String targetFile = "output_image_" + Utils.getCurrentTimestamp() + ".png";

        Utils.downloadFileFromUrl(
                response.content().url().toString(),
                String.valueOf(Path.of(String.valueOf(targetDir),"images",targetFile))
        );

        return response.toString();
    }

    public void generateTtsFromText(String input_file){
        PythonInvoker pythonInvoker = new PythonInvoker();
        Path inputDir = Utils.getTestPath();

        try {
            pythonInvoker.invokeTtsViaPython(
                    inputDir.toString() + input_file,
                    inputDir.toString() + "/tts/output_"
                            + Utils.getCurrentTimestamp() + ".mp3");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
