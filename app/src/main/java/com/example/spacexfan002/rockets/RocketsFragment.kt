package com.example.spacexfan002.rockets

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.adapter.SpaceXListAdapter
import com.example.spacexfan002.data.SpaceXModel
import com.example.spacexfan002.databinding.FragmentRocketsBinding
import com.example.spacexfan002.detail.RocketDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.loginFragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class RocketsFragment : Fragment(), SpaceXListAdapter.Listener {
    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!
    private var viewModel: RocketViewModel? = null
    private lateinit var recyclerAdapter: SpaceXListAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var favorites: List<Favorites>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RocketViewModel::class.java]
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage
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
        binding.backButton.setOnClickListener {
            auth.signOut()
            (activity as MainActivity).replaceFragment(LoginFragment())
        }

        if ((activity as MainActivity).getSharedPref()){
            (activity as MainActivity).setSharedPref(false)
            viewModel?.makeAPICall()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewRocket.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = SpaceXListAdapter(this)
        binding.recyclerViewRocket.adapter = recyclerAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {

        viewModel?.allListLivedata?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerAdapter.setSpacexList(it.filter { spaceXModel -> !spaceXModel.upcoming!! })
                recyclerAdapter.notifyDataSetChanged()
            }
        })
        viewModel?.listenAllList(this.lifecycle, this.lifecycleScope)
    }


    override fun onItemClick(spaceXModel: Favorites) {
        val bundle = Bundle()
        bundle.putSerializable("SpaceXModel", spaceXModel)
        val fragment = RocketDetailsFragment()
        fragment.arguments = bundle
        (activity as MainActivity).replaceFragment(fragment)
    }

    override fun onCheckedClick(checkBox: CheckBox, spaceXModel: Favorites) {

        val favoriteMap = hashMapOf<String,Any>()
        favoriteMap["id"] = spaceXModel.id
        favoriteMap["favorite"] = checkBox.isChecked
        favoriteMap["name"] = spaceXModel.name!!
        favoriteMap["img"] = spaceXModel.img.toString()
        favoriteMap["details"] = spaceXModel.details.toString()
        favoriteMap["upcoming"] = spaceXModel.upcoming!!
        favoriteMap["date_precision"] = spaceXModel.date_precision!!
        favoriteMap["date_local"] = spaceXModel.date_local!!
        favoriteMap["flight_number"] = spaceXModel.flight_number!!
        favoriteMap["original"] = spaceXModel.original
        if (checkBox.isChecked) {
            firestore.collection("Favorites").document(spaceXModel.id).set(favoriteMap)
                .addOnSuccessListener {
                    Toast.makeText(context, "Favorilere Eklendi",Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage?.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }else{
            firestore.collection("Favorites").document(spaceXModel.id).delete().addOnSuccessListener {
                Toast.makeText(context, "Favoriden çıkarıldı", Toast.LENGTH_LONG).show()
            }
        }
        Log.d("Fatih", "onCheckboxClick worked")
        spaceXModel.favorite = checkBox.isChecked
        val updateFavorites = Favorites(
            spaceXModel.id,
            spaceXModel.name,
            spaceXModel.img,
            checkBox.isChecked,
            spaceXModel.details,
            spaceXModel.upcoming,
            spaceXModel.date_precision,
            spaceXModel.date_local,
            spaceXModel.flight_number,
            spaceXModel.original
        )
        viewModel?.updateFavorite(updateFavorites)
    }


}



