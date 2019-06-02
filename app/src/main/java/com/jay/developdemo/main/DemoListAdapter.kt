package com.jay.developdemo.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.developdemo.R

/**
 * Created by Jay on 2018/11/2.
 */
class DemoListAdapter(private val demoList: List<DemoItem>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_demo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.bind(demoList[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return demoList.size
    }

    class DemoItem(var title: String?, var description: String?, var activity: Class<*>?)

    internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title: TextView = view.findViewById(R.id.tv_title)

        var description: TextView = view.findViewById(R.id.tv_description)

        fun bind(demo: DemoItem, onItemClickListener: OnItemClickListener) {
            title.text = demo.title
            description.text = demo.description
            itemView.setOnClickListener { onItemClickListener.onItemClick(demo) }
        }
    }

    interface OnItemClickListener {

        fun onItemClick(item: DemoItem)
    }
}
