package com.ezatpanah.flowdatabase.repository

import com.ezatpanah.flowdatabase.db.ContactsDao
import com.ezatpanah.flowdatabase.db.ContactsEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: ContactsDao) {

    suspend fun saveContact(entity : ContactsEntity)=dao.saveContact(entity)
    suspend fun updateTask(entity: ContactsEntity)= dao.updateContact(entity)
    suspend fun deleteContact(entity : ContactsEntity)=dao.deleteContact(entity)
    fun getDetailsContact(id:Int)= dao.getContact(id)
    fun getAllContacts()=dao.getAllContacts()
    fun deleteAllContacts()=dao.deleteAllContacts()
    fun getSortedListASC()=dao.sortedASC()
    fun getSortedListDESC()=dao.sortedDESC()
    fun searchContact(name: String) = dao.searchContact(name)



}