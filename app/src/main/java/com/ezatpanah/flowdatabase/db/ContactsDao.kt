package com.ezatpanah.flowdatabase.db

import androidx.room.*
import com.ezatpanah.flowdatabase.utils.Constants.CONTACTS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface  ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(contactsEntity: ContactsEntity)

    @Delete
    suspend fun deleteNote(contactsEntity: ContactsEntity)

    @Query("SELECT * FROM $CONTACTS_TABLE")
    fun getAllNotes(): Flow<List<ContactsEntity>>
}