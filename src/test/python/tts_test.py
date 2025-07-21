import os
import subprocess

# sample input file
sample_input_file = "/Users/mrtn/IdeaProjects/OpenAiDemoMavenized/src/test/resources/sample_input.txt"

# Define the output file
sample_output_file = "//Users/mrtn/IdeaProjects/OpenAiDemoMavenized/src/test/resources/sample_output.mp3"

# Run the tts.py script
try:
    result = subprocess.run(
        ["python3", "/Users/mrtn/IdeaProjects/OpenAiDemoMavenized/src/main/python/tts.py", sample_input_file, sample_output_file],
        check=True,
        capture_output=True,
        text=True
    )
    print("Script output:", result.stdout)
except subprocess.CalledProcessError as e:
    print("Error:", e.stderr)

# Check if the output file was created
if os.path.exists(sample_output_file):
    print(f"Output file '{sample_output_file}' created successfully.")
else:
    print(f"Failed to create output file '{sample_output_file}'.")