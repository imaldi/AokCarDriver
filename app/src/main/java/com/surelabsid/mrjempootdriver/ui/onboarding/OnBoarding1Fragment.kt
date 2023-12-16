package com.surelabsid.mrjempootdriver.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.FragmentOnBoarding1Binding


class OnBoarding1Fragment : Fragment() {

    lateinit var binding: FragmentOnBoarding1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLanjut.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onBoardingContainer, OnBoarding2Fragment()).commit()
        }
    }


}