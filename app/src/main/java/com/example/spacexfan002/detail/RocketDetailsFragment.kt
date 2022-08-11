package com.example.spacexfan002.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Space
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.R
import com.example.spacexfan002.data.SpaceXModel
import com.example.spacexfan002.databinding.FragmentDetailsBinding
import com.example.spacexfan002.databinding.FragmentRocketsBinding
import com.example.spacexfan002.favorite.FavoriteViewModel
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.rockets.RocketViewModel
import com.example.spacexfan002.rockets.RocketsFragment
import com.example.spacexfan002.upcoming.UpcomingFragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.spacex_list.*


class RocketDetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private var viewModel: RocketViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    private var updateFavorites:Favorites? = null
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RocketViewModel::class.java]

        val spaceXModel = this.arguments?.getSerializable("SpaceXModel") as Favorites
        rocketName.text = "NAME: "+ spaceXModel.name
        Glide.with(this).load(spaceXModel.img).into(imageView)
        textView2.text = "Details: " + spaceXModel.details?.toString()
        textView.text = "Flight Number: " + spaceXModel.flight_number.toString()
        textView4.text = "Date Local: " + spaceXModel.date_local.toString()

        binding.favBtn.setOnCheckedChangeListener { compoundButton, b ->
            favchecked(spaceXModel)


        }

        binding.favBtn.isChecked = spaceXModel.favorite == true

        binding.backButton.setOnClickListener{
            (activity as MainActivity).replaceFragment(RocketsFragment())
        }
    }
    private fun favchecked(favorites: Favorites){
            if(binding.favBtn.isChecked) {
                updateFavorites = Favorites(favorites.id,favorites.name,favorites.img,true,favorites.details,favorites.upcoming,favorites.date_precision,favorites.date_local,favorites.flight_number)
            }else{
                updateFavorites = Favorites(favorites.id,favorites.name,favorites.img,false,favorites.details,favorites.upcoming,favorites.date_precision,favorites.date_local,favorites.flight_number)
            }
            viewModel?.updateFavorite(updateFavorites!!)
            Toast.makeText(requireContext(), favorites.favorite.toString(), Toast.LENGTH_LONG).show()

    }

}