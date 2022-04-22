package com.igzafer.roomdatabasetest.repository

import com.igzafer.roomdatabasetest.database.ISubscriberDAO
import com.igzafer.roomdatabasetest.model.SubscriberModel

class SubscriberRepository(private val dao: ISubscriberDAO) {

    val subscribers = dao.getSubscribers()

    suspend fun insert(subscriber: SubscriberModel):Long {
       return dao.insertSubscriber(subscriber)
    }
    suspend fun update(subscriber: SubscriberModel) {
        dao.updateSubscriber(subscriber)
    }
    suspend fun delete(subscriber: SubscriberModel) {
        dao.deleteSubscriber(subscriber)
    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }
}