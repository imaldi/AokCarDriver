package com.surelabsid.mrjempootdriver.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surelabsid.mrjempootdriver.room.dao.DaoOrderan
import com.surelabsid.mrjempootdriver.room.model.EntityOrderan
import com.surelabsid.mrjempootdriver.ui.orderan.modelresponse.DataItem
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.database.VERSION

@Database(entities = [EntityOrderan::class], version = VERSION, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun daoOrderan(): DaoOrderan
}