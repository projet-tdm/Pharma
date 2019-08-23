package com.example.pharma.ListAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.bumptech.glide.Glide
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.Notification
import com.example.pharma.Entity.baseUrl
import com.example.pharma.R
import com.google.android.material.chip.Chip






class NotificationAdapter(val ctx:Context,val data:List<Notification>):BaseAdapter() {


    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.notiflayout,parent,false)
            val textView = view?.findViewById(R.id.textView18) as TextView
            val textView3 = view.findViewById(R.id.textView19) as TextView

            holder = ViewHolder(textView, textView3)
            view.setTag(holder)
        }
        else
        {
            holder = view.tag as ViewHolder


        }
        holder.textView.setText("La Commande "+data.get(i).commande.toString() + "est traitée")

        holder.textView3.setText(data.get(i).date)





        return view
    }


    private class ViewHolder(val textView:TextView,val textView3:TextView)
}