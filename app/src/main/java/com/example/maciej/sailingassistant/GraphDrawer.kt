package com.example.maciej.sailingassistant

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class GraphDrawer(context: Context, attributeSet: AttributeSet?)
    : View(context, attributeSet) {

    var points = ArrayList<Point>()
    var info = ""
    private var max = 0.0
    private val paintArray = Array(8) { Paint() }
    private fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

    init {
        paintArray[0].setARGB(255, 255, 0, 0)
        paintArray[1].setARGB(255, 0, 255, 0)
        paintArray[2].setARGB(255, 0, 0, 255)
        paintArray[3].setARGB(255, 255, 255, 0)
        paintArray[4].setARGB(255, 0, 255, 255)
        paintArray[5].setARGB(255, 255, 0, 255)
        paintArray[6].setARGB(255, 0, 0, 0)
        paintArray[7].setARGB(255, 0, 0, 0)
        paintArray[7].textSize = 20f
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val startY = 10f
        val startX = 40f.toPx()
        val stopY = height.toFloat() - 1
        val stopX = width.toFloat() - 1
        val middleY = height.toFloat() / 2
        val step = (stopX - startX) / points.size

        canvas.drawLine(startX, startY, startX, stopY, paintArray[6])
        if (info == "accelerometer" || info == "gyroscope" || info == "inclinations")
            canvas.drawLine(startX, middleY, stopX, middleY, paintArray[6])
        else canvas.drawLine(startX, stopY, stopX, stopY, paintArray[6])

        when (info) {
            "windSpeed" -> {
                windSpeedDraw(startX, middleY, stopY, step, canvas)
            }
            "tensometers" -> {
                tensometersDraw(startX, middleY, stopY, step, canvas)
            }
            "accelerometer" -> {
                acceleratorDraw(startX, middleY, stopY, step, canvas)
            }
            "gyroscope" -> {
                gyroscopeDraw(startX, middleY, stopY, step, canvas)
            }
            "inclinations" -> {
                inclinationsDraw(startX, middleY, stopY, step, canvas)
            }
            "windDirection" -> {
                windDirectionDraw(startX, middleY, stopY, step, canvas)
            }
            "speed" -> {
                speedDraw(startX, middleY, stopY, step, canvas)
            }
            else -> println("nie dziala")
        }
    }

    /** Funkcja zawraca zaokrągloną wartość, żeby maxem nie była jakaś dziwna wartość
     */
    private fun calcMax(max: Double): Double {
        var temp = max
        var counter = 0
        while (temp > 10.0) {
            temp /= 10.0
            counter++
        }
        temp = when {
            temp > 5.0 -> 10.0
            temp > 2.0 -> 5.0
            else -> 2.0
        }
        for (i in 0 until counter) {
            temp *= 10.0
        }
        return temp
    }

    private fun windSpeedDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        for (i in 0 until points.size) if (max < points[i].windSpeed) max = points[i].windSpeed
        max = calcMax(max)
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val y1 = (stopY - (stopY / max) * points[i].windSpeed).toFloat()
            val x2 = startX + step * (i + 1)
            val y2 = (stopY - (stopY / max) * points[i + 1].windSpeed).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paintArray[0])
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("${max / 2}", 0f, middleY + 10, paintArray[7])
        canvas.drawText("0", 0f, stopY, paintArray[7])
    }

    private fun tensometersDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        for (i in 0 until points.size) {
            for (j in 0 until points[i].tensometers.size) {
                if (max < points[i].tensometers[j]) max = points[i].tensometers[j]
            }
        }
        max = calcMax(max)
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val first = FloatArray(points[i].tensometers.size)
            val second = FloatArray(points[i].tensometers.size)
            for (j in 0 until points[i].tensometers.size) {
                first[j] = (stopY - (stopY / max) * points[i].tensometers[j]).toFloat()
                second[j] = (stopY - (stopY / max) * points[i + 1].tensometers[j]).toFloat()
                canvas.drawLine(x1, first[j], x2, second[j], paintArray[j])
            }
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("${max / 2}", 0f, middleY + 10, paintArray[7])
        canvas.drawText("0", 0f, stopY, paintArray[7])
    }

    private fun acceleratorDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        for (i in 0 until points.size) {
            if (max < abs(points[i].accelerometer["x"]!!))
                max = abs(points[i].accelerometer["x"]!!)
            if (max < abs(points[i].accelerometer["y"]!!))
                max = abs(points[i].accelerometer["y"]!!)
            if (max < abs(points[i].accelerometer["z"]!!))
                max = abs(points[i].accelerometer["z"]!!)
        }
        max = calcMax(max)
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val yx1 = (middleY - (middleY / max) * points[i].accelerometer["x"]!!).toFloat()
            val yx2 = (middleY - (middleY / max) * points[i + 1].accelerometer["x"]!!).toFloat()
            val yy1 = (middleY - (middleY / max) * points[i].accelerometer["y"]!!).toFloat()
            val yy2 = (middleY - (middleY / max) * points[i + 1].accelerometer["y"]!!).toFloat()
            val yz1 = (middleY - (middleY / max) * points[i].accelerometer["z"]!!).toFloat()
            val yz2 = (middleY - (middleY / max) * points[i + 1].accelerometer["z"]!!).toFloat()
            canvas.drawLine(x1, yx1, x2, yx2, paintArray[0])
            canvas.drawLine(x1, yy1, x2, yy2, paintArray[1])
            canvas.drawLine(x1, yz1, x2, yz2, paintArray[2])
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("0", 0f, middleY + 10, paintArray[7])
        canvas.drawText("${0 - max}", 0f, stopY, paintArray[7])
    }

    private fun gyroscopeDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        max = 90.0
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val yx1 = (middleY - (middleY / max) * points[i].gyroscope["x"]!!).toFloat()
            val yx2 = (middleY - (middleY / max) * points[i + 1].gyroscope["x"]!!).toFloat()
            val yy1 = (middleY - (middleY / max) * points[i].gyroscope["y"]!!).toFloat()
            val yy2 = (middleY - (middleY / max) * points[i + 1].gyroscope["y"]!!).toFloat()
            val yz1 = (middleY - (middleY / max) * points[i].gyroscope["z"]!!).toFloat()
            val yz2 = (middleY - (middleY / max) * points[i + 1].gyroscope["z"]!!).toFloat()
            canvas.drawLine(x1, yx1, x2, yx2, paintArray[0])
            canvas.drawLine(x1, yy1, x2, yy2, paintArray[1])
            canvas.drawLine(x1, yz1, x2, yz2, paintArray[2])
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("0", 0f, middleY + 10, paintArray[7])
        canvas.drawText("${0 - max}", 0f, stopY, paintArray[7])
    }

    private fun inclinationsDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        max = 90.0
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val first = FloatArray(points[i].inclinations.size)
            val second = FloatArray(points[i].inclinations.size)
            for (j in 0 until points[i].inclinations.size) {
                first[j] = (middleY - (middleY / max) * points[i].inclinations[j]).toFloat()
                second[j] = (middleY - (middleY / max) * points[i + 1].inclinations[j]).toFloat()
                canvas.drawLine(x1, first[j], x2, second[j], paintArray[j])
            }
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("0", 0f, middleY + 10, paintArray[7])
        canvas.drawText("${0 - max}", 0f, stopY, paintArray[7])
    }

    private fun windDirectionDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        max = 360.0
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val y1 = (stopY - (stopY / max) * points[i].windDirection).toFloat()
            val y2 = (stopY - (stopY / max) * points[i + 1].windDirection).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paintArray[0])
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("${max / 2}", 0f, middleY + 10, paintArray[7])
        canvas.drawText("0", 0f, stopY, paintArray[7])
    }

    private fun speedDraw(startX: Float, middleY: Float, stopY: Float, step: Float, canvas: Canvas) {
        for (i in 0 until points.size) if (max < points[i].speed) max = points[i].speed
        max = calcMax(max)
        for (i in 0 until points.size - 1) {
            val x1 = startX + step * i
            val x2 = startX + step * (i + 1)
            val y1 = (stopY - (stopY / max) * points[i].speed).toFloat()
            val y2 = (stopY - (stopY / max) * points[i + 1].speed).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paintArray[0])
        }
        canvas.drawText("$max", 0f, 20f, paintArray[7])
        canvas.drawText("${max / 2}", 0f, middleY + 10, paintArray[7])
        canvas.drawText("0", 0f, stopY, paintArray[7])
    }
}