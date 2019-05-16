package com.example.maciej.sailingassistant

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import java.util.*
import android.support.v4.content.LocalBroadcastManager
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    lateinit var points: ArrayList<Point>
    lateinit var centerPoint: Point
//    var centerPointString = ""
    lateinit var centerDataTime : Datetime

    var direction = ""
    val numberOfNeighbors = 300
    var width = 0
    var height = 0

    lateinit var mygestureDetector: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        /**
         * działa tak, że ZAWSZE PRZESYŁA 301 PUNKTÓW i działa
         * na razie przy dojściu do końca odcinka wytraca się punkt od którego zaczęliśmy;
         * później można ewentualnie zaimplementować zapamiętanie przedostatniego punktu środkowego
         */
        points = intent.getParcelableArrayListExtra("points")
        graphDrawer.points = points
        graphDrawer.info = "tensometers"
        centerPoint = points[numberOfNeighbors / 2]
//        centerPointString = centerPoint.datetime!!.toFormattedString()
        centerDataTime = centerPoint.datetime!!


        mygestureDetector = GestureDetector(this@DetailActivity, MyGestureDetector())

        val touchListener = View.OnTouchListener { v, event ->
            mygestureDetector.onTouchEvent(event)
        }
        scrollView2.setOnTouchListener(touchListener)
    }

    /**
     * Klasa obsługująca gesty
     */
    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 400
        private val SWIPE_VELOCITY_THRESHOLD = 400


        /*override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {

            if (e1 != null && e2 != null) {     //sprawdza czy ruch był w lewo czy w prawo
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeLeft()
                        } else {
                            onSwipeRight()
                        }
                    }
                }
            }
            textView.text = "points size = ${points.size}"

            //wysłąnie danych do MainActivity
            Log.d("sender", "BROADCASTING message")
            val intent = Intent("center_intent").putExtra("center", centerDataTime).putExtra("direction", direction)
            LocalBroadcastManager.getInstance(this@DetailActivity).sendBroadcast(intent)

            return super.onFling(e1, e2, velocityX, velocityY)
        }*/


        private fun onSwipeRight() {
            direction = "right"
            Log.e("ViewSwipe", "RIGHT")
        }

        private fun onSwipeLeft() {
            direction = "left"
            Log.e("ViewSwipe", "LEFT")
        }


    }



}
