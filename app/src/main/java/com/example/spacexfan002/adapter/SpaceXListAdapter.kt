@file:Suppress("LocalVariableName")

package com.example.spacexfan002.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexfan002.databinding.SpacexListBinding
import com.example.spacexfan002.favorite.favdata.Favorites

class SpaceXListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<SpaceXListAdapter.myViewHolder>() {
    private var spacexList: List<Favorites>? = null

    interface Listener {
        fun onItemClick(spaceXModel: Favorites)
        fun onCheckedClick(checkBox: CheckBox, spaceXModel: Favorites)
    }

    fun setSpacexList(rockets: List<Favorites>) {
        this.spacexList = rockets
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myViewHolder {
        val binding = SpacexListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(spacexList?.get(position)!!, listener)
    }

    override fun getItemCount(): Int {
        return if (spacexList == null) {
            0
        } else {
            spacexList?.size!!
        }
    }

    @Suppress("ClassName")
    class myViewHolder(val binding: SpacexListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val rocketTitle: TextView = binding.rocketTitle
        private val rocketImage: ImageView = binding.rocketImage
        fun bind(data: Favorites, listener: Listener) {
            binding.favBtn.isChecked = data.favorite!!
            val ImageURL: String? = data.img
            if (ImageURL != null && !data.upcoming!!) {
                rocketTitle.text = data.name
                Glide.with(binding.root).load(data.img).into(rocketImage)
            } else {
                rocketTitle.text = data.name
                rocketImage.visibility = View.GONE
                binding.favBtn.visibility = View.GONE
            }
            if(data.favorite == true){
                binding.favBtn.isChecked = true
            }
            binding.root.setOnClickListener {
                listener.onItemClick(data)
            }
            binding.favBtn.setOnClickListener {
                data.favorite = !data.favorite!!
                listener.onCheckedClick(binding.favBtn, data)
            }
        }
    }
}