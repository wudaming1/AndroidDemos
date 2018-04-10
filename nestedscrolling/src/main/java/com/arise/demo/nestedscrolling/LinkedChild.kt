package com.arise.demo.nestedscrolling

/**
 * Created by wudaming on 2018/4/10.
 */
interface LinkedChild {

    var enableLinkedScroll: Boolean

    var enableLinkedMove: Boolean

    /**
     *  @param axis Flags consisting of {@link android.view.View#SCROLL_AXIS_HORIZONTAL},
     *                         {@link android.view.View#SCROLL_AXIS_VERTICAL} or both
     */
    fun acceptScroll(axis: Int): Boolean = false

    /**
     * @param x 可用的x轴距离
     * @param y 可用的y轴距离
     * @param consumed 用于回传消息，传进来时一定是二维数组[0,0]
     */
    fun onLinkedScroll(x: Int, y: Int, consumed: IntArray)

    fun onStopLinkedScroll() {}
}