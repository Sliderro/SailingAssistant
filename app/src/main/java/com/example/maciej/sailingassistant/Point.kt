package com.example.maciej.sailingassistant


import android.os.Parcel
import android.os.Parcelable


data class Point(val datetime: Datetime , val longitude: Double, val latitude: Double, val accelerometer: Accelerometer,
                 val windDirecion: Double,val windSpeed: Double) : Parcelable {

    val tensometers: DoubleArray = DoubleArray(6)
    val inclinations: DoubleArray = DoubleArray(2)

    constructor( datetime: Datetime ,  longitude: Double,  latitude: Double,  accelerometer: Accelerometer,
                 windDirecion: Double, windSpeed: Double, tensometers: DoubleArray, inclinations: DoubleArray) : this(
            datetime, longitude, latitude, accelerometer, windDirecion, windSpeed) {
        System.arraycopy(tensometers,0,this.tensometers,0,tensometers.size)
        System.arraycopy(inclinations,0,this.inclinations,0,inclinations.size)
    }
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Datetime::class.java.classLoader),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readParcelable(Accelerometer::class.java.classLoader),
            parcel.readDouble(),
            parcel.readDouble()) {
        parcel.readDoubleArray(tensometers)
        parcel.readDoubleArray(inclinations)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(datetime, flags)
        parcel.writeDouble(longitude)
        parcel.writeDouble(latitude)
        parcel.writeParcelable(accelerometer, flags)
        parcel.writeDouble(windDirecion)
        parcel.writeDouble(windSpeed)
        parcel.writeDoubleArray(tensometers)
        parcel.writeDoubleArray(inclinations)
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

}