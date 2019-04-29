package com.example.maciej.sailingassistant

import org.junit.Assert.*
import org.junit.Test

class DatetimeTest {
    @Test
    fun testCompare() {
        val datetime1 = Datetime(2000,12,1,13,21,30,541)
        val datetime2 = Datetime(2000,12,1,13,21,30,641)

        assertEquals(-1,datetime1.compareTo(datetime2))
        assertEquals(1,datetime2.compareTo(datetime1))

        val datetime3 = Datetime(2000,12,1,13,21,30,541)
        assertEquals(0,datetime1.compareTo(datetime3))
    }

    @Test
    fun testFromString() {
        val datetime = Datetime(2000,12,1,13,21,30,541)
        val string = "2000-12-01T13:21:30:541Z"
        val datetimeFromString = Datetime.fromString(string)
        assertEquals(0,datetime.compareTo(datetimeFromString))
    }
    @Test
    fun testToFormattedString() {
        val datetime = Datetime(2000,12,1,13,21,30,541)
        val string = "2000-12-01T13:21:30:541Z"
        val datetimeString = datetime.toFormattedString()

        assertTrue(datetimeString.contentEquals(string))
    }
}