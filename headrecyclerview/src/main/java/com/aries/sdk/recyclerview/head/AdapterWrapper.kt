package com.aries.sdk.recyclerview.head

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.support.v4.util.SparseArrayCompat
import android.view.View
import com.aries.sdk.recyclerview.ViewHolder


/**
 * Created by wudaming on 2018/4/9.
 */
class AdapterWrapper<in T:RecyclerView.ViewHolder>(adapter: RecyclerView.Adapter<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val BASE_ITEM_TYPE_HEADER = 100000
    private val BASE_ITEM_TYPE_FOOTER = 200000

    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFootViews = SparseArrayCompat<View>()

    private val mInnerAdapter: RecyclerView.Adapter<T> = adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (mHeaderViews.get(viewType) != null) {

            return ViewHolder.createViewHolder(parent.context, mHeaderViews.get(viewType))

        } else if (mFootViews.get(viewType) != null) {
            return ViewHolder.createViewHolder(parent.context, mFootViews.get(viewType))
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        }
        if (isFooterViewPos(position)) {
            return
        }
        mInnerAdapter.onBindViewHolder(holder as T, position - getHeadersCount())
    }

    override fun getItemCount(): Int {
        return getHeadersCount() + getFootersCount() + getRealItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount())
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount())
    }

    private fun getRealItemCount(): Int {
        return mInnerAdapter.itemCount
    }

    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    fun addFootView(view: View) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

    fun getHeadersCount(): Int {
        return mHeaderViews.size()
    }

    fun getFootersCount(): Int {
        return mFootViews.size()
    }

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < getHeadersCount()
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= getHeadersCount() + getRealItemCount()
    }

}