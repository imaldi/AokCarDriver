package com.surelabsid.mrjempootdriver.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.service.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.service.modelresponse.ResponseNotification
import com.surelabsid.mrjempootdriver.ui.beranda.BerandaFragment
import com.surelabsid.mrjempootdriver.ui.peforma.PeformanceFragment
import com.surelabsid.mrjempootdriver.ui.profil.ProfileFragment
import com.surelabsid.mrjempootdriver.ui.ewallet.EWalletFragment
import com.surelabsid.mrjempootdriver.ui.orderan.OrderanFragment
import com.surelabsid.mrjempootdriver.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var bottomNavigation: BottomNavigationView? = null
    var fragment: Fragment? = null
    var fragmentManager: FragmentManager? = null
    var buttonNavigation: BottomNavigationView? = null
    private var notif: ResponseNotification? = null
    private val mMenuId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notif = intent.getSerializableExtra("data") as ResponseNotification?

        buttonNavigation = findViewById<View>(R.id.bottomNavigation) as BottomNavigationView
        buttonNavigation!!.menu.findItem(R.id.menu_beranda).isChecked = true
        fragmentManager = supportFragmentManager

        if (notif != null){

            if (notif?.type == "transaksi") {
                val bundle = Bundle()
                bundle.putSerializable("service_id", notif?.serviceId)
//                bundle.putSerializable("data_notif", notif?.data?.get(0)?.serviceId)
                val fragment = OrderanFragment()
                fragment.arguments = bundle
                fragmentManager!!.beginTransaction().replace(R.id.mainContainer, fragment).commit()
            }

        } else {
            fragmentManager!!.beginTransaction().replace(R.id.mainContainer, BerandaFragment()).commit()
        }

        buttonNavigation!!.setOnItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.menu_beranda -> fragment = BerandaFragment()
                R.id.menu_peforma -> fragment = PeformanceFragment()
                R.id.menu_orderan -> fragment = OrderanFragment()
                R.id.menu_wallet -> fragment = EWalletFragment()
                R.id.menu_profile -> fragment = ProfileFragment()
            }
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainContainer, fragment!!).commit()
            true
        }
    }
}