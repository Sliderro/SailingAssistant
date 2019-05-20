package com.example.maciej.sailingassistant

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class GraphDrawer(context: Context, attributeSet: AttributeSet?)
    : View(context,attributeSet){

    var points = ArrayList<Point>()
    var max = 0.0
    var info = ""
    //fun Float.toPx() : Float = (this * Resources.getSystem().displayMetrics.density)

    override fun draw(canvas: Canvas){


        super.draw(canvas)
        val startY = 1f
        val startX = 1f
        val stopY = height.toFloat() - 1
        val stopX = width.toFloat() - 1
        val middleY = height.toFloat()/2
        val step = (stopX - startX)/points.size
        val black = Paint()
        val paintArray = Array(6) {Paint()}
        black.setARGB(255,0,0,0)
        paintArray[0].setARGB(255,255,0,0)
        paintArray[1].setARGB(255,0,255,0)
        paintArray[2].setARGB(255,0,0,255)
        paintArray[3].setARGB(255,255,255,0)
        paintArray[4].setARGB(255,0,255,255)
        paintArray[5].setARGB(255,255,0,255)
        canvas.drawLine(startX,startY,startX,stopY,black)
        if(info == "accelerometer" || info == "gyroscope" || info == "inclinations")
            canvas.drawLine(startX,middleY,stopX,middleY,black)
        else canvas.drawLine(startX,stopY,stopX,stopY,black)
        when (info) {
            "windSpeed" -> {
                for(i in 0 until points.size) if(max < points[i].windSpeed) max = points[i].windSpeed
                max = calcMax(max)
                for(i in 0 until points.size - 1){
                    val x1 = step * i
                    val y1 = (stopY - (stopY / max) * points[i].windSpeed).toFloat()
                    val x2 = step * (i + 1)
                    val y2 = (stopY - (stopY / max) * points[i+1].windSpeed).toFloat()
                    canvas.drawLine(x1,y1,x2,y2,paintArray[0])
                }
            }
            "tensometers" -> {
                for(i in 0 until points.size) {
                    for (j in 0 until points[i].tensometers.size) {
                        if(max < points[i].tensometers[j]) max = points[i].tensometers[j]
                    }
                }
                max = calcMax(max)
                for(i in 0 until points.size - 1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val first = FloatArray(points[i].tensometers.size)
                    val second = FloatArray(points[i].tensometers.size)
                    for(j in 0 until points[i].tensometers.size){
                        first[j] = (stopY - (stopY/max) * points[i].tensometers[j]).toFloat()
                        second[j] = (stopY - (stopY/max) * points[i+1].tensometers[j]).toFloat()
                        canvas.drawLine(x1,first[j],x2,second[j],paintArray[j])
                    }
                }
            }
            "accelerometer" -> {
                for(i in 0 until points.size) {
                    if(max < abs(points[i].accelerometer["x"]!!))
                        max = abs(points[i].accelerometer["x"]!!)
                    if(max < abs(points[i].accelerometer["y"]!!))
                        max = abs(points[i].accelerometer["y"]!!)
                    if(max < abs(points[i].accelerometer["z"]!!))
                        max = abs(points[i].accelerometer["y"]!!)
                }
                max = calcMax(max)
                for(i in 0 until points.size -1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val yx1 = (middleY - (middleY/max) * points[i].accelerometer["x"]!!).toFloat()
                    val yx2 = (middleY - (middleY/max) * points[i+1].accelerometer["x"]!!).toFloat()
                    val yy1 = (middleY - (middleY/max) * points[i].accelerometer["y"]!!).toFloat()
                    val yy2 = (middleY - (middleY/max) * points[i+1].accelerometer["y"]!!).toFloat()
                    val yz1 = (middleY - (middleY/max) * points[i].accelerometer["z"]!!).toFloat()
                    val yz2 = (middleY - (middleY/max) * points[i+1].accelerometer["z"]!!).toFloat()
                    canvas.drawLine(x1,yx1,x2,yx2,paintArray[0])
                    canvas.drawLine(x1,yy1,x2,yy2,paintArray[1])
                    canvas.drawLine(x1,yz1,x2,yz2,paintArray[2])
                }
            }
            "gyroscope" -> {
                max = 90.0
                for(i in 0 until points.size -1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val yx1 = (middleY - (middleY/max) * points[i].gyroscope["x"]!!).toFloat()
                    val yx2 = (middleY - (middleY/max) * points[i+1].gyroscope["x"]!!).toFloat()
                    val yy1 = (middleY - (middleY/max) * points[i].gyroscope["y"]!!).toFloat()
                    val yy2 = (middleY - (middleY/max) * points[i+1].gyroscope["y"]!!).toFloat()
                    val yz1 = (middleY - (middleY/max) * points[i].gyroscope["z"]!!).toFloat()
                    val yz2 = (middleY - (middleY/max) * points[i+1].gyroscope["z"]!!).toFloat()
                    canvas.drawLine(x1,yx1,x2,yx2,paintArray[0])
                    canvas.drawLine(x1,yy1,x2,yy2,paintArray[1])
                    canvas.drawLine(x1,yz1,x2,yz2,paintArray[2])
                }
            }
            "inclinations" -> {
                max = 90.0
                for(i in 0 until points.size - 1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val first = FloatArray(points[i].inclinations.size)
                    val second = FloatArray(points[i].inclinations.size)
                    for(j in 0 until points[i].inclinations.size){
                        first[j] = (middleY - (middleY/max) * points[i].inclinations[j]).toFloat()
                        second[j] = (middleY - (middleY/max) * points[i+1].inclinations[j]).toFloat()
                        canvas.drawLine(x1,first[j],x2,second[j],paintArray[j])
                    }
                }
            }
            "windDirection" -> {
                max = 360.0
                for(i in 0 until points.size -1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val y1 = (stopY - (stopY / max) * points[i].windDirection).toFloat()
                    val y2 = (stopY - (stopY / max) * points[i+1].windDirection).toFloat()
                    canvas.drawLine(x1,y1,x2,y2,paintArray[0])
                }
            }
            "speed" -> {
                for(i in 0 until points.size) if(max < points[i].speed) max = points[i].speed
                max = calcMax(max)
                for(i in 0 until points.size -1){
                    val x1 = step * i
                    val x2 = step * (i+1)
                    val y1 = (stopY - (stopY / max) * points[i].speed).toFloat()
                    val y2 = (stopY - (stopY / max) * points[i+1].speed).toFloat()
                    canvas.drawLine(x1,y1,x2,y2,paintArray[0])
                }
            }
            else -> println("nie dziala")
        }
    }

    /** Funkcja zawraca zaokrągloną wartość, żeby maxem nie była jakaś dziwna wartość
    */
    private fun calcMax(max: Double) : Double{
        var temp = max
        var counter = 0
        while(temp > 10.0){
            temp /= 10.0
            counter++
        }
        temp = when {
            temp > 2.0 -> 5.0
            temp > 5.0 -> 10.0
            else -> 2.0
        }
        for(i in 0 until counter){
            temp *= 10.0
        }
        return temp
    }
}