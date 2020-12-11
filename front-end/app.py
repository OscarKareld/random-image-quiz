from flask import Flask, render_template, redirect, url_for, request
app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/scoreboard')
def scoreboard():
    return render_template('scoreboard.html')

@app.route('/api')
def api():
    return render_template('api.html')

@app.route('/quiz/<id>')
def quiz():
    return render_template('quiz.html')

@app.route('/result/<id>')
def result():
    return render_template('result.html')

app.run(host='localhost', port=5000, debug=True) 

