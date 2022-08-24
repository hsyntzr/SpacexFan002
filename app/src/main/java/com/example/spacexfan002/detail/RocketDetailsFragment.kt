package com.example.spacexfan002.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Suppress("UNREACHABLE_CODE")
class RocketDetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var viewModel: RocketViewModel? = null
    private var updateFavorites: Favorites? = null
    private lateinit var recyclerAdapter: ImageContainerAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var spaceXModel: Favorites

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RocketViewModel::class.java]
        firestore = Firebase.firestore
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

        spaceXModel = this.arguments?.getSerializable("SpaceXModel") as Favorites
        binding.rocketName.text = "NAME: " + spaceXModel.name
        Glide.with(this).load(spaceXModel.img).into(binding.imageView)
        binding.textView2.text = "Details: " + spaceXModel.details
        binding.textView.text = "Flight Number: " + spaceXModel.flight_number.toString()
        binding.textView4.text = "Date Local: " + spaceXModel.date_local.toString()

    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.favBtn.setOnCheckedChangeListener { _, _ ->
            favChecked(spaceXModel)
        }
        binding.favBtn.isChecked = spaceXModel.favorite == true

        binding.backButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RocketsFragment())
        }
        initRecyclerView(spaceXModel.original)
    }

    private fun favChecked(favorites: Favorites) {
        roomFavoriteUpdate(favorites)
        firebaseFavoriteUpdate(favorites)
    }

    private fun firebaseFavoriteUpdate(favorites: Favorites) {
        val favoriteMap = hashMapOf<String, Any>()
        favoriteMap["id"] = favorites.id
        favoriteMap["favorite"] = binding.favBtn.isChecked
        favoriteMap["name"] = favorites.name!!
        favoriteMap["img"] = favorites.img.toString()
        favoriteMap["details"] = favorites.details.toString()
        favoriteMap["upcoming"] = favorites.upcoming!!
        favoriteMap["date_precision"] = favorites.date_precision!!
        favoriteMap["date_local"] = favorites.date_local!!
        favoriteMap["flight_number"] = favorites.flight_number!!
        favoriteMap["original"] = favorites.original
        if (binding.favBtn.isChecked) {
            firestore.collection("Favorites").document(favorites.id).set(favoriteMap)
                .addOnSuccessListener {
                    Toast.makeText(context, "Favorilere Eklendi", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        it.localizedMessage?.toString(),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
        } else {
            firestore.collection("Favorites").document(favorites.id).delete().addOnSuccessListener {
                Toast.makeText(context, "Favoriden çıkarıldı", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun roomFavoriteUpdate(favorites: Favorites) {
        updateFavorites = Favorites(
            favorites.id,
            favorites.name,
            favorites.img,
            binding.favBtn.isChecked,
            favorites.details,
            favorites.upcoming,
            favorites.date_precision,
            favorites.date_local,
            favorites.flight_number,
            favorites.original
        )
        viewModel?.updateFavorite(updateFavorites!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView(favorites: ArrayList<String>) {
        binding.recyclerViewDetails.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerAdapter = ImageContainerAdapter(favorites)
        binding.recyclerViewDetails.adapter = recyclerAdapter
    }


}
