package com.example.maciej.sailingassistant

import android.os.Parcel
import android.os.Parcelable

/**
 * Klasa reprezentująca datę i godzinę
 */
data class Datetime(val year:Int, val month:Int, val day:Int, val hour:Int, val minute: Int, val second:Int, val milisecond: Int) : Parcelable, Comparable<Datetime> {

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
        parcel.writeInt(milisecond)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Datetime> {
        const val zeroDatetimeString = "0000-00-00T00:00:00:000Z"
        override fun createFromParcel(parcel: Parcel): Datetime {
            return Datetime(parcel)
        }

        override fun newArray(size: Int): Array<Datetime?> {
            return arrayOfNulls(size)
        }

        /**
         * Tworzy instancję klasy ze sformatowanego stringa ("yy-mm-ddThh-mm-ss-mmmZ")
         */
        fun fromString(string: String) : Datetime {
            val yearString = string.substring(0,4)
            val monthString = string.substring(5,7)
            val dayString = string.substring(8,10)
            val hourString = string.substring(11,13)
            val minuteString = string.substring(14,16)
            val secondString = string.substring(17,19)
            val milisecondString = string.substring(20,23)
            return Datetime(yearString.toInt(),monthString.toInt(),dayString.toInt(),hourString.toInt(),minuteString.toInt(),secondString.toInt(),milisecondString.toInt())
        }

    }

    override fun compareTo(other: Datetime): Int {
        var i : Int = this.year.compareTo(other.year)
        if(i!=0) return i
        i=this.month.compareTo(other.month)
        if(i!=0) return i
        i=this.day.compareTo(other.day)
        if(i!=0) return i
        i=this.hour.compareTo(other.hour)
        if(i!=0) return i
        i=this.minute.compareTo(other.minute)
        if(i!=0) return i
        i=this.second.compareTo(other.second)
        if(i!=0) return i
        i=this.milisecond.compareTo(other.milisecond)
        return i
    }

    /**
     * Tworzy sformatowany string na podstawie instancji klasy
     */
     fun toFormattedString(): String {
        val yearS = addZeroPadding(4,year.toString())
        val monthS = addZeroPadding(2,month.toString())
        val dayS = addZeroPadding(2,day.toString())
        val hourS = addZeroPadding(2,hour.toString())
        val minuteS = addZeroPadding(2,minute.toString())
        val secondS = addZeroPadding(2,second.toString())
        val milisecondS = addZeroPadding(3,milisecond.toString())
        return "$yearS-$monthS-${dayS}T$hourS:$minuteS:$secondS:${milisecondS}Z"
    }

    /**
     * Dodaje zera do początku stringa do uzyskania żądanej długości, np addedZeroPadding(3,"a")==00a
     */
    private fun addZeroPadding(desiredLength: Int, string: String): String {
        var res = string
        while(res.length<desiredLength) {
            res = "0$res"
        }
        return res
    }

}