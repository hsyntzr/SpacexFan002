package com.example.spacexfan002.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.databinding.FragmentUpcomingDetailsBinding
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.upcoming.UpcomingFragment

class UpcomingDetailsFragment : Fragment() {

    private var _binding: FragmentUpcomingDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = this.arguments?.getSerializable("SpaceXModelUpcoming") as Favorites

        binding.upcomingID.text = "ID: " + args.id
        binding.upcomingFlight.text = "Flight Number: " + args.flight_number
        Glide.with(this).load(args.img).into(binding.imageView)
        binding.upcomingDate.text = "Date Local: " + args.date_local
        binding.upcomingPrecision.text = "Date Precision: " + args.date_precision
        binding.upcomingName.text = "NAME: " + args.name
        binding.title.text = args.name
    }

    fun backButton(view: View) {
        view.setOnClickListener {
            (activity as MainActivity).replaceFragment(UpcomingFragment())
        }
    }

}