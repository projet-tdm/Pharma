package com.example.pharma
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CustomAdapterPharmacie(val ctx:Context,val data:List<Pharmacie>):BaseAdapter() {
    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder:ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.phramalayout,parent,false)
            val textView = view.findViewById(R.id.textView11) as TextView
           val textView2 = view?.findViewById(R.id.textView12) as TextView


           holder = ViewHolder(textView,textView2)
            view?.setTag(holder)
        }
        else {
            holder = view.tag as ViewHolder

        }
        holder.textView.setText(data.get(i).adresse)
       /* holder.imageView.setImageResource(data.get(i).image)*/
        holder.textView2.text = data.get(i).tel
        return view
    }

    /*
       override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
           var view = p0
           if (view == null) {
               view = LayoutInflater.from(ctx).inflate(R.layout.mylayout, parent, false)

           }
           val textView = view?.findViewById(R.id.textView) as TextView
           val imageView = view?.findViewById(R.id.imageView) as ImageView
           Toast.makeText(ctx,"message $i",Toast.LENGTH_SHORT).show()
           textView.setText(data.get(i).firstName)
           imageView.setImageResource(data.get(i).image)
           return view
       }
       */
    private class ViewHolder(val textView:TextView,val textView2:TextView)
}