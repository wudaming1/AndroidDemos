package com.arise.android.demo

import android.graphics.Point
import android.util.Log

/**
 * 可持续移动线段，但是不记录历史信息。两次moveTo后方可使用。
 */
class MovableLine {

    private val READY = 2

    private val startPoint: Point = Point(0, 0)
    private val endPoint: Point = Point(0, 0)

    private var moveCount = 0

    fun getXOffset() = endPoint.x - startPoint.x

    fun getXOffsetAbsoluteValue() = {
        Math.abs(getXOffset())
    }

    fun getYOffset() = endPoint.y - startPoint.y

    fun getYOffsetAbsoluteValue() = {
        Math.abs(getYOffset())
    }

    fun moveTo(targetX: Int, targetY: Int) {
        startPoint.set(endPoint.x, endPoint.y)
        endPoint.set(targetX, targetY)
        moveCount++
    }

    fun moveTo(target: Point) {
        moveTo(target.x, target.y)
    }

    fun isReady() = moveCount >= READY

    fun reset() {
        moveCount = 0
        startPoint.set(0, 0)
        endPoint.set(0, 0)
    }
}