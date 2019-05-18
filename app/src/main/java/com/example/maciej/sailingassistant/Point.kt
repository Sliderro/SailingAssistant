package com.example.maciej.sailingassistant


import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Klasa modelująca odczyty w danym punkcie czasu
 */
@IgnoreExtraProperties
data class Point(var longitude: Double = 0.0, var latitude: Double = 0.0,
                 val windDirecion: Double = 0.0, val windSpeed: Double = 0.0) : Parcelable {
    var datetime: Datetime? = Datetime(0, 0, 0, 0, 0, 0, 0)
    val tensometers: List<Double> = ArrayList(6)
    val inclinations: List<Double> = ArrayList(2)
    val accelerometer: Map<String, Double> = HashMap()

    constructor(datetime: Datetime, longitude: Double, latitude: Double, windDirecion: Double, windSpeed: Double,
                tensometers: DoubleArray, inclinations: DoubleArray, accelerometer: Map<String, Double>) : this(longitude, latitude, windDirecion, windSpeed) {

        this.datetime = datetime
        System.arraycopy(tensometers, 0, this.tensometers, 0, tensometers.size)
        System.arraycopy(inclinations, 0, this.inclinations, 0, inclinations.size)
    }

    constructor(parcel: Parcel) : this(
            //parcel.readParcelable(Datetime::class.java.classLoader),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble()) {
        datetime = parcel.readParcelable(Datetime::class.java.classLoader)
        parcel.readList(tensometers, Double::class.java.classLoader)
        parcel.readList(inclinations, Double::class.java.classLoader)
        parcel.readMap(accelerometer, Double::class.java.classLoader)
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeDouble(windDirecion)
        parcel.writeDouble(windSpeed)
        parcel.writeParcelable(datetime, flags)
        parcel.writeList(tensometers)
        parcel.writeList(inclinations)
        parcel.writeMap(accelerometer)
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
            "windDirection" ->  windDirecion
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
            else ->  null
        }
    }

}