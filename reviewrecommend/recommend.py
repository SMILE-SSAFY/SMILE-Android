from flask import Flask
from flask import request


app = Flask(__name__)

@app.route("/recommend", methods=['GET','POST'])
def hello_world():
    if request.method == 'POST':
        return f"{request.form['name']}+{request.form['password']}"