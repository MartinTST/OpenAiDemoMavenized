package de.mrtnxyr.openai.demo.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.ArrayList;

public interface PhilosopherConversationAssistant {
        @UserMessage(
                "Please reply as if following philosophers having a conversation: {{personList}}. " +
                        "the Topic is as follows: {{query}}. " +
                        "The whole conversation should have {{numberOfParts}} parts " +
                        "with {{numberOfSentencesPerPart}} sentences in each part. " +
                        "Use up to {{numberOfWordsPerSentence}} words per sentence. " +
                        "The conversation should be in the following mode: {{mode}}. " +
                        "Reply in {{lang}}. "
        )
        String chat(
                @V("lang") String lang,
                @V("query") String query,
                @V("personList") ArrayList<String> personList,
                @V("mode") String mode,
                @V("numberOfWordsPerSentence") Integer numberOfWordsPerSentence,
                @V("numberOfParts") Integer numberOfParts,
                @V("numberOfSentencesPerPart") Integer numberOfSentencesPerPart
                );
    }

