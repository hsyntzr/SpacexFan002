package com.example.spacexfan002.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexfan002.R
import com.example.spacexfan002.databinding.ImageContainerBinding


class ImageContainerAdapter(spaceXListOriginal: List<String>) : RecyclerView.Adapter<ImageContainerAdapter.myViewHolder>() {

    private var spacexList = spaceXListOriginal


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val binding = ImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(spacexList[position])
    }

    override fun getItemCount(): Int {
        return spacexList.size
    }

    @Suppress("ClassName")
    class myViewHolder(binding: ImageContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
                Glide.with(itemView).load(data).into(itemView.findViewById(R.id.imageViewDetails))
        }
    }
}
