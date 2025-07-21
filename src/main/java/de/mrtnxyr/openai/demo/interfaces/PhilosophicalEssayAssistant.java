package de.mrtnxyr.openai.demo.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.ArrayList;

public interface PhilosophicalEssayAssistant {
    @UserMessage(
            "Please write a philosophical essay. " +
                    "the Topic is as follows: {{query}}. " +
                    "the Topic should relate to the ideas, theories and concepts of following philosophers: {{personList}} " +
                    "Please reply in {{lang}}. ")
    @SystemMessage(
            "The whole essay should have the following structure contained in this list: {{contentList}} \n" +
                    "Each element of this list has 2 sub-elements. " +
                    "The first sub-element indicates the title. +" +
                    "The second sub-element indicates the number of sentences for this title. \n" +
                    "The essay should include citations from the relevant literature with Harvard in-text citation. for each citation, cite the the exact page number. \n" +
                    //"for the citation material use at least 2 different sources from each philosopher. \n" +
                    "For the citations, use a mixture of original and secondary literature. \n" +
                    "The last part of the essay should contain the bibliography. \n"
    )
    String chat(
            @V("lang") String lang,
            @V("query") String query,
            @V("personList") ArrayList<String> personList,
            @V("contentList") ArrayList<Content> contentList
    );
}
