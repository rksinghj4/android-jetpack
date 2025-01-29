package com.kotlinflow.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDb(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("email")
    val email: String?,
    @ColumnInfo("avatar")
    val avatar: String?
)