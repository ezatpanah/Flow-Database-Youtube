package com.ezatpanah.flowdatabase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.flowdatabase.db.ContactsEntity
import com.ezatpanah.flowdatabase.repository.DatabaseRepository
import com.ezatpanah.flowdatabase.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel  @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {
    val contactsList = MutableLiveData<DataStatus<List<ContactsEntity>>>()

    fun saveNote(noteModel: ContactsEntity) =viewModelScope.launch {
        repository.saveNote(noteModel)
    }

    fun deleteNote(noteModel: ContactsEntity) =viewModelScope.launch {
        repository.deleteNote(noteModel)
    }

    fun getAllNotes()=viewModelScope.launch {
        contactsList.postValue(DataStatus.loading())
        repository.getAllNotes()
            .onEach { delay(2000) }
            .catch { contactsList.postValue(DataStatus.error(it.message.toString())) }
            .collect{ contactsList.postValue(DataStatus.success(it))}
    }
}