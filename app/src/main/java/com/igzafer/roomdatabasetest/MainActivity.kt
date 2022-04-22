package com.igzafer.roomdatabasetest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.igzafer.roomdatabasetest.adapter.MyRecyclerViewAdapter
import com.igzafer.roomdatabasetest.database.SubscriberDatabase
import com.igzafer.roomdatabasetest.databinding.ActivityMainBinding
import com.igzafer.roomdatabasetest.model.SubscriberModel
import com.igzafer.roomdatabasetest.repository.SubscriberRepository
import com.igzafer.roomdatabasetest.viewmodel.SubscriberViewModel
import com.igzafer.roomdatabasetest.viewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var recyclerViewAdapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer {
            recyclerViewAdapter.setList(it)
            recyclerViewAdapter.notifyDataSetChanged()
        })

    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = MyRecyclerViewAdapter { selectedItem: SubscriberModel ->
            listItemClicked(
                selectedItem
            )
        }
        binding.recyclerView.adapter=recyclerViewAdapter
        displaySubscribersList()

    }

    private fun listItemClicked(subscriber: SubscriberModel) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}