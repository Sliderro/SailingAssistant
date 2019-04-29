package com.example.maciej.sailingassistant

import android.util.Log
import com.google.firebase.database.*

/**
 * Zarządza onlinową bazą danych firebase
 */
class FirebaseDatabaseManager {
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun fetchPoints(startDatetime: Datetime, endDatetime: Datetime){

        val query = db.child("boats").child("twR0OqZbv3b875Y8NSnjR39leZi2").orderByKey().startAt(startDatetime.toFormattedString()).endAt(endDatetime.toFormattedString())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("myDB",p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val points = ArrayList<Point?>()
                var currentPoint: Point?
                for(a in p0.children) {
                    Log.d("myDB",a.key)
                    currentPoint=a.getValue(Point::class.java)
                    currentPoint?.datetime = Datetime.fromString(a.key!!)
                    points.add(currentPoint)
                    Log.d("myDB",currentPoint.toString())
                }
                //TODO przekazanie utowrzonej listy punktów do main activity
            }
        })
    }
}