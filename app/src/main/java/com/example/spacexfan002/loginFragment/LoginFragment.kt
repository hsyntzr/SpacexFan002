package com.example.spacexfan002.loginFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.databinding.FragmentLoginBinding
import com.example.spacexfan002.rockets.RocketsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            (activity as MainActivity).replaceFragment(RocketsFragment())
        }
        (activity as MainActivity).bottomNavigationView.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            signInBtn()
        }
    }


    private fun signInBtn() {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()

        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(context, "Enter email and password! ", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val mainActivity = (activity as MainActivity)
                mainActivity.bottomNavigationView.visibility = View.VISIBLE
                mainActivity.replaceFragment(RocketsFragment())

            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }
    }

}