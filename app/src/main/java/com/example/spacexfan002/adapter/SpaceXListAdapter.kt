@file:Suppress("LocalVariableName")

package com.example.spacexfan002.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacexfan002.R
import com.example.spacexfan002.favorite.favdata.Favorites
import kotlinx.android.synthetic.main.spacex_list.view.*

class SpaceXListAdapter(private val spacexList: List<Favorites>?,private val listener: Listener) :
    RecyclerView.Adapter<SpaceXListAdapter.myViewHolder>() {

    interface Listener {
        fun onItemClick(spaceXModel: Favorites)
        fun onCheckedClick(checkBox: CheckBox, spaceXModel: Favorites)
    }

   /* fun setSpacexList(rockets: List<Favorites>) {
        this.spacexList = rockets
    }*/

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.spacex_list, parent, false)
        return myViewHolder(view)
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
    class myViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rocketTitle: TextView = view.rocketTitle
        private val rocketImage: ImageView = view.rocketImage
        private val favBtn: CheckBox = view.favBtn
        fun bind(data: Favorites, listener: Listener) {

            itemView.favBtn.isChecked = data.favorite!!

            val ImageURL: String? = data.img
            if (ImageURL != null && !data.upcoming!!) {
                rocketTitle.text = data.name
                Glide.with(itemView).load(data.img).into(rocketImage)

            } else {
                rocketTitle.text = data.name
                rocketImage.visibility = View.GONE
                itemView.favBtn.visibility = View.GONE
            }



            itemView.setOnClickListener {
                listener.onItemClick(data)
            }

            itemView.favBtn.setOnClickListener  {
                data.favorite = !data.favorite!!
                listener.onCheckedClick(itemView.favBtn, data)
            }
        }
    }


}