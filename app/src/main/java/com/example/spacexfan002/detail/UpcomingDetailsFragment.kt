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
import kotlinx.android.synthetic.main.fragment_details.imageView
import kotlinx.android.synthetic.main.fragment_upcoming_details.*

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
        upcomingID.text = "ID: " + args.id
        upcomingFlight.text = "Flight Number: " + args.flight_number
        Glide.with(this).load(args.img).into(imageView)
        upcomingDate.text = "Date Local: " + args.date_local
        upcomingPrecision.text = "Date Precision: " + args.date_precision
        upcomingName.text = "NAME: " + args.name
        title.text = args.name
        binding.backButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(UpcomingFragment())

        }

        /*
            val spaceXModel = args?.getSerializable("spaceModel")
            upcomingName.text = "NAME: " + args?.getString("upcomingName")
            upcomingPrecision.text = "Date Precision: " + args?.getString("upcomingPrecision")
            upcomingDate.text = "Date Local: " + args?.getString("upcomingDate")
            Glide.with(this).load(args?.getString("upcomingImage")).into(imageView)
            upcomingFlight.text = "Flight Number: " + args?.getInt("upcomingFlight")
            upcomingID.text = "ID: " + args?.getString("upcomingID")


*/

    }
}