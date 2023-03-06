package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentCharacterViewBinding
import com.squareup.picasso.Picasso

class characterView : Fragment() {

    private lateinit var binding: FragmentCharacterViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterViewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var image = ""
        var name = ""
        var status = ""
        var species = ""
        var locationName = ""

        arguments?.let {

            name = requireArguments().getString("name").toString()
            status = requireArguments().getString("status").toString()
            species = requireArguments().getString("species").toString()
            locationName = requireArguments().getString("locationName").toString()
            image = requireArguments().getString("image").toString()

        }
        binding.textView.text = name
        binding.textView4.text = locationName
        binding.textView3.text = status
        binding.textView2.text = species
        Picasso.get().load(image).into(binding.imageView)
        super.onViewCreated(view, savedInstanceState)


    }

}