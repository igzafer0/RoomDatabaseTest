package com.igzafer.roomdatabasetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.igzafer.roomdatabasetest.R
import com.igzafer.roomdatabasetest.databinding.SubscriberRowBinding
import com.igzafer.roomdatabasetest.generated.callback.OnClickListener
import com.igzafer.roomdatabasetest.model.SubscriberModel

class MyRecyclerViewAdapter(

    private val clickListener: (SubscriberModel) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var subscribers = ArrayList<SubscriberModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SubscriberRowBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.subscriber_row, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }
    fun setList(subscriber: List<SubscriberModel>){
        subscribers.clear()
        subscribers.addAll(subscriber)
    }
}

class MyViewHolder(val binding: SubscriberRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: SubscriberModel, clickListener: (SubscriberModel) -> Unit) {
        binding.nameTw.text = subscriber.name
        binding.emailTw.text = subscriber.email
        binding.recyclerLn.setOnClickListener {
            clickListener(subscriber)
        }
    }
}