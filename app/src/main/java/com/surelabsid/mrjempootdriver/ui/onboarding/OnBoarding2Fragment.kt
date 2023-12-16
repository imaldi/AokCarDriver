package com.surelabsid.mrjempootdriver.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.FragmentOnBoarding2Binding

class OnBoarding2Fragment : Fragment() {

    lateinit var binding: FragmentOnBoarding2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonKembali.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onBoardingContainer, OnBoarding1Fragment()).commit()
        }


        binding.buttonLanjut.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onBoardingContainer, OnBoarding3Fragment()).commit()
        }

    }

}