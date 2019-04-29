package com.example.maciej.sailingassistant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {

    lateinit var points: ArrayList<Point>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        points = intent.getParcelableArrayListExtra("points")
    }
}
