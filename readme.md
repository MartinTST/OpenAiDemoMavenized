Description
# Philosophical advisor
The application is a Java-based project that integrates with OpenAI 
- to simulate philosophical conversations
- generate essays
- and create visualizations based on text input. 

It also includes functionality to convert text to speech using Python via an apache.commons.exec Executor. 

## Classes
### PhilosophySimulator
Manages the simulation of dialogues, essays, and visualizations. It uses AI services to generate responses and images, and it invokes Python scripts for text-to-speech conversion.
### PythonInvoker
This class is responsible for invoking Python scripts from within the Java application. It provides methods to execute Python scripts, particularly for text-to-speech conversion, by calling the appropriate Python interpreter and script paths.

## Interfaces
### ImageGeneratorPreprocessingAssistant
This interface defines a method for preprocessing text to make it suitable for image generation. The method summarizes the text to ensure it contains visually representable elements and does not exceed a specified number of keywords.  
### PhilosopherConversationAssistant
This interface is responsible for managing philosophical dialogues. It allows generating dialogues based on inputs and parameters such as language and the number of words per sentence.  
### PhilosophicalEssayAssistant
This interface supports the creation of philosophical essays. It takes inputs like language, topic, and a list of persons and content to generate a coherent essay.

## Python
### tts.py
This Python script is responsible for converting text to speech. It uses a text-to-speech library to convert the input text into an audio file. The script is invoked by the Java application through the PythonInvoker class.