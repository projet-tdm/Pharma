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
import com.example.pharma.Entity.baseUrl
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
            val img = view.findViewById(R.id.imageView3) as ImageView
            holder = ViewHolder(textView, textView3, textView4, chip,img)
            view.setTag(holder)
        }
        else
        {
            holder = view.tag as ViewHolder


        }
        var anul=view.findViewById(R.id.button2) as Button
        var payer=view.findViewById(R.id.payer) as Button
        var mnt=view.findViewById(R.id.mnt) as TextView

        anul.visibility=View.GONE
        payer.visibility=View.GONE
        mnt.visibility=View.GONE
        holder.textView.setText("Commande "+data.get(i).numero.toString())
        /* holder.imageView.setImageResource(data.get(i).image)*/
        when (data.get(i).etat) {
            "A"-> holder.chip.setChipBackgroundColorResource(R.color.annul)
            "T"-> {holder.chip.setChipBackgroundColorResource(R.color.traite)
                   //ICI On fait le payement
                  //BOUTON PAYER
                   payer.visibility=View.VISIBLE
                  //LE MONTANT
                   mnt.visibility=View.VISIBLE
                  payer.setOnClickListener {

                  }



            }
            "C"-> {holder.chip.setChipBackgroundColorResource(R.color.cours)

                anul.visibility=View.VISIBLE
                anul.setOnClickListener {
                    val cmdModel = ViewModelProviders.of(this.ctx as FragmentActivity).get(CmdModel::class.java)
                    cmdModel.annuler(ctx!!,data.get(i).numero)
                    MaterialDialog(ctx!!).show {
                        message(R.string.an_btn_msg)
                        positiveButton(R.string.ok) {
                            view.findNavController().navigate(R.id.action_commandeFragment_self)
                        }
                        onCancel {
                            view.findNavController().navigate(R.id.action_commandeFragment_self)
                        }

                    }

                }}
            "P"-> {holder.chip.setChipBackgroundColorResource(R.color.colorAccent)}



            }
        Glide.with(ctx).load(
            baseUrl+"upload/"+ data.get(i).photo).into( holder.image)
        holder.textView3.setText(data.get(i).pharma)
        holder.textView4.text = data.get(i).date




        return view
    }


    private class ViewHolder(val textView:TextView,val textView3:TextView,val textView4:TextView,val chip:Chip,val image:ImageView)
}