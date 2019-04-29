package com.example.maciej.sailingassistant

import android.util.Log
import com.google.firebase.database.*

class FirebaseDatabaseManager {
    private val db: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun fetchPoints(startDatetime: String, endDatetime: String){

        val query = db.child("boats").child("twR0OqZbv3b875Y8NSnjR39leZi2")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("myDB",p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                var point: Point?
                for(a in p0.children) {
                    Log.d("myDB",a.key)
                    point=a.getValue(Point::class.java)
                    point?.datetime = Datetime.fromString(a.key!!)
                    Log.d("myDB",point.toString())
                }
                Log.d("myDB","")
            }
        })
    }
}