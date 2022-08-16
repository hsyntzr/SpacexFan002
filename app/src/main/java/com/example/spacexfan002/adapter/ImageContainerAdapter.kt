package com.example.spacexfan002.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexfan002.R
import kotlinx.android.synthetic.main.image_container.view.*

class ImageContainerAdapter(spaceXListOriginal: List<String>) : RecyclerView.Adapter<ImageContainerAdapter.myViewHolder>() {

    private var spacexList = spaceXListOriginal
    fun setSpacexList(rockets: List<String>) {
        this.spacexList = rockets
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(spacexList[position])
    }

    override fun getItemCount(): Int {

        return spacexList.size
    }

    @Suppress("ClassName")
    class myViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: String) {

                Glide.with(itemView).load(data).into(itemView.imageViewDetails)
                println(data)
        }
    }
}
