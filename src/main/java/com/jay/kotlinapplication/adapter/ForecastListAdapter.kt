package com.jay.kotlinapplication.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by rocklu on 2017/5/28.
 */
class ForecastListAdapter(val listString :List<String>) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder?, p1: Int) {
        p0?.textView?.text = listString.get(p1)
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): ViewHolder {
        return ViewHolder(TextView(p0?.context))
    }

    override fun getItemCount(): Int {
        return listString.size
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}