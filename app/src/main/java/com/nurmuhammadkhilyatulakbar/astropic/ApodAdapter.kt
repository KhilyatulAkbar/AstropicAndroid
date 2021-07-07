package com.nurmuhammadkhilyatulakbar.astropic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.item_apod.view.*

class ApodAdapter (val data: List<ApodModel>?, val context: Context) : RecyclerView.Adapter<ApodAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_apod, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data?.get(position))

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("apod", data?.get(position))
            context.startActivity(intent)
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(get: ApodModel?) {
            itemView.title.text = get?.title
            if(get?.mediaType.equals("image")){
                itemView.ivImage.load(get?.url)
            }
            else if(get?.mediaType.equals("video")){
                itemView.ivImage.load(get?.thumbnailUrl)
            }
        }

    }

}