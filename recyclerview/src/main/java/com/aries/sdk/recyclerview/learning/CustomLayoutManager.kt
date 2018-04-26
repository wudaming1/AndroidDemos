package com.aries.sdk.recyclerview.learning

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Author wudaming
 * Created on 2018/4/26
 */
class CustomLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {

    }
}