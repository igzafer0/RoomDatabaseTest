package com.igzafer.roomdatabasetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igzafer.roomdatabasetest.model.SubscriberModel
import com.igzafer.roomdatabasetest.repository.SubscriberRepository
import com.igzafer.roomdatabasetest.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: SubscriberModel

    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Kaydet"
        clearAllOrDeleteButtonText.value = "Tümünü Sil"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(SubscriberModel(0, name, email))
        }
        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
        inputName.value = null
        inputEmail.value = null

    }

    fun insert(subscriber: SubscriberModel): Job =
        viewModelScope.launch {
            val result=repository.insert(subscriber)
            if(result>-1){
                statusMessage.value = Event("Takipçi $result başarıyla eklendi")
            }else{
                statusMessage.value = Event("Takipçi ekleme başarısız")

            }

        }

    fun update(subscriber: SubscriberModel): Job = viewModelScope.launch {
        repository.update(subscriber)
        isUpdateOrDeleteOff()
        statusMessage.value = Event("Takipçi başarıyla güncellendi")

    }

    fun delete(subscriber: SubscriberModel): Job = viewModelScope.launch {
        repository.delete(subscriber)
        isUpdateOrDeleteOff()
        statusMessage.value = Event("Takipçi başarıyla silindi")

    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("Tüm takipçiler başarıyla silindi")
    }

    fun initUpdateAndDelete(subscriber: SubscriberModel) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDeleteOn(subscriber)
    }

    private fun isUpdateOrDeleteOff() {
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Kaydet"
        clearAllOrDeleteButtonText.value = "Tümünü Sil"

    }

    private fun isUpdateOrDeleteOn(subscriber: SubscriberModel) {
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        saveOrUpdateButtonText.value = "Güncelle"
        clearAllOrDeleteButtonText.value = "Sil"

    }
}