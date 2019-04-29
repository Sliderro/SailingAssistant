package com.example.maciej.sailingassistant

import android.os.Parcel
import android.os.Parcelable

data class Accelerometer(val x:Double,val y:Double,val z:Double) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(x)
        parcel.writeDouble(y)
        parcel.writeDouble(z)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Accelerometer> {
        override fun createFromParcel(parcel: Parcel): Accelerometer {
            return Accelerometer(parcel)
        }

        override fun newArray(size: Int): Array<Accelerometer?> {
            return arrayOfNulls(size)
        }
    }
}