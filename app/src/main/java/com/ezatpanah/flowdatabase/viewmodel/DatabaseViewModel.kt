package com.ezatpanah.flowdatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.flowdatabase.db.ContactsEntity
import com.ezatpanah.flowdatabase.repository.DatabaseRepository
import com.ezatpanah.flowdatabase.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel  @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {
    val contactsList = MutableLiveData<DataStatus<List<ContactsEntity>>>()

    init{
        getAllContacts()
    }

    fun saveContact(entity: ContactsEntity) =viewModelScope.launch {
        repository.saveContact(entity)
    }

    fun deleteContact(entity: ContactsEntity) =viewModelScope.launch {
        repository.deleteContact(entity)
    }

    fun deleteAllContacts() = viewModelScope.launch {
        repository.deleteAllContacts()
    }

    fun getAllContacts()=viewModelScope.launch {
        contactsList.postValue(DataStatus.loading())
        repository.getAllContacts()
            .catch { contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect{ contactsList.postValue(DataStatus.success(it,it.isEmpty()))}
    }

    fun getSortedListASC()=viewModelScope.launch {
        contactsList.postValue(DataStatus.loading())
        repository.getSortedListASC()
            .catch { contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect{ contactsList.postValue(DataStatus.success(it,it.isEmpty()))}
    }

    fun getSortedListDESC()=viewModelScope.launch {
        contactsList.postValue(DataStatus.loading())
        repository.getSortedListDESC()
            .catch { contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect{ contactsList.postValue(DataStatus.success(it,it.isEmpty()))}
    }

    fun getSearchContacts(name: String) = viewModelScope.launch {
        repository.searchContact(name).collect() {
            contactsList.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }
}