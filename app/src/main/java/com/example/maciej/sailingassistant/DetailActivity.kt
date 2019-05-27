package com.example.maciej.sailingassistant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import java.util.*
import android.support.v4.content.LocalBroadcastManager
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var points: ArrayList<Point>
    private lateinit var centerPoint: Point
    private lateinit var centerDataTime: Datetime
    var numberOfNeighborsDetail = MainActivity.numberOfNeighbors

    private lateinit var mygestureDetector: GestureDetector
    var direction = ""

    lateinit var map: GoogleMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        /**
         * działa tak, że ZAWSZE PRZESYŁA 301 PUNKTÓW i działa
         * na razie przy dojściu do końca odcinka wytraca się punkt od którego zaczęliśmy;
         * później można ewentualnie zaimplementować zapamiętanie przedostatniego punktu środkowego
         */
        points = intent.getParcelableArrayListExtra("points")
        wSpeed.points = points
        tenso.points = points
        wDirection.points = points
        speed.points = points
        acc.points = points
        incl.points = points
        gyroscope.points = points
        wSpeed.info = "windSpeed"
        tenso.info = "tensometers"
        wDirection.info = "windDirection"
        speed.info = "speed"
        acc.info = "accelerometer"
        incl.info = "inclinations"
        gyroscope.info = "gyroscope"

        //jeśli wszystkich danych jest mniej niż wskazano w MainActivity (300) to wyświetlamy je wszystkie i blokujemy przewijanie w bok
        numberOfNeighborsDetail = if (points.size >= MainActivity.numberOfNeighbors) MainActivity.numberOfNeighbors else points.size

        centerPoint = points[numberOfNeighborsDetail / 2]
        centerDataTime = centerPoint.datetime!!


        mapView = findViewById(R.id.mapView2)
        mapView.onCreate(null)
        mapView.getMapAsync(this)

        //wyswietlanie godziny
        tTime.text = getString(R.string.interval, points.first().datetime?.hour, points.first().datetime?.minute, points.first().datetime?.second,
                points.last().datetime?.hour, points.last().datetime?.minute, points.last().datetime?.second)

        mygestureDetector = GestureDetector(this@DetailActivity, MyGestureDetector())

        val touchListener = View.OnTouchListener { _, event ->
            mygestureDetector.onTouchEvent(event)
        }
        scrollView2.setOnTouchListener(touchListener)
    }

    /**
     * Obsluga mapy
     */
    override fun onMapClick(p0: LatLng?) {
        println(p0)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) this.map = map
        map?.setMinZoomPreference(17.0f)
        drawRoute(points)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    private fun drawRoute(points: ArrayList<Point>) {
        val startLatitude = points[numberOfNeighborsDetail / 2].latitude
        val startLongitude = points[numberOfNeighborsDetail / 2].longitude
        val startPosition = LatLng(startLatitude, startLongitude)
        map.moveCamera(CameraUpdateFactory.newLatLng(startPosition))
        object : Thread() {
            override fun run() {
                super.run()
                val p = PolylineOptions().width(5.0f).color(Color.BLUE)
                for (i in 0..(points.size - 2)) {
                    p.add(LatLng(points[i].latitude, points[i].longitude), LatLng(points[i + 1].latitude, points[i + 1].longitude))
                }
                runOnUiThread {
                    map.clear()
                    map.addPolyline(p)
                }
            }
        }.start()
    }


    /**
     * Klasa obsługująca gesty
     */
    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 200
        private val SWIPE_VELOCITY_THRESHOLD = 200

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {

            if (e1 != null && e2 != null) {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY) && numberOfNeighborsDetail >= MainActivity.numberOfNeighbors) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {     //sprawdza czy ruch był w lewo czy w prawo
                            onSwipeLeft()
                        } else {
                            onSwipeRight()
                        }
                        //wysłanie danych do MainActivity
                        Log.d("sender", "BROADCASTING message")
                        val intent = Intent("center_intent").putExtra("center", centerDataTime).putExtra("direction", direction)
                        LocalBroadcastManager.getInstance(this@DetailActivity).sendBroadcast(intent)
                        finish()
                    }
                } else {
                    Log.d("scroll", "SCROLL")
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }


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
