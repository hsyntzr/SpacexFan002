package com.example.spacexfan002.rockets

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.adapter.SpaceXListAdapter
import com.example.spacexfan002.databinding.FragmentRocketsBinding
import com.example.spacexfan002.detail.RocketDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.favorite.loginFragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_rockets.*


class RocketsFragment : Fragment(), SpaceXListAdapter.Listener {
    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!
    var viewModel: RocketViewModel? = null
    private lateinit var recyclerAdapter: SpaceXListAdapter

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RocketViewModel::class.java]
        (activity as MainActivity).bottomNavigationView.visibility = View.VISIBLE
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRocketsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        binding.backButton.setOnClickListener() {
            auth.signOut()
            (activity as MainActivity).replaceFragment(LoginFragment())
        }

        viewModel?.makeAPICall()

    }

    private fun initRecyclerView() {
        recyclerViewRocket.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = SpaceXListAdapter(this)
        recyclerViewRocket.adapter = recyclerAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {

            viewModel?.getAllList()?.observe(viewLifecycleOwner) {
                if (it != null) {
                    recyclerAdapter.setSpacexList(it.filter { spacexmodel -> !spacexmodel.upcoming!! })
                    recyclerAdapter.notifyDataSetChanged()
                }
               /* binding.recyclerViewRocket.also { recycler->
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                    recycler.adapter = SpaceXListAdapter(it.filter { it ->
                    it.upcoming == false

                    }, this)
                }*/
            else {
                Toast.makeText(context, "Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onItemClick(spaceXModel: Favorites) {
        val bundle = Bundle()
        bundle.putSerializable("SpaceXModel", spaceXModel)
        val fragment = RocketDetailsFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    override fun onCheckedClick(checkBox: CheckBox, spaceXModel: Favorites) {
        Log.d("Fatih","onCheckboxClick worked")
        spaceXModel.favorite = checkBox.isChecked
        val updateFavorites = Favorites(spaceXModel.id,spaceXModel.name,spaceXModel.img,checkBox.isChecked,spaceXModel.details,spaceXModel.upcoming,spaceXModel.date_precision,spaceXModel.date_local,spaceXModel.flight_number)
        viewModel?.updateFavorite(updateFavorites)
    }

}

