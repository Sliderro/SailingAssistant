package com.example.maciej.sailingassistant

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    lateinit var map: GoogleMap
    private lateinit var mapView: MapView
    private val firebaseDatabaseManager = FirebaseDatabaseManager()
    var startDatetime: Datetime = Datetime.fromString("2019-05-02T20:27:19:154Z")
    var endDatetime: Datetime = Datetime.fromString("2019-05-02T20:27:27:215Z")


    override fun onMapClick(p0: LatLng?) {
        println(p0)
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) this.map = map
        map?.setMinZoomPreference(1.0f)
        val ny = LatLng(40.7143528, -74.0059731)
        map?.moveCamera(CameraUpdateFactory.newLatLng(ny))
        map?.addPolyline(PolylineOptions().add(ny, LatLng(40.7243528, -74.0159731)).width(5.0f).color(Color.RED))
        map?.setOnMapClickListener(this)

        firebaseDatabaseManager.fetchPoints(startDatetime, endDatetime, object : FirebaseCallback {
            override fun onCallback(points: ArrayList<Point?>) {
                Log.d("TAG", points.toString())
                println("test 1 = ${points[3]}")
                println("test 2 = ${points[3]!!.latitude}")

                var startLatitude = points[0]!!.latitude
                var startLongitude = points[0]!!.longitude
//                var endLatitude = points[1]!!.latitude - 15
//                var endLongitude = points[1]!!.longitude - 11

                val ny2 = LatLng(startLatitude, startLongitude)
                map?.moveCamera(CameraUpdateFactory.newLatLng(ny2))
//                map?.addPolyline(PolylineOptions().add(ny2, LatLng(endLatitude, endLongitude)).width(5.0f).color(Color.BLUE))

                for (i in 0..points.size-2 ){   //robocze rysowanie trasy
                    map?.addPolyline(PolylineOptions().add(LatLng(points[i]!!.latitude, points[i]!!.longitude), LatLng(points[i+1]!!.latitude, points[i+1]!!.longitude)).width(5.0f).color(Color.BLUE))
                }
            }
        })



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(null)
        mapView.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    fun launchDetailActivity(points: ArrayList<Point>) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putParcelableArrayListExtra("points", points)
        startActivity(detailIntent)
    }
}
