##Python 3
##Program dodający losowe dane do bazy danych
##Używa biblioteki pyrebase która można pobrać przy pomocy komendy: pip install pyrebase

import pyrebase
import datetime
import random
import time

def stuffToX(x,arg):
    while len(arg)<x:
        arg="0"+arg
    return arg



config = {
    "apiKey": "AIzaSyDtJAFlxe1-inRVknRV1uF_VUxZJTjIASg",
    "authDomain": "sailingassistant-c1f9c.firebase.com",
    "databaseURL": "https://sailingassistant-c1f9c.firebaseio.com",
    "storageBucket": "sailingassistant-c1f9c.appspot.com"
}
firebase = pyrebase.initialize_app(config)
database =firebase.database()

##Trzeba podać poprawne dane do logowania usera(łódki)
email = "boat1@example.com" ##input("email: ")
password ="boat123" ##=input("password: ")

auth = firebase.auth()
user = auth.sign_in_with_email_and_password(email,password)

for i in range(5):
    dtime = datetime.datetime.now()
    dtimeString = stuffToX(4,str(dtime.year))+"-"+stuffToX(2,str(dtime.month))+"-"+stuffToX(2,str(dtime.day))+"T"+stuffToX(2,str(dtime.hour))+":"+stuffToX(2,str(dtime.minute))+":"+stuffToX(2,str(dtime.second))+":"+stuffToX(3,str(dtime.microsecond % 1000))+"Z"
    latitude = (random.random()-0.5)*180
    longitude = (random.random()-0.5)*360
    windSpeed = random.randint(0,50)
    windDirecion = random.random()*360
    accelerometerX = (random.random()-0.5)*5
    accelerometerY = (random.random()-0.5)*5
    accelerometerZ = (random.random()-0.5)*5
    acc = { "x":accelerometerX, "y":accelerometerY,  "z":accelerometerZ }
    tensometers = []
    for i in range(6):
        tensometers.append(random.random()*10)
    inclinations = []
    for i in range(2):
        inclinations.append(random.random()*10)




    data = {"longitude":longitude,"latitude":latitude,"windDirection":windDirecion,"windSpeed":windSpeed,"accelerometer":acc,"tensometers":tensometers,"inclinations":inclinations}
    print("sending: ")
    print(data)
    database.child("boats").child(user['localId']).child(dtimeString).set(data,user['idToken'])
    time.sleep(1)

print("finished")

