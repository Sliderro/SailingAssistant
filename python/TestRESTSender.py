##Python 3
##Program dodający losowe dane do bazy danych
##Używa biblioteki pyrebase która można pobrać przy pomocy komendy: pip install pyrebase

import pyrebase
import datetime
import random
import time

config = {
    "apiKey": "AIzaSyDtJAFlxe1-inRVknRV1uF_VUxZJTjIASg",
    "authDomain": "sailingassistant-c1f9c.firebase.com",
    "databaseURL": "https://sailingassistant-c1f9c.firebaseio.com",
    "storageBucket": "sailingassistant-c1f9c.appspot.com"
}
firebase = pyrebase.initialize_app(config)
database =firebase.database()

##Trzeba podać poprawne dane do logowania usera(łódki)
email = input("email: ")
password =input("password: ")

auth = firebase.auth()
user = auth.sign_in_with_email_and_password(email,password)

for i in range(5):
    dtime = datetime.datetime.now()
    dtimeString = str(dtime.year)+"-"+str(dtime.month)+"-"+str(dtime.day)+" "+str(dtime.hour)+":"+str(dtime.minute)+":"+str(dtime.second)+":"+str(dtime.microsecond)
    gps1 = random.randint(0,100)
    gps2 = random.randint(0,100)
    gpsString = str(gps1)+"N,"+str(gps2)+"E"
    wind = random.randint(0,50)
    windString = str(wind)+"W"
    data = {"GPS":gpsString,"wind":windString}
    print("sending: ")
    print(data)
    database.child("boats").child(user['localId']).child(dtimeString).set(data,user['idToken'])
    time.sleep(1)

print("finished")


