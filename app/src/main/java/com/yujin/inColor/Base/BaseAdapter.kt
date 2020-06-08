package com.yujin.inColor.Base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<itemType: Any> : RecyclerView.Adapter<BaseViewHolder<itemType>>() {
    abstract val layoutId: Int
    abstract fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder<itemType>

    private var itemList = ArrayList<itemType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<itemType> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(layoutId, parent, false)
        return viewHolder(viewType, view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder<itemType>, position: Int) {
        holder.onBind(position, itemList[position])
    }

    fun setList(list: ArrayList<itemType>) {
        itemList = list
        notifyDataSetChanged()
    }

    fun addList(list: ArrayList<itemType>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }
}