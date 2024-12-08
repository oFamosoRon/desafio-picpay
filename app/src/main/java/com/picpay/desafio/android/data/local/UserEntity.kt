package com.picpay.desafio.android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val img: String?,
    val name: String?,
    val username: String?
)
