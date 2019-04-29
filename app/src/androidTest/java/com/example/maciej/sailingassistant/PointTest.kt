package com.example.maciej.sailingassistant

import android.os.Bundle
import org.junit.Assert.*
import org.junit.Test

class PointTest {
    @Test
    fun testParcelable() {
        val datetime = Datetime(1,2,3,4,5,6,7)
        val accelerometer = HashMap<String,Double>()
        accelerometer.set("x",0.1)
        accelerometer.set("y",0.2)
        accelerometer.set("z",0.3)
        val point1 = Point(datetime,11.0,12.0,13.0,14.0, doubleArrayOf(0.01,0.02,0.03,0.04,0.05,0.06), doubleArrayOf(1.01,1.02),accelerometer)
        val bundle = Bundle()
        bundle.putParcelable("point",point1)
        val point2 = bundle.getParcelable<Point>("point")

        assertTrue(point1==point2)
    }
}