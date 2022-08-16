package com.example.spacexfan002.favorite


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.adapter.SpaceXListAdapter
import com.example.spacexfan002.databinding.FragmentFavoriteBinding
import com.example.spacexfan002.detail.RocketDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.loginFragment.LoginFragment
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

    private fun initRecyclerView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = SpaceXListAdapter(this)
        binding.favoriteRecyclerView.adapter = recyclerViewAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initRecyclerView()
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]



        favoriteViewModel.readAllData.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerViewAdapter.setSpacexList(it.filter { spaceXModel -> spaceXModel.favorite!! })
                recyclerViewAdapter.notifyDataSetChanged()
                /*if(it.none { favorite -> favorite.favorite!! }){
                    Toast.makeText(context, "LIST IS EMPTY", Toast.LENGTH_SHORT).show()
                }*/
                /*binding.favoriteRecyclerView.also { recycler->
                         recycler.layoutManager = LinearLayoutManager(requireContext())
                       recycler.adapter = SpaceXListAdapter(it.filter {it ->
                           it.favorite == true
                        }, this)*/
            }
        }

        /*  favoriteViewModel.readAllData.observe(viewLifecycleOwner, Observer { favoriteList ->

              recyclerViewAdapter.setSpacexList(favoriteList.filter { it ->
                  true
              })
              if (favoriteList.size == 0) {
              }
          })*/

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
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
        val updateFavorites = Favorites(
            spaceXModel.id,
            spaceXModel.name,
            spaceXModel.img,
            false,
            spaceXModel.details,
            spaceXModel.upcoming,
            spaceXModel.date_precision,
            spaceXModel.date_local,
            spaceXModel.flight_number,
            spaceXModel.original
        )
        favoriteViewModel.updateFavorite(updateFavorites)
    }


}