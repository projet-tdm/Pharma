package com.example.pharma.ListAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.pharma.Entity.Notification
 import com.example.pharma.R







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
            var lin:ConstraintLayout= view.findViewById(R.id.linearLayout6) as ConstraintLayout

            holder = ViewHolder(textView, textView3,lin)
            view.setTag(holder)
        }
        else
        {
            holder = view.tag as ViewHolder


        }
        holder.textView.setText("La Commande "+data.get(i).commande.toString() + " est traitée")

        holder.textView3.setText(data.get(i).date)
        if (data.get(i).vue==1) holder.lin.setBackgroundColor(Color.parseColor("#F0F8FF"))





        return view
    }


    private class ViewHolder(val textView:TextView,val textView3:TextView,var lin:ConstraintLayout)
}