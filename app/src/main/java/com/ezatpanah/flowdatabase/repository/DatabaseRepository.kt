package com.ezatpanah.flowdatabase.repository

import com.ezatpanah.flowdatabase.db.ContactsDao
import com.ezatpanah.flowdatabase.db.ContactsEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: ContactsDao) {

    suspend fun saveNote(entity : ContactsEntity)=dao.saveNote(entity)
    suspend fun deleteNote(entity : ContactsEntity)=dao.deleteNote(entity)
    fun getAllNotes()=dao.getAllNotes()


}