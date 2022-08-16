package com.example.spacexfan002.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.adapter.ImageContainerAdapter
import com.example.spacexfan002.databinding.FragmentDetailsBinding
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.rockets.RocketViewModel
import com.example.spacexfan002.rockets.RocketsFragment
import kotlinx.android.synthetic.main.fragment_details.*


class RocketDetailsFragment : Fragment()  {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var viewModel: RocketViewModel? = null
    private var updateFavorites: Favorites? = null
    private lateinit var recyclerAdapter: ImageContainerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RocketViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spaceXModel = this.arguments?.getSerializable("SpaceXModel") as Favorites
        super.onViewCreated(view, savedInstanceState)
        rocketName.text = "NAME: " + spaceXModel.name
        Glide.with(this).load(spaceXModel.img).into(imageView)
        textView2.text = "Details: " + spaceXModel.details
        textView.text = "Flight Number: " + spaceXModel.flight_number.toString()
        textView4.text = "Date Local: " + spaceXModel.date_local.toString()

        binding.favBtn.setOnCheckedChangeListener { _, _ ->
            favChecked(spaceXModel)
        }
        binding.favBtn.isChecked = spaceXModel.favorite == true

        binding.backButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RocketsFragment())
        }
        initRecyclerView(spaceXModel.original)
        initObservers(spaceXModel.original)
    }

    private fun favChecked(favorites: Favorites) {
        updateFavorites = if (binding.favBtn.isChecked) {
            Favorites(
                favorites.id,
                favorites.name,
                favorites.img,
                true,
                favorites.details,
                favorites.upcoming,
                favorites.date_precision,
                favorites.date_local,
                favorites.flight_number,
                favorites.original
            )
        } else {
            Favorites(
                favorites.id,
                favorites.name,
                favorites.img,
                false,
                favorites.details,
                favorites.upcoming,
                favorites.date_precision,
                favorites.date_local,
                favorites.flight_number,
                favorites.original
            )
        }
        viewModel?.updateFavorite(updateFavorites!!)
    }

    private fun initRecyclerView(favorites: ArrayList<String>) {
        binding.recyclerViewDetails.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerAdapter = ImageContainerAdapter(favorites)
        recyclerViewDetails.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers(favorites: ArrayList<String>) {
        viewModel?.getAllList()?.observe(viewLifecycleOwner) {

            if (it != null) {
                recyclerAdapter.setSpacexList(favorites)
                recyclerAdapter.notifyDataSetChanged()
            }
            /* binding.recyclerViewRocket.also { recycler->
                 recycler.layoutManager = LinearLayoutManager(requireContext())
                 recycler.adapter = SpaceXListAdapter(it.filter { it ->
                 it.upcoming == false

                 }, this)
             }*/

        }
    }


}
