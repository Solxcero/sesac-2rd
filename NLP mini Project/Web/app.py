#-*- coding: utf-8 -*-
from flask import Flask, request, render_template,jsonify
from modules import pre


import urllib.request
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from sklearn import preprocessing
from tensorflow.keras.models import load_model
from modules import pre




app = Flask(__name__)

@app.route("/")
def index():
    # return render_template("index.html")
    return render_template('index.html', message='안녕하세요! 저를 클릭해주세요')

@app.route("/chat")
def chat():
#     user_input = request.form["user_input"]
#     prediction = pred(user_input)
    
    return render_template("chat.html")

@app.route("/predict", methods=["POST"])
def predict():
    value = dict(request.form)
    for i in value:
        value = i
    pre.word_clean(value)
    print(value)
    predictions = pre.pred(value)
    print(predictions)
    # jsonify({"state": "SUCCESS", "prediction": prediction})
    return jsonify({"state": "SUCCESS", "prediction": predictions})


if __name__ == '__main__':
    # app.run(host='192.168.10.29' ,port=5000) #http://192.168.10.196:5000/
    app.run()