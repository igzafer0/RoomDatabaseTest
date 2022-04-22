package com.igzafer.roomdatabasetest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.igzafer.roomdatabasetest.model.SubscriberModel

@Dao
interface ISubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: SubscriberModel):Long

    @Update
    suspend fun updateSubscriber(subscriber: SubscriberModel)

    @Delete
    suspend fun deleteSubscriber(subscriber: SubscriberModel):Int

    @Query("delete from subscriber_data_table")
    suspend fun deleteAll()

    @Query("select * from subscriber_data_table")
    fun getSubscribers():LiveData<List<SubscriberModel>>

}