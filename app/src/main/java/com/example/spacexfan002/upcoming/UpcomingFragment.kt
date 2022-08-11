package com.example.spacexfan002.upcoming

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.R
import com.example.spacexfan002.adapter.SpaceXListAdapter
import com.example.spacexfan002.data.SpaceXModel
import com.example.spacexfan002.databinding.FragmentRocketsBinding
import com.example.spacexfan002.databinding.FragmentUpcomingBinding
import com.example.spacexfan002.detail.RocketDetailsFragment
import com.example.spacexfan002.detail.UpcomingDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.favorite.loginFragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.spacex_list.*

class UpcomingFragment : Fragment(), SpaceXListAdapter.Listener {
    private val bundle = Bundle()
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: SpaceXListAdapter
    private var viewModel: UpcomingViewModel? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        viewModel?.makeAPICall()

        binding.backButton.setOnClickListener() {
            auth.signOut()
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
    }

    private fun initRecyclerView() {
        upcommingRecyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = SpaceXListAdapter( this)
        upcommingRecyclerAdapter.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {

        viewModel?.getAllList()?.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setSpacexList(it.filter { List -> List.upcoming!! })
                recyclerAdapter.notifyDataSetChanged()
            /*    binding.upcommingRecyclerAdapter.also { recycler->
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                   recycler.adapter = SpaceXListAdapter(it.filter { it ->
                        it.upcoming == true
                    }, this)*/


            } else {
                Toast.makeText(context, "Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        }
        /*
        viewModel?.getLiveDataOBServer()?.observe(viewLifecycleOwner) {
            if ( spacexModel != null) {
                recyclerAdapter.setSpacexList(spacexModel.filter{it.upcoming})
                recyclerAdapter.notifyDataSetChanged()

            } else {
                Log.d("Debug h", "line 56")
            }
        }*/
    }

    /*
        override fun onItemClick(item: Favorites) {

            bundle.putString("upcomingName",item.name)
            bundle.putString("upcomingDate", item.date_local)
            bundle.putInt("upcomingFlight",item.flight_number)
            bundle.putString("upcomingID",item.id)
            bundle.putString("upcomingPrecision",item.date_precision)
            bundle.putString("upcomingImage",item.links?.patch?.small)

            bundle.putSerializable("spaceModel", spaceXModel)
            val fragment = UpcomingDetailsFragment()

            fragment.arguments = bundle

            (activity as MainActivity).replaceFragment(fragment)
        }

        override fun onCheckedClick(checkBox: CheckBox, spaceXModel: SpaceXModel) {

            if (favBtn.isChecked) {
                println("")
            }
        }
    */
    override fun onItemClick(spaceXModel: Favorites) {
        val bundle = Bundle()
        bundle.putSerializable("SpaceXModelUpcoming", spaceXModel)
        val fragment = UpcomingDetailsFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    override fun onCheckedClick(checkBox: CheckBox, spaceXModel: Favorites) {
        TODO("Not yet implemented")
    }


}