package com.example.spacexfan002.favorite


import android.app.Activity
import android.os.Bundle
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
import com.example.spacexfan002.databinding.FragmentFavoriteBinding
import com.example.spacexfan002.detail.RocketDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.favorite.loginFragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class FavoriteFragment : Fragment(), SpaceXListAdapter.Listener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerViewAdapter: SpaceXListAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        //İnit  RecyclerView
       /* binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = SpaceXListAdapter(arrayListOf(),this)
        binding.favoriteRecyclerView.adapter = recyclerViewAdapter*/

        favoriteViewModel.readAllData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.favoriteRecyclerView.also { recycler->
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                    recycler.adapter = SpaceXListAdapter(it.filter {it ->
                        it.favorite == true
                    }, this)
                }
                //recyclerViewAdapter.setSpacexList(it.filter { spacexmodel -> spacexmodel.favorite!! })
            } else {
                Toast.makeText(context, "Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        }

      /*  favoriteViewModel.readAllData.observe(viewLifecycleOwner, Observer { favoriteList ->

            recyclerViewAdapter.setSpacexList(favoriteList.filter { it ->
                true
            })
            if (favoriteList.size == 0) {
                Toast.makeText(context, "Liste Boş! ", Toast.LENGTH_LONG).show()
            }
        })*/

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener() {
            auth.signOut()
            (activity as MainActivity).replaceFragment(LoginFragment())
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
        val updateFavorites = Favorites(spaceXModel.id,spaceXModel.name,spaceXModel.img,false,spaceXModel.details,spaceXModel.upcoming,spaceXModel.date_precision,spaceXModel.date_local,spaceXModel.flight_number)
        favoriteViewModel.updateFavorite(updateFavorites)
        Toast.makeText(requireContext(), "Kulllanıcı Silindi", Toast.LENGTH_LONG).show()
    }


}