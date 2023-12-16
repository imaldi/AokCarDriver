package com.surelabsid.mrjempootdriver.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.databinding.FragmentOnBoardingJenisTransportBinding
import com.surelabsid.mrjempootdriver.ui.pendaftaran.LoginDaftarActivity
import com.surelabsid.mrjempootdriver.utils.moveActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingJenisTransportFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    lateinit var binding: FragmentOnBoardingJenisTransportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingJenisTransportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLanjut.setOnClickListener {

        }

    }
}