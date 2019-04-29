package com.example.maciej.sailingassistant

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    lateinit var map: GoogleMap
    private lateinit var mapView: MapView

    override fun onMapClick(p0: LatLng?) {
        println(p0)
    }

    override fun onMapReady(map: GoogleMap?) {
        if( map != null ) this.map = map
        map?.setMinZoomPreference(12.0f)
        val ny = LatLng(40.7143528, -74.0059731)
        map?.moveCamera(CameraUpdateFactory.newLatLng(ny))
        map?.addPolyline(PolylineOptions().add(ny, LatLng(40.7243528, -74.0159731)).width(5.0f).color(Color.RED))
        map?.setOnMapClickListener(this)
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

    fun launchDetailActivity(points : ArrayList<Point>) {
        val detailIntent = Intent(this,DetailActivity::class.java)
        detailIntent.putParcelableArrayListExtra("points",points)
        startActivity(detailIntent)
    }
}
