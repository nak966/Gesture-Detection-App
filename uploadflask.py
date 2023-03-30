import os
from flask import Flask, request

app = Flask(__name__)

@app.route('/upload_video', methods=['POST'])
def upload_video():
    file = request.files['video']
    filename = file.filename
    filepath = os.path.join(os.getcwd(), filename)
    file.save(filepath)
    return 'File uploaded successfully'

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)
