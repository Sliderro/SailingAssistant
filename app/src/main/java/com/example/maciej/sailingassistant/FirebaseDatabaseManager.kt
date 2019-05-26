package com.example.maciej.sailingassistant

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


/**
 * Zarządza onlinową bazą danych firebase
 */
object FirebaseDatabaseManager {
    private val db: DatabaseReference
    var points = ArrayList<Point?>()

    init {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        db = FirebaseDatabase.getInstance().reference
    }

    /**
     * pobiera punkty z danego dnia zadanego stringiem w postaci: yyyy-mm-dd
     */
    fun fetchPoints(dateString: String, firebaseCallback: FirebaseCallback) {
        val fetchDate = if(dateString =="") "a" else dateString

        val query = db.child("logs").child("twR0OqZbv3b875Y8NSnjR39leZi2").child(fetchDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("myDB", p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                points.clear()
                var currentPoint: Point?
                for (a in p0.children) {
                    Log.d("myDB", a.key)
                    currentPoint = a.getValue(Point::class.java)
                    currentPoint?.datetime = Datetime.fromString(a.key!!)
                    points.add(currentPoint)
                    Log.d("myDB", currentPoint.toString())
                }
                firebaseCallback.onPointsFetched()
            }
        })

    }

}