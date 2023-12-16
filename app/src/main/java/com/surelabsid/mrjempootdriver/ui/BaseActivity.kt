package com.surelabsid.mrjempootdriver.ui

import androidx.appcompat.app.AppCompatActivity
import com.surelabsid.mrjempootdriver.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

}