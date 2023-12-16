package com.surelabsid.mrjempootdriver.room.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_orderan")
data class EntityOrderan (
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "id_orderan")
    var id_orderan: Int? = null,

    @ColumnInfo(name = "status_orderan")
    var status_orderan: String? = null,

    @ColumnInfo(name = "current_button")
    var current_button: String? = null,

)