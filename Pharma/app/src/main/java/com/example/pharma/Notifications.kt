package com.example.pharma


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
  import com.example.pharma.Entity.NotificationModel
import com.example.pharma.ListAdapter.NotificationAdapter
 import kotlinx.android.synthetic.main.fragment_notifications.*


class Notifications : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val nss = pref.getInt("nss", 0)

        val notifModel = ViewModelProviders.of(activity!!).get(NotificationModel::class.java)
        notifModel.loadData(activity!!,nss)
        listnotif.adapter=NotificationAdapter(activity!!,notifModel.list!!)
    }
}
