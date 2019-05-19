package com.example.maciej.sailingassistant


import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties


/**
 * Klasa modelująca odczyty w danym punkcie czasu
 */
@IgnoreExtraProperties
data class Point(var longitude: Double = 0.0, var latitude: Double = 0.0, val speed: Double = 0.0,
                 val windDirection: Double = 0.0, val windSpeed: Double = 0.0) : Parcelable {
    var datetime: Datetime? = Datetime(0, 0, 0, 0, 0, 0, 0)
    var tensometers: List<Double> = ArrayList(6)
    var inclinations: List<Double> = ArrayList(2)
    var accelerometer: Map<String, Double> = HashMap()
    var gyroscope: Map<String,Double> = HashMap()


    constructor(datetime: Datetime, longitude: Double, latitude: Double, windDirection: Double, windSpeed: Double,
                tensometers: ArrayList<Double>, inclinations: ArrayList<Double>, accelerometer: Map<String, Double>, gyroscope: Map<String,Double>) : this(longitude, latitude, windDirection, windSpeed) {

        this.datetime = datetime
        this.tensometers=tensometers
        this.inclinations=inclinations
        this.accelerometer=accelerometer
        this.gyroscope=gyroscope
    }

    constructor(parcel: Parcel) : this(
            //parcel.readParcelable(Datetime::class.java.classLoader),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble()) {
        datetime = parcel.readParcelable(Datetime::class.java.classLoader)
        parcel.readList(tensometers, Double::class.java.classLoader)
        parcel.readList(inclinations, Double::class.java.classLoader)
        parcel.readMap(accelerometer, Double::class.java.classLoader)
        parcel.readMap(gyroscope, Double::class.java.classLoader)
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeDouble(speed)
        parcel.writeDouble(windDirection)
        parcel.writeDouble(windSpeed)
        parcel.writeParcelable(datetime, flags)
        parcel.writeList(tensometers)
        parcel.writeList(inclinations)
        parcel.writeMap(accelerometer)
        parcel.writeMap(gyroscope)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Point> {
        override fun createFromParcel(parcel: Parcel): Point {
            return Point(parcel)
        }

        override fun newArray(size: Int): Array<Point?> {
            return arrayOfNulls(size)
        }
    }

    fun getField(fieldName: String): Double? {
        return when(fieldName) {
            "longtitude" ->  longitude
            "latitude" ->  latitude
            "speed" -> speed
            "windDirection" ->  windDirection
            "windSpeed" ->  windSpeed
            "tensometers0" ->  tensometers[0]
            "tensometers1" ->  tensometers[1]
            "tensometers2" ->  tensometers[2]
            "tensometers3" ->  tensometers[3]
            "tensometers4" ->  tensometers[4]
            "tensometers5" ->  tensometers[5]
            "inclinations0" ->  inclinations[0]
            "inclinations1" ->  inclinations[1]
            "accelerometerX" ->  accelerometer["x"]
            "accelerometerY" ->  accelerometer["y"]
            "accelerometerZ" ->  accelerometer["z"]
            "gyroscopeX" ->  gyroscope["x"]
            "gyroscopeY" ->  gyroscope["y"]
            "gyroscopeZ" ->  gyroscope["z"]
            else ->  null
        }
    }

}