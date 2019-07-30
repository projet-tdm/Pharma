package com.example.pharma.ListAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pharma.Entity.Commande
import com.example.pharma.R
import com.google.android.material.chip.Chip






class CommandeAdapter(val ctx:Context,val data:List<Commande>):BaseAdapter() {


    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.cmdlayout,parent,false)
            val textView = view?.findViewById(R.id.textView7) as TextView

            val textView3 = view.findViewById(R.id.pharma) as TextView
            val textView4 = view.findViewById(R.id.textView24) as TextView
            val chip = view.findViewById(R.id.chip2) as Chip

            holder = ViewHolder(textView, textView3, textView4, chip)
            view.setTag(holder)
        }
        else {
            holder = view.tag as ViewHolder

        }
        holder.textView.setText("Commande "+data.get(i).numero.toString())
        /* holder.imageView.setImageResource(data.get(i).image)*/
        when (data.get(i).etat) {
            "A"-> holder.chip.setChipBackgroundColorResource(R.color.annul)
            "T"-> holder.chip.setChipBackgroundColorResource(R.color.traite)
           "C"-> holder.chip.setChipBackgroundColorResource(R.color.cours)


        }
        holder.textView3.setText(data.get(i).pharma.nom)
        /* holder.imageView.setImageResource(data.get(i).image)*/
        holder.textView4.text = data.get(i).date



        return view
    }


    private class ViewHolder(val textView:TextView,val textView3:TextView,val textView4:TextView,val chip:Chip)
}