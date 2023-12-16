package com.surelabsid.mrjempootdriver.ui.orderan.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.surelabsid.mrjempootdriver.R
import com.surelabsid.mrjempootdriver.databinding.*
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.ui.orderan.viewholder.diterima.*
import com.surelabsid.mrjempootdriver.ui.orderan.viewholder.masuk.*
import com.surelabsid.mrjempootdriver.ui.orderan.viewholder.riwayat.*
import com.surelabsid.mrjempootdriver.utils.Constant
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_CUSTOMER_PHOTO
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_ITEM_PHOTO
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_SERVICE_IMAGE
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.utils.formatDate
import com.surelabsid.mrjempootdriver.utils.toRupiah

class OrderanAdapter(
    private val context: Context,
    private val data: List<DataItem?>?,
    private val list_orderan_local: List<EntityOrderan>?,
    private val itemClick: OnClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sessionManager = SessionManager(context)

    //dua digit diawal id
    //digit ketiga 1 = masuk, 2 = diterima, 4 = complete
    val GOCENG_MASUK = 152
    val GOCENG_DITERIMA = 153
    val GOCENG_RIWAYAT = 154

    val GOBAN_MASUK = 162
    val GOBAN_DITERIMA = 163
    val GOBAN_RIWAYAT = 164

    val GOCAP_MASUK = 172
    val GOCAP_DITERIMA = 173
    val GOCAP_RIWAYAT = 174

    val GOPEK_MASUK = 212
    val GOPEK_DITERIMA = 213
    val GOPEK_RIWAYAT = 214

    val GOKIDS_MASUK = 372
    val GOKIDS_DITERIMA = 373
    val GOKIDS_RIWAYAT = 374

    var orderanView = 0

    override fun getItemViewType(position: Int): Int {
        return orderanView
    }

    fun setCurrentViewType(viewType: Int) {
        orderanView = viewType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            GOCENG_MASUK -> {
                val binding =
                    ItemOrderanGocengMasukBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocengMasuk(context, binding)
            }
            GOBAN_MASUK -> {
                val binding =
                    ItemOrderanGobanMasukBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGobanMasuk(context, binding)
            }
            GOCAP_MASUK -> {
                val binding =
                    ItemOrderanGocapMasukBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocapMasuk(context, binding)
            }
            GOPEK_MASUK -> {
                val binding =
                    ItemOrderanGopekMasukBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGopekMasuk(context, binding, parent)
            }
            GOKIDS_MASUK -> {
                val binding =
                    ItemOrderanGokidsMasukBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGokidsMasuk(context, binding)
            }

            GOCENG_DITERIMA -> {
                val binding =
                    ItemOrderanGocengDiterimaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocengDiterima(context, binding)
            }
            GOBAN_DITERIMA -> {
                val binding =
                    ItemOrderanGobanDiterimaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGobanDiterima(context, binding)
            }
            GOCAP_DITERIMA -> {
                val binding =
                    ItemOrderanGocapDiterimaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocapDiterima(context, binding)
            }
            GOPEK_DITERIMA -> {
                val binding =
                    ItemOrderanGopekDiterimaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGopekDiterima(context, binding, parent)
            }
            GOKIDS_DITERIMA -> {
                val binding =
                    ItemOrderanGokidsDiterimaBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGokidsDiterima(context, binding)
            }

            GOCENG_RIWAYAT -> {
                val binding =
                    ItemOrderanGocengRiwayatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocengRiwayat(context, binding)
            }
            GOBAN_RIWAYAT -> {
                val binding =
                    ItemOrderanGobanRiwayatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGobanRiwayat(context, binding)
            }
            GOCAP_RIWAYAT -> {
                val binding =
                    ItemOrderanGocapRiwayatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGocapRiwayat(context, binding)
            }
            GOPEK_RIWAYAT -> {
                val binding =
                    ItemOrderanGopekRiwayatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGopekRiwayat(context, binding, parent)
            }
            GOKIDS_RIWAYAT -> {
                val binding =
                    ItemOrderanGokidsRiwayatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolderGokidsRiwayat(context, binding)
            }

        }

        val binding =
            ItemOrderanGopekMasukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)

    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewType = getItemViewType(position)

        val item = data?.get(position)

        when (viewType) {
            GOCENG_MASUK -> {
                val viewHolderGocengMasuk = holder as ViewHolderGocengMasuk

                with(viewHolderGocengMasuk) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    Glide.with(iconService).load(BASE_URL_SERVICE_IMAGE + item?.icon)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(iconService)


                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    val button_name = context.getString(R.string.menuju_lokasi_penjemputan)
                    buttonTerima.setOnClickListener {
                        itemClick?.itemClickTerima(item, button_name)
                    }

                    buttonTolak.setOnClickListener {
                        itemClick?.itemClickGocengMasukTolak(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOBAN_MASUK -> {
                val viewHolderGobanMasuk = holder as ViewHolderGobanMasuk

                with(viewHolderGobanMasuk) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    Glide.with(iconService).load(BASE_URL_SERVICE_IMAGE + item?.icon)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(iconService)


                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    val button_name = context.getString(R.string.menuju_lokasi_penjemputan)
                    buttonTerima.setOnClickListener {
                        itemClick?.itemClickTerima(item, button_name)
                    }

                    buttonTolak.setOnClickListener {
                        itemClick?.itemClickGocengMasukTolak(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOCAP_MASUK -> {
                val viewHolderGocapMasuk = holder as ViewHolderGocapMasuk

                with(viewHolderGocapMasuk) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    Glide.with(iconService).load(BASE_URL_SERVICE_IMAGE + item?.icon)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(iconService)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble() ?: 0.0)
///
                    senderName.text = item?.senderName
                    receiverName.text = item?.receiverName
                    detailSenderName.text = item?.senderName
                    detailSenderPhone.text = item?.senderPhone
                    detailSenderAddress.text = item?.pickupAddress

                    detailReceiverName.text = item?.receiverName
                    detailReceiverPhone.text = item?.receiverPhone
                    detailReceiverAddress.text = item?.destinationAddress

                    jenisPaket.text = item?.goodsItem
                    beratPaket.text = "-"
                    biayaEkstra.text = "-"
                    ///

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip

                    val button_name = context.getString(R.string.menuju_lokasi_penjemputan)
                    buttonTerima.setOnClickListener {
                        itemClick?.itemClickTerima(item, button_name)
                    }

                    buttonTolak.setOnClickListener {
                        itemClick?.itemClickGocengMasukTolak(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOKIDS_MASUK -> {
                val viewHolderGokidsMasuk = holder as ViewHolderGokidsMasuk

                with(viewHolderGokidsMasuk) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    Glide.with(iconService).load(BASE_URL_SERVICE_IMAGE + item?.icon)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(iconService)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.idLangganan != null) {
                        dateRangeSubscribe.text = "${item.startDate} - ${item?.endDate}"
                        totalHari.text = "${item?.duration} Hari"
                        textViewTarifPerhari.text = "-"
                        textViewTitleTarifPerhari.visibility = View.VISIBLE
                    } else {
                        dateRangeSubscribe.visibility = View.GONE
                        totalHari.visibility = View.GONE
                        textViewTarifPerhari.visibility = View.GONE
                        textViewTitleTarifPerhari.visibility = View.GONE
                    }

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.dropoffTime != null) {
                        textWaktuDropoff.text = item.dropoffTime
                    } else {
                        textWaktuDropoff.text = "-"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    textTrip.text = item?.trip

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    val button_name = context.getString(R.string.menuju_lokasi_penjemputan)
                    buttonTerima.setOnClickListener {
                        itemClick?.itemClickTerima(item, button_name)
                    }

                    buttonTolak.setOnClickListener {
                        itemClick?.itemClickGocengMasukTolak(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }

            }
            GOPEK_MASUK -> {
                val viewHolderGopekMasuk = holder as ViewHolderGopekMasuk

                with(viewHolderGopekMasuk) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    Glide.with(iconService).load(BASE_URL_SERVICE_IMAGE + item?.icon)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(iconService)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())
                    finalCost1.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    totalPesanan.text = item?.itemTransaction?.size.toString()

                    detailPesanan.removeAllViews()
                    item?.itemTransaction?.forEach {

                        val viewDetail = ItemDetailPesananBinding.inflate(
                            LayoutInflater.from(context),
                            parent,
                            false
                        )

                        Glide.with(viewDetail.imageViewItem).load(BASE_URL_ITEM_PHOTO + it?.itemImage)
                            .transform(
                                CenterCrop(),
                                RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                            )
                            .error(R.drawable.bg_beranda).into(viewDetail.imageViewItem)

                        viewDetail.textViewNamaItem.text = it?.itemName
                        viewDetail.textViewVarian.text = "Varian : ${it?.varianTrx}"
                        viewDetail.textViewLevel.text = "Level : ${it?.levelTrx}"
                        viewDetail.textViewCatataan.text = "${it?.itemNote}"

                        viewDetail.textViewCountPesanan.text = "x ${it?.itemAmount}"

                        detailPesanan.addView(viewDetail.root)
                    }

                    pickupTitle.text = context.getString(R.string.restoran)
                    pickupName.text = item?.merchantName
                    pickupAddress.text = item?.merchantAddress

                    destinationName.text = item?.customerFullname
                    destinationAddress.text = item?.destinationAddress

                    textBiaya.text = toRupiah(item?.totalBelanja?.toDouble())
                    textBiayaPlatform.text = toRupiah(item?.cost?.toDouble())
                    textBiayaOngkir.text = toRupiah(item?.price?.toInt()?.minus(item.cost?.toInt() ?: 0).toString().toDouble())

                    discount.text = toRupiah((item?.voucherDiscount ?: "0").toDouble())

                    val button_name = context.getString(R.string.saya_sudah_di_restoran)
                    buttonTerima.setOnClickListener {
                        itemClick?.itemClickTerima(item, button_name)
                    }

                    buttonTolak.setOnClickListener {
                        itemClick?.itemClickGocengMasukTolak(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }

            GOCENG_DITERIMA -> {
                val viewHolderGocengDiterima = holder as ViewHolderGocengDiterima

                with(viewHolderGocengDiterima) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)


                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    var button_name = context.getString(R.string.menuju_lokasi_penjemputan)

                    if (list_orderan_local != null){
                        list_orderan_local.forEach {
                            if (it.id_orderan == item?.id?.toInt()) {
                                button_name = it.current_button ?: context.getString(R.string.menuju_lokasi_penjemputan)

                                if (button_name == context.getString(R.string.menuju_lokasi_penjemputan)) {
                                    buttonCancel.visibility = View.VISIBLE
                                } else {
                                    buttonCancel.visibility = View.GONE
                                }
                            }
                        }
                    }

                    buttonProcess.text = button_name

                    buttonProcess.setOnClickListener {
                        itemClick?.itemClickGocengProcess(item, button_name)
                    }

                    buttonCancel.setOnClickListener {
                        itemClick?.itemClickCancel(item, button_name)
                    }

                    buttonNavigation.setOnClickListener {
                        itemClick?.itemClickNavigation(item, button_name)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOBAN_DITERIMA -> {
                val viewHolderGobanDiterima = holder as ViewHolderGobanDiterima

                with(viewHolderGobanDiterima) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    var button_name = context.getString(R.string.menuju_lokasi_penjemputan)

                    if (list_orderan_local != null){
                        list_orderan_local.forEach {
                            if (it.id_orderan == item?.id?.toInt()) {
                                button_name = it.current_button ?: context.getString(R.string.menuju_lokasi_penjemputan)

                                if (button_name == context.getString(R.string.menuju_lokasi_penjemputan)) {
                                    buttonCancel.visibility = View.VISIBLE
                                } else {
                                    buttonCancel.visibility = View.GONE
                                }
                            }
                        }
                    }

                    buttonProcess.text = button_name

                    buttonProcess.setOnClickListener {
                        itemClick?.itemClickGocengProcess(item, button_name)
                    }

                    buttonCancel.setOnClickListener {
                        itemClick?.itemClickCancel(item, button_name)
                    }

                    buttonNavigation.setOnClickListener {
                        itemClick?.itemClickNavigation(item, button_name)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOCAP_DITERIMA -> {
                val viewHolderGocapDiterima = holder as ViewHolderGocapDiterima

                with(viewHolderGocapDiterima) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble() ?: 0.0)

                    senderName.text = item?.senderName
                    receiverName.text = item?.receiverName
                    detailSenderName.text = item?.senderName
                    detailSenderPhone.text = item?.senderPhone
                    detailSenderAddress.text = item?.pickupAddress

                    detailReceiverName.text = item?.receiverName
                    detailReceiverPhone.text = item?.receiverPhone
                    detailReceiverAddress.text = item?.destinationAddress

                    jenisPaket.text = item?.goodsItem
                    beratPaket.text = "-"
                    biayaEkstra.text = "-"

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    trip.text = item?.trip

                    var button_name = context.getString(R.string.menuju_lokasi_penjemputan)

                    if (list_orderan_local != null){
                        list_orderan_local.forEach {
                            if (it.id_orderan == item?.id?.toInt()) {
                                button_name = it.current_button ?: context.getString(R.string.menuju_lokasi_penjemputan)

                                if (button_name == context.getString(R.string.menuju_lokasi_penjemputan)) {
                                    buttonCancel.visibility = View.VISIBLE
                                } else {
                                    buttonCancel.visibility = View.GONE
                                }
                            }
                        }
                    }

                    buttonProcess.text = button_name

                    buttonProcess.setOnClickListener {
                        itemClick?.itemClickGocapProcess(item, button_name)
                    }

                    buttonCancel.setOnClickListener {
                        itemClick?.itemClickCancel(item, button_name)
                    }

                    buttonNavigation.setOnClickListener {
                        itemClick?.itemClickNavigation(item, button_name)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }
            GOKIDS_DITERIMA -> {
                val viewHolderGokidsDiterima = holder as ViewHolderGokidsDiterima

                with(viewHolderGokidsDiterima) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())
///
                    if (item?.idLangganan != null) {
                        dateRangeSubscribe.text = "${item.startDate} - ${item?.endDate}"
                        totalHari.text = "${item?.duration} Hari"
                        textViewTarifPerhari.text = "-"
                    } else {
                        dateRangeSubscribe.visibility = View.GONE
                        totalHari.visibility = View.GONE
                        textViewTarifPerhari.visibility = View.GONE
                    }
                    ///

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    textTrip.text = item?.trip

                    if (item?.dropoffTime != null) {
                        textWaktuDropoff.text = item?.dropoffTime
                    } else {
                        textWaktuDropoff.text = "-"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupAddress.text = item?.pickupAddress
                    destinationAddress.text = item?.destinationAddress

                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    var button_name = context.getString(R.string.menuju_lokasi_penjemputan)

                    if (list_orderan_local != null){
                        list_orderan_local.forEach {
                            if (it.id_orderan == item?.id?.toInt()) {
                                button_name = it.current_button ?: context.getString(R.string.menuju_lokasi_penjemputan)

                                if (button_name == context.getString(R.string.menuju_lokasi_penjemputan)) {
                                    buttonCancel.visibility = View.VISIBLE
                                } else {
                                    buttonCancel.visibility = View.GONE
                                }
                            }
                        }
                    }

                    buttonProcess.text = button_name

                    buttonProcess.setOnClickListener {
                        itemClick?.itemClickGocengProcess(item, button_name)
                    }

                    buttonCancel.setOnClickListener {
                        itemClick?.itemClickCancel(item, button_name)
                    }

                    buttonNavigation.setOnClickListener {
                        itemClick?.itemClickNavigation(item, button_name)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                    buttonLive.setOnClickListener {
                        itemClick?.itemClickLive(item)
                    }
                }

            }
            GOPEK_DITERIMA -> {
                val viewHolderGopekDiterima = holder as ViewHolderGopekDiterima

                with(viewHolderGopekDiterima) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())
                    finalCost1.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    totalPesanan.text = item?.itemTransaction?.size.toString()

                    detailPesanan.removeAllViews()

                    item?.itemTransaction?.forEach {

                        val viewDetail = ItemDetailPesananBinding.inflate(
                            LayoutInflater.from(context),
                            parent,
                            false
                        )

//                        viewDetail.root.setOnClickListener { it1 ->
//                            Toast.makeText(context, it?.itemName, Toast.LENGTH_SHORT).show()
//                        }

                        Glide.with(viewDetail.imageViewItem).load(BASE_URL_ITEM_PHOTO + it?.itemImage)
                            .transform(
                                CenterCrop(),
                                RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                            )
                            .error(R.drawable.bg_beranda).into(viewDetail.imageViewItem)

                        viewDetail.textViewNamaItem.text = it?.itemName
                        viewDetail.textViewVarian.text = "Varian : ${it?.varianTrx}"
                        viewDetail.textViewLevel.text = "Level : ${it?.levelTrx}"
                        viewDetail.textViewCatataan.text = "${it?.itemNote}"

                        viewDetail.textViewCountPesanan.text = "x ${it?.itemAmount}"

                        detailPesanan.addView(viewDetail.root)

                    }

                    pickupTitle.text = context.getString(R.string.restoran)
                    pickupName.text = item?.merchantName
                    pickupAddress.text = item?.merchantAddress

                    destinationName.text = item?.customerFullname
                    destinationAddress.text = item?.destinationAddress

                    textBiaya.text = toRupiah(item?.totalBelanja?.toDouble())
                    textBiayaPlatform.text = toRupiah(item?.cost?.toDouble())
                    textBiayaOngkir.text = toRupiah(item?.price?.toInt()?.minus(item.cost?.toInt() ?: 0).toString().toDouble())

                    discount.text = toRupiah((item?.voucherDiscount ?: "0").toDouble())

                    var button_name = context.getString(R.string.saya_sudah_di_restoran)

                    if (list_orderan_local != null){
                        list_orderan_local.forEach {
                            if (it.id_orderan == item?.id?.toInt()) {
                                button_name = it.current_button ?: context.getString(R.string.saya_sudah_di_restoran)

//                                if (button_name == context.getString(R.string.menuju_lokasi_penjemputan)) {
//                                    buttonCancel.visibility = View.VISIBLE
//                                } else {
//                                    buttonCancel.visibility = View.GONE
//                                }
                            }
                        }
                    }

                    buttonProcess.text = button_name

                    buttonProcess.setOnClickListener {
                        itemClick?.itemClickGopekProcess(item, button_name)
                    }

//                    buttonCancel.setOnClickListener {
//                        itemClick?.itemClickCancel(item, button_name)
//                    }

                    buttonNavigation.setOnClickListener {
                        itemClick?.itemClickNavigation(item, button_name)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                }
            }

            GOCENG_RIWAYAT -> {
                val viewHolderGocengRiwayat = holder as ViewHolderGocengRiwayat

                with(viewHolderGocengRiwayat) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupTitle.text = context.getString(R.string.jemput)
                    pickupName.text = context.getString(R.string.rumah)
                    pickupAddress.text = item?.pickupAddress
                    pickupIcon.setImageResource(R.drawable.ic_baseline_location)

                    destinationTitle.text = context.getString(R.string.tujuan)
                    destinationName.text = item?.customerFullname
                    destinationAddress.text = item?.destinationAddress
                    destinationIcon.setImageResource(R.drawable.ic_baseline_location)

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    tanggalOrderan.text = "Orderan Selesai tgl ${formatDate(item?.date)}"

                    if (item?.poinCommision != null) {
                        textGetPoin.text = "${item.poinCommision} Point"
                    } else {
                        textViewPoint.text = "0 Point"
                    }


                    buttonNilai.setOnClickListener {
                        itemClick?.itemClickNilai(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }


                }
            }
            GOBAN_RIWAYAT -> {
                val viewHolderGobanRiwayat = holder as ViewHolderGobanRiwayat

                with(viewHolderGobanRiwayat) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupTitle.text = context.getString(R.string.jemput)
                    pickupName.text = item?.customerFullname
                    pickupAddress.text = item?.pickupAddress
                    pickupIcon.setImageResource(R.drawable.ic_baseline_location)

                    destinationTitle.text = context.getString(R.string.tujuan)
                    destinationName.text = "-"
                    destinationAddress.text = item?.destinationAddress
                    destinationIcon.setImageResource(R.drawable.ic_baseline_location)

                    trip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    tanggalOrderan.text = "Orderan Selesai tgl ${formatDate(item?.date)}"

                    if (item?.poinCommision != null) {
                        textGetPoin.text = "${item.poinCommision} Point"
                    } else {
                        textViewPoint.text = "0 Point"
                    }

                    buttonNilai.setOnClickListener {
                        itemClick?.itemClickNilai(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }
                }


            }
            GOCAP_RIWAYAT -> {
                val viewHolderGocapRiwayat = holder as ViewHolderGocapRiwayat

                with(viewHolderGocapRiwayat) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    senderName.text = item?.senderName
                    receiverName.text = item?.receiverName
                    detailSenderName.text = item?.senderName
                    detailSenderPhone.text = item?.senderPhone
                    detailSenderAddress.text = item?.pickupAddress

                    detailReceiverName.text = item?.receiverName
                    detailReceiverPhone.text = item?.receiverPhone
                    detailReceiverAddress.text = item?.destinationAddress

                    jenisPaket.text = item?.goodsItem
                    beratPaket.text = "-"
                    biayaEkstra.text = "-"

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item?.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupTitle.text = context.getString(R.string.jemput)
                    pickupName.text = context.getString(R.string.rumah)
                    pickupAddress.text = item?.pickupAddress
                    pickupIcon.setImageResource(R.drawable.ic_baseline_location)

                    destinationTitle.text = context.getString(R.string.tujuan)
                    destinationName.text = item?.customerFullname
                    destinationAddress.text = item?.destinationAddress
                    destinationIcon.setImageResource(R.drawable.ic_baseline_location)

                    trip.text = item?.trip
//                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

//                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

//                    tarif.text = item?.finalCost

                    tanggalOrderan.text = "Orderan Selesai tgl ${formatDate(item?.date)}"

                    if (item?.poinCommision != null) {
                        textGetPoin.text = "${item.poinCommision} Point"
                    } else {
                        textViewPoint.text = "0 Point"
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }
                }


            }
            GOKIDS_RIWAYAT -> {
                val viewHolderGokidsRiwayat = holder as ViewHolderGokidsRiwayat

                with(viewHolderGokidsRiwayat) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)


                    nameCustomer.text = item?.customerFullname
                    kodePesanan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())

                    if (item?.idLangganan != null) {
                        dateRangeSubscribe.text = "${item.startDate} - ${item?.endDate}"
                        totalHari.text = "${item.duration} Hari"
                        textViewTarifPerhari.text = "-"
                        textViewTarifPerhari.visibility = View.VISIBLE
                    } else {
                        dateRangeSubscribe.visibility = View.GONE
                        totalHari.visibility = View.GONE
                        textViewTarifPerhari.visibility = View.GONE
                        textViewTitleTarifPerhari.visibility = View.GONE
                    }

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.dropoffTime != null) {
                        textWaktuDropoff.text = item?.dropoffTime
                    } else {
                        textWaktuDropoff.text = "-"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    pickupTitle.text = context.getString(R.string.jemput)
                    pickupName.text = context.getString(R.string.rumah)
                    pickupAddress.text = item?.pickupAddress
                    pickupIcon.setImageResource(R.drawable.ic_baseline_location)

                    destinationTitle.text = context.getString(R.string.tujuan)
                    destinationName.text = item?.customerFullname
                    destinationAddress.text = item?.destinationAddress
                    destinationIcon.setImageResource(R.drawable.ic_baseline_location)

                    textTrip.text = item?.trip
                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

                    tarif.text = toRupiah(item?.finalCost?.toDouble())

                    tanggalOrderan.text = "Orderan Selesai tgl ${formatDate(item?.date)}"

                    if (item?.poinCommision != null) {
                        textGetPoin.text = "${item.poinCommision} Point"
                    } else {
                        textViewPoint.text = "0 Point"
                    }

                    buttonNilai.setOnClickListener {
                        itemClick?.itemClickNilai(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }

                    buttonLive.setOnClickListener {
                        itemClick?.itemClickLive(item)
                    }
                }

            }
            GOPEK_RIWAYAT -> {
                val viewHolderGopekRiwayat = holder as ViewHolderGopekRiwayat

                with(viewHolderGopekRiwayat) {

                    Glide.with(photoCustomer).load(BASE_URL_CUSTOMER_PHOTO + item?.customerImage)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                        )
                        .error(R.drawable.bg_beranda).into(photoCustomer)

                    nameCustomer.text = item?.customerFullname
                    kodePesan.text = "Kode Pesanan: ${item?.kodeTrx}"
                    finalCost.text = toRupiah(item?.finalCost?.toDouble())
                    finalCost1.text = toRupiah(item?.finalCost?.toDouble())

                    pickupTitle.text = context.getString(R.string.restoran)
                    pickupName.text = "${item?.merchantName} - ${item?.merchantAddress}"
//                    pickupAddress.text = item?.pickupAddress
                    pickupIcon.setImageResource(R.drawable.ic_baseline_location)

                    destinationTitle.text = context.getString(R.string.dikirim_ke)
                    destinationName.text = "${item?.customerFullname} - ${item?.destinationAddress}"
//                    destinationAddress.text = item?.destinationAddress
                    destinationIcon.setImageResource(R.drawable.ic_baseline_location)

//                    trip.text = item?.trip
//                    distance.text = "Rute ${item?.distance}${item?.costDesc}"

//                    titleTarif.text = context.getString(R.string.tarif_jempoot_motorbike)

//                    tarif.text = item?.finalCost

                    textBiaya.text = toRupiah(item?.totalBelanja?.toDouble())
                    textBiayaPlatform.text = toRupiah(item?.cost?.toDouble())
                    textBiayaOngkir.text = toRupiah(item?.price?.toInt()?.minus(item.cost?.toInt() ?: 0).toString().toDouble())

                    discount.text = toRupiah((item?.voucherDiscount ?: "0").toDouble())

                    tanggalOrderan.text = "Orderan Selesai tgl ${formatDate(item?.date)}"

                    if (item?.poinCommision != null) {
                        textGetPoin.text = "${item.poinCommision} Point"
                    } else {
                        textViewPoint.text = "0 Point"
                    }

                    if (item?.poinCommision != null) {
                        textViewPoint.text = "Dapatkan ${item.poinCommision} Poin"
                    } else {
                        textViewPoint.text = "Tidak ada poin"
                    }

                    if (item?.walletPayment == "1") {
                        typePayment.text = "EzzPay"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    } else {
                        typePayment.text = "Cash"
                        imageTypePayment.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    }

                    totalPesanan.text = item?.itemTransaction?.size.toString()

                    detailPesanan.removeAllViews()
                    item?.itemTransaction?.forEach {

                        val viewDetail = ItemDetailPesananBinding.inflate(
                            LayoutInflater.from(context),
                            parent,
                            false
                        )

                        Glide.with(viewDetail.imageViewItem).load(BASE_URL_ITEM_PHOTO + it?.itemImage)
                            .transform(
                                CenterCrop(),
                                RoundedCorners(Constant.Companion.value.ROUND_IMAGE)
                            )
                            .error(R.drawable.bg_beranda).into(viewDetail.imageViewItem)

                        viewDetail.textViewNamaItem.text = it?.itemName
                        viewDetail.textViewVarian.text = "Varian : ${it?.varianTrx}"
                        viewDetail.textViewLevel.text = "Level : ${it?.levelTrx}"
                        viewDetail.textViewCatataan.text = "${it?.itemNote}"

                        viewDetail.textViewCountPesanan.text = "x ${it?.itemAmount}"

                        detailPesanan.addView(viewDetail.root)
                    }

                    buttonNilai.setOnClickListener {
                        itemClick?.itemClickNilai(item)
                    }

                    buttonChat.setOnClickListener {
                        itemClick?.itemClickChat(item)
                    }

                    buttonCall.setOnClickListener {
                        itemClick?.itemClickCall(item)
                    }
                }

            }
        }
    }

    class ViewHolder(
        val context: Context,
        val binding: ItemOrderanGopekMasukBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        //Goceng Riwayat
        val photoCustomer = binding.imageView
        val nameCustomer = binding.textViewNama
        val kodePesan = binding.textViewKodePesanan
        val pickupTitle = binding.appCompatTextView9
        val pickupName = binding.appCompatTextView10

        val pickupAddress = binding.appCompatTextView11

        val pickupIcon = binding.appCompatImageView6

        val destinationTitle = binding.appCompatTextView12
        val destinationName = binding.appCompatTextView13
        val destinationAddress = binding.appCompatTextView14
        val destinationIcon = binding.appCompatImageView7

    }

    interface OnClickListener {
        fun itemClick(item: DataItem?)
        fun itemClickNavigation(item: DataItem?, button_name: String)
        fun itemClickCancel(item: DataItem?, button_name: String)
        fun itemClickTerima(item: DataItem?, button_name: String)
        fun itemClickGocengMasukTolak(item: DataItem?)
        fun itemClickGocengProcess(item: DataItem?, button_name: String)
        fun itemClickGopekProcess(item: DataItem?, button_name: String)
        fun itemClickGocapProcess(item: DataItem?, button_name: String)
        fun itemClickNilai(item: DataItem?)
        fun itemClickChat(item: DataItem?)
        fun itemClickCall(item: DataItem?)
        fun itemClickLive(item: DataItem?)

    }
}
