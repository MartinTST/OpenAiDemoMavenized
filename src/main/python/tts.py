import os
import sys
from openai import OpenAI

param_input_file = sys.argv[1]
param_output_file = sys.argv[2]
openai = OpenAI(api_key=os.environ.get("openaikey"))

input_file = open(param_input_file, "r")
content = input_file.read()
input_file.close()
# Create text-to-speech audio file
try:
    with (openai.audio.speech.with_streaming_response.create(
            input=content,
            model='tts-1',
            voice='onyx',
            speed=1.15

    ) as response):
        response.stream_to_file(param_output_file)
except Exception as e:
    print(e)