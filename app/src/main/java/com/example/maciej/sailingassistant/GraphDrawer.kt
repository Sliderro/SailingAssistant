package com.example.maciej.sailingassistant

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.example.maciej.sailingassistant.FirebaseDatabaseManager.points

class GraphDrawer(context: Context, attributeSet: AttributeSet?)
    : View(context,attributeSet){

    var points = ArrayList<Point>()
    var max = 0.0
    var info = ""
    fun Float.toPx() : Float = (this * Resources.getSystem().displayMetrics.density)

    override fun draw(canvas: Canvas){


        super.draw(canvas)
        val startY = 0f.toPx()
        val startX = 0f.toPx()
        val stopY = height.toFloat() - 1
        val stopX = width.toFloat() - 1
        val step = stopX/points.size
        val black = Paint()
        val red = Paint()
        val green = Paint()
        val blue = Paint()
        val yellow = Paint()
        val aqua = Paint()
        val fuchsia = Paint()
        black.setARGB(255,0,0,0)
        red.setARGB(255,255,0,0)
        green.setARGB(255,0,255,0)
        blue.setARGB(255,0,0,255)
        yellow.setARGB(255,255,255,0)
        aqua.setARGB(255,0,255,255)
        fuchsia.setARGB(255,255,0,255)
        canvas.drawLine(startX,startY,startX,stopY,black)
        canvas.drawLine(startX,stopY,stopX,stopY,black)
        if(info == "windSpeed"){
            for(i in 0 until points.size) if(max < points[i].windSpeed) max = points[i].windSpeed
            for(i in 0 until points.size - 1){
                val x1 = step * i
                val y1 = (stopY - (stopY / max) * points[i].windSpeed).toFloat()
                val x2 = step * (i + 1)
                val y2 = (stopY - (stopY / max) * points[i+1].windSpeed).toFloat()
                canvas.drawLine(x1,y1,x2,y2,red)
            }
        } else if (info == "tensometers") {
            for(i in 0 until points.size) {
                for (j in 0 until points[i].tensometers.size) {
                    if(max < points[i].tensometers[j]) max = points[i].tensometers[j]
                }
            }
            for(i in 0 until points.size - 1){
                val x1 = step * i
                val x2 = step * (i+1)
                val y01 = (stopY - (stopY/max) * points[i].tensometers[0]).toFloat()
                val y02 = (stopY - (stopY/max) * points[i+1].tensometers[0]).toFloat()
                val y11 = (stopY - (stopY/max) * points[i].tensometers[1]).toFloat()
                val y12 = (stopY - (stopY/max) * points[i+1].tensometers[1]).toFloat()
                val y21 = (stopY - (stopY/max) * points[i].tensometers[2]).toFloat()
                val y22 = (stopY - (stopY/max) * points[i+1].tensometers[2]).toFloat()
                val y31 = (stopY - (stopY/max) * points[i].tensometers[3]).toFloat()
                val y32 = (stopY - (stopY/max) * points[i+1].tensometers[3]).toFloat()
                val y41 = (stopY - (stopY/max) * points[i].tensometers[4]).toFloat()
                val y42 = (stopY - (stopY/max) * points[i+1].tensometers[4]).toFloat()
                val y51 = (stopY - (stopY/max) * points[i].tensometers[5]).toFloat()
                val y52 = (stopY - (stopY/max) * points[i+1].tensometers[5]).toFloat()
                canvas.drawLine(x1,y01,x2,y02,red)
                canvas.drawLine(x1,y11,x2,y12,green)
                canvas.drawLine(x1,y21,x2,y22,blue)
                canvas.drawLine(x1,y31,x2,y32,yellow)
                canvas.drawLine(x1,y41,x2,y42,aqua)
                canvas.drawLine(x1,y51,x2,y52,fuchsia)
            }
        }
    }


}