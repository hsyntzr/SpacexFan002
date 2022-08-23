package com.example.spacexfan002.upcoming

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.adapter.SpaceXListAdapter
import com.example.spacexfan002.databinding.FragmentUpcomingBinding
import com.example.spacexfan002.detail.UpcomingDetailsFragment
import com.example.spacexfan002.favorite.favdata.Favorites
import com.example.spacexfan002.loginFragment.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpcomingFragment : Fragment(), SpaceXListAdapter.Listener {
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
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
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
    }

    private fun initRecyclerView() {
        binding.upcommingRecyclerAdapter.layoutManager = LinearLayoutManager(context)
        recyclerAdapter = SpaceXListAdapter(this)
        binding.upcommingRecyclerAdapter.adapter = recyclerAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObservers() {
        viewModel?.readAllData?.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setSpacexList(it.filter { List -> List.upcoming!! })
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        viewModel?.listenAllList(this.lifecycle, this.lifecycleScope)
    }

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