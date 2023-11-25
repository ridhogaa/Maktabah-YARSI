package com.maktabah.maktabahyarsi.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history_book")
data class HistoryBookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_buku")
    val id: String,

    @ColumnInfo(name = "judul_buku")
    val title: String,

    @ColumnInfo(name = "deskripsi_buku")
    val desc: String,

    @ColumnInfo(name = "jumlah_halaman")
    val page: Int,

    @ColumnInfo(name = "creator")
    val creator: String,

    @ColumnInfo(name = "cover_buku")
    val imageUrl: String,

    @ColumnInfo(name = "id_user")
    val idUser: String,

    @ColumnInfo(name = "is_history")
    var isHistory: Boolean
)