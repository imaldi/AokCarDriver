package com.surelabsid.mrjempootdriver.ui.jempootpoin

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.ActivityRadeemPoinBinding
import com.surelabsid.mrjempootdriver.databinding.DialogRedeemBerhasilBinding
import com.surelabsid.mrjempootdriver.databinding.DialogWithdrawBerhasilBinding
import com.surelabsid.mrjempootdriver.ui.BaseActivity
import com.surelabsid.mrjempootdriver.ui.jempootpoin.adapter.SouvenirAdapter
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelrequest.RequestRedeemPoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponsePoin
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.ResponseSouvenir
import com.surelabsid.mrjempootdriver.ui.jempootpoin.modelresponse.SouvenirItem
import com.surelabsid.mrjempootdriver.ui.jempootpoin.viewmodel.JempootPoinViewModel
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.item_view_type.ITEM_SOUVENIR
import com.surelabsid.mrjempootdriver.utils.showSnackbar

class RadeemPoinActivity : BaseActivity() {

    lateinit var binding: ActivityRadeemPoinBinding

    lateinit var viewModel: JempootPoinViewModel

    lateinit var dialogRedeemBerhasilBinding: DialogRedeemBerhasilBinding

    private lateinit var dialog: Dialog

    private var adapter: SouvenirAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadeemPoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(JempootPoinViewModel::class.java)

        initView()

        attachObserve()
    }

    private fun attachObserve() {
        with(viewModel) {
            souvenir.observe(this@RadeemPoinActivity, { responseSouvenir(it) })
            redeem_poin.observe(this@RadeemPoinActivity, { responseRedeemPoin(it) })
        }
    }

    private fun responseSouvenir(it: ResponseSouvenir?) {
        if (it?.code == "200") {

            adapter = SouvenirAdapter(this, it.data?.souvenir, object : SouvenirAdapter.OnClickListener {
                override fun itemClick(item: SouvenirItem?) {

                    val my_poin = sessionManager.poin.toInt()
                    val redeem_poin = item?.poinBuy?.toInt()
                    if (redeem_poin ?: 0 > my_poin){
                        binding.root.showSnackbar(getString(R.string.poin_not_enough), Snackbar.LENGTH_SHORT)
                    } else {
                        val param = RequestRedeemPoin(sessionManager.id, item?.idSouvenir.toString())
                        viewModel.redeemPoin(param)
                    }

                }

            })

            adapter?.setCurrentViewType(ITEM_SOUVENIR)
            binding.listSouvenir.adapter = adapter

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun responseRedeemPoin(it: ResponsePoin?) {
        if (it?.code == "200") {

            showDialogRedeemBerhasil()

        } else {
            binding.root.showSnackbar("${it?.code} - ${it?.message}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun showDialogRedeemBerhasil() {
        dialogRedeemBerhasilBinding = DialogRedeemBerhasilBinding.bind(View.inflate(this, R.layout.dialog_redeem_berhasil, null))

        dialog = Dialog(this).apply {
            setContentView(dialogRedeemBerhasilBinding.root)
        }

        dialog.show()

        dialogRedeemBerhasilBinding.buttonOke.setOnClickListener {
            finish()
        }
    }


    private fun initView() {

        viewModel.listSouvenir()

        with(binding) {
            imageButtonBack.setOnClickListener {
                onBackPressed()
            }
            textPoin.text = "${sessionManager.poin} POIN"

            textSearch.doAfterTextChanged {
                adapter?.filter?.filter(it.toString())
            }

        }
    }
}