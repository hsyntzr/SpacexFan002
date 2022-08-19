package com.example.spacexfan002.loginFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.spacexfan002.MainActivity
import com.example.spacexfan002.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(LoginFragment())
        }
        binding.registerBtn.setOnClickListener{
            if (binding.registerEmail.text.toString()
                    .isNotEmpty() && binding.registerPassword.text.toString().isNotEmpty()
            ) {
                auth.createUserWithEmailAndPassword(
                    binding.registerEmail.text.toString(),
                    binding.registerPassword.text.toString()
                ).addOnSuccessListener {
                    (activity as MainActivity).replaceFragment(LoginFragment())
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }



        }
    }

}