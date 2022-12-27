package com.ezatpanah.flowdatabase.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezatpanah.flowdatabase.utils.Constants.CONTACTS_TABLE

@Entity(tableName = CONTACTS_TABLE)
data class ContactsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
)