package com.surelabsid.mrjempootdriver.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem

@Dao
interface DaoOrderan {

    @Insert
    fun insertOrderan(entityCart: EntityOrderan)

    @Query("SELECT * FROM tb_orderan")
    fun getOrderan(): List<EntityOrderan>

    @Query("SELECT * FROM tb_orderan WHERE id_orderan = :id_orderan")
    fun getOrderanById(id_orderan: Int): List<EntityOrderan>

    @Query("UPDATE tb_orderan SET current_button = :button_name WHERE id_orderan = :id_orderan")
    fun updateButtonName(id_orderan: Int, button_name: String)

    @Query("DELETE FROM tb_orderan WHERE id_orderan = :id_orderan")
    fun deleteOrderan(id_orderan: Int)

    @Query("DELETE FROM tb_orderan")
    fun deleteOrderanAll()


}