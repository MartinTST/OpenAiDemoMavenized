package de.mrtnxyr.openai.demo.interfaces;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ImageGeneratorPreprocessingAssistant {
    @UserMessage("Please summarize the following text so that it is suitable for image generation: {{inputText}} " +
            "The output text has no more than {{numberOfKeywords}} words. ")
    String chat(@V("numberOfKeywords") Integer numberOfKeywords,
                @V("inputText") String inputText);
}
