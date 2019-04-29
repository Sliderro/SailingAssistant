package com.example.maciej.sailingassistant

import android.os.Parcel
import android.os.Parcelable

data class Datetime(val year:Int, val month:Int, val day:Int, val hour:Int, val minute: Int, val second:Int, val microsecond: Int) : Parcelable, Comparable<Datetime> {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(day)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeInt(second)
        parcel.writeInt(microsecond)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Datetime> {
        override fun createFromParcel(parcel: Parcel): Datetime {
            return Datetime(parcel)
        }

        override fun newArray(size: Int): Array<Datetime?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: Datetime): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}