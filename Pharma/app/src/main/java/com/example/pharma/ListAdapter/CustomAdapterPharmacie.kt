package com.example.pharma.ListAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.Pharmacies
import com.example.pharma.R
import java.util.*

class CustomAdapterPharmacie(val ctx:Context,val data:ArrayList<Pharmacie>):BaseAdapter() {
    var arrayList = data


    //Store image and arraylist in Temp Array List we Required it later
    var tempArrayList = ArrayList(arrayList)

    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.phramalayout,parent,false)
            val textView = view.findViewById(R.id.textView11) as TextView
            val textView2 = view?.findViewById(R.id.textView12) as TextView
            val textView3 = view.findViewById(R.id.textView21) as TextView

           holder = ViewHolder(textView, textView2, textView3)
            view.setTag(holder)
        }
        else {
            holder = view.tag as ViewHolder

        }
        holder.textView3.setText(data.get(i).nom)
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
    //Function to set data according to Search Keyword in ListView
    fun filter(text: String?) {


        //Our Search text
        val text = text!!.toLowerCase(Locale.getDefault())


        //Here We Clear Both ArrayList because We update according to Search query.


        arrayList.clear()

        if (text.length == 0) {

            /*If Search query is Empty than we add all temp data into our main ArrayList

            We store Value in temp in Starting of Program.

            */
            arrayList.addAll(tempArrayList)


        } else {


            for (i in 0..tempArrayList.size - 1) {

                /*
                If our Search query is not empty than we Check Our search keyword in Temp ArrayList.
                if our Search Keyword in Temp ArrayList than we add to our Main ArrayList
                */

                if (tempArrayList.get(i).nom.toLowerCase(Locale.getDefault()).startsWith(text,true)) arrayList.add(tempArrayList.get(i))

            }
        }

        //This is to notify that data change in Adapter and Reflect the changes.
        notifyDataSetChanged()


    }
    private class ViewHolder(val textView:TextView,val textView2:TextView,val textView3:TextView)
}