package com.example.pharma


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.edit

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.NotificationModel
import com.example.pharma.ListAdapter.NotificationAdapter
import kotlinx.android.synthetic.main.fragment_commande.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_pharmacies.*


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
         imageView7.visibility= View.INVISIBLE
        notifModel.loadData(activity!!,nss)
        if(notifModel.list?.size==0 || notifModel.list==null) imageView7.visibility= View.VISIBLE
        listnotif.adapter=NotificationAdapter(activity!!,notifModel.list!!)
        listnotif.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->

            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
            pref.edit {
                putInt("decision"
                    ,1)
                putInt("notif"
                    ,position)
            }
            view?.findNavController()?.navigate(R.id.action_notifications_to_detailNotif)


        }
    }
}
