package com.surelabsid.mrjempootdriver.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.FragmentOnBoarding4Binding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginDaftarActivity
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.utils.moveActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoarding4Fragment : Fragment() {

    lateinit var binding: FragmentOnBoarding4Binding

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoarding4Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonKembali.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onBoardingContainer, OnBoarding3Fragment()).commit()
        }


        binding.buttonLanjut.setOnClickListener {
            sessionManager.on_boarding = true
            activity?.moveActivity(LoginDaftarActivity::class.java)
        }

    }
}