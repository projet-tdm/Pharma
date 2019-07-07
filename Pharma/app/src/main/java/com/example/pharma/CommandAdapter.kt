package com.example.pharma
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class CommandeAdapter(val ctx:Context,val data:List<Commande>):BaseAdapter() {
    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder:ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.cmdlayout,parent,false)
            val textView = view?.findViewById(R.id.textView7) as TextView

            val textView3 = view?.findViewById(R.id.pharma) as TextView
            val textView4 = view?.findViewById(R.id.textView24) as TextView


            holder = ViewHolder(textView,textView3,textView4)
            view?.setTag(holder)
        }
        else {
            holder = view.tag as ViewHolder

        }
        holder.textView.setText("Commande "+data.get(i).numero.toString())
        /* holder.imageView.setImageResource(data.get(i).image)*/

        holder.textView3.setText(data.get(i).pharma.adresse)
        /* holder.imageView.setImageResource(data.get(i).image)*/
        holder.textView4.text = data.get(i).date
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
    private class ViewHolder(val textView:TextView,val textView3:TextView,val textView4:TextView)
}