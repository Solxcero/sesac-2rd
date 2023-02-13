import urllib.request
import pandas as pd
import numpy as np
from konlpy.tag import Okt
import tensorflow as tf
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from sklearn import preprocessing
from tensorflow.keras.models import load_model

import pickle
import imp
imp.reload


def word_clean(df):
    from hanspell import spell_checker
    import re
    from tqdm import tqdm
    CHANGE_FILTER = re.compile("([~!?\"':;&)(])") #제외할 문자
    okt = Okt()
    X_train = []
    for sentence in tqdm(df):
        sentence = re.sub(CHANGE_FILTER,'','{}'.format(sentence)) #문자 제외
#         sentence = sentence.upper()
        ok = spell_checker.check(sentence) # 맞춤법 확인
        sentence = ok.checked #바꾼 문자로 변경
        clean_words = []
        for word in okt.pos(sentence):
            if word[1] in ['Noun', 'Modifier', 'Alpha']: #명사, 관형사(가끔 나와서), 영어
                 clean_words.append(word[0]) #찾아진 것 중 단어만 추가
        sentence = ' '.join(clean_words)
        X_train.append(sentence)
    return X_train


okt = Okt()
max_len = 15
tokenizer = Tokenizer()

def question_processing(sentences):
    sentences = [sentences]
    inputs = []
    for sentence in sentences : 
        sentence = okt.morphs(sentence)
        encoded = tokenizer.texts_to_sequences([sentence])
        inputs.append(encoded[0])
    padded_inputs = pad_sequences(inputs, maxlen=max_len, padding='post')
    return padded_inputs

df= pd.read_csv('C:/Users/user/Desktop/Project/web/qa_final.csv')

model_loaded = tf.keras.models.load_model('C:/Users/user/Desktop/Project/web/intent_epo100_bs64.h5')
with open('C:/Users/user/Desktop/Project/web/tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)

idx_encode = preprocessing.LabelEncoder()
idx_encode.fit(df['A'])

# y_train = idx_encode.transform(df['A'])

label_idx = dict(zip(list(idx_encode.classes_), idx_encode.transform(list(idx_encode.classes_))))
idx_label = {}
for key, value in label_idx.items():
    idx_label[value] = key

def pred(input_text):
    pred = []
    input_sentence = question_processing(word_clean([input_text])[0])
    prediction = np.argmax(model_loaded.predict(input_sentence), axis = 1)
    for p in prediction: pred.append(idx_label[p])
    return pred[0]