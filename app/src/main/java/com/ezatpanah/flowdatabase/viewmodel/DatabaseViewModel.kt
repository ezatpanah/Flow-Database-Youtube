package com.ezatpanah.flowdatabase.viewmodel

import androidx.lifecycle.LiveData
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
class DatabaseViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private val _contactsList = MutableLiveData<DataStatus<List<ContactsEntity>>>()
    val contactsList : LiveData<DataStatus<List<ContactsEntity>>>
        get() = _contactsList

    val contactsDetail = MutableLiveData<DataStatus<ContactsEntity>>()

    init {
        getAllContacts()
    }

    fun saveContact(isEdite: Boolean, entity: ContactsEntity) = viewModelScope.launch {
        if (isEdite) {
            repository.updateTask(entity)
        } else {
            repository.saveContact(entity)
        }
    }

    fun deleteContact(entity: ContactsEntity) = viewModelScope.launch {
        repository.deleteContact(entity)
    }

    fun deleteAllContacts() = viewModelScope.launch {
        repository.deleteAllContacts()
    }

    fun getAllContacts() = viewModelScope.launch {
        _contactsList.postValue(DataStatus.loading())
        repository.getAllContacts()
            .catch { _contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect { _contactsList.postValue(DataStatus.success(it, it.isEmpty())) }
    }

    fun getSortedListASC() = viewModelScope.launch {
        _contactsList.postValue(DataStatus.loading())
        repository.getSortedListASC()
            .catch { _contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect { _contactsList.postValue(DataStatus.success(it, it.isEmpty())) }
    }

    fun getSortedListDESC() = viewModelScope.launch {
        _contactsList.postValue(DataStatus.loading())
        repository.getSortedListDESC()
            .catch { _contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect { _contactsList.postValue(DataStatus.success(it, it.isEmpty())) }
    }

    fun getSearchContacts(name: String) = viewModelScope.launch {
        repository.searchContact(name).collect() {
            _contactsList.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }

    fun getDetailsContact(id: Int) = viewModelScope.launch {
        repository.getDetailsContact(id).collect {
            contactsDetail.postValue(DataStatus.success(it, false))
        }
    }

}