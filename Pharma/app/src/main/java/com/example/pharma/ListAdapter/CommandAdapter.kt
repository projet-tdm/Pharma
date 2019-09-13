package com.example.pharma.ListAdapter
 import android.app.Activity
 import android.content.Context
 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import android.widget.*
 import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
 import com.bumptech.glide.Glide
 import com.example.pharma.CommandeFragment

 import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.Commande
import com.example.pharma.Entity.baseUrl
import com.google.android.material.chip.Chip
import android.R

class CommandeAdapter(val ctx:Context,val data:List<Commande>,frag:CommandeFragment):BaseAdapter() {

    val commandFragment = frag

    override fun getItem(p0: Int)= data.get(p0)

    override fun getItemId(p0: Int) = data.get(p0).hashCode().toLong()

    override fun getCount() = data.size

    override fun getView(i: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(com.example.pharma.R.layout.cmdlayout,parent,false)
            val textView = view?.findViewById(com.example.pharma.R.id.textView7) as TextView
            val textView3 = view.findViewById(com.example.pharma.R.id.pharma) as TextView
            val textView4 = view.findViewById(com.example.pharma.R.id.textView24) as TextView
            val chip = view.findViewById(com.example.pharma.R.id.chip2) as Chip
            val img = view.findViewById(com.example.pharma.R.id.imageView3) as ImageView
            holder = ViewHolder(textView, textView3, textView4, chip,img)
            view.setTag(holder)
        }
        else
        {
            holder = view.tag as ViewHolder
        }
        var anul=view.findViewById(com.example.pharma.R.id.button2) as Button
        var payer=view.findViewById(com.example.pharma.R.id.payer) as Button
        var mnt=view.findViewById(com.example.pharma.R.id.mnt) as TextView
        var curr=view.findViewById(com.example.pharma.R.id.curr) as TextView

        anul.visibility=View.GONE
        payer.visibility=View.GONE
        mnt.visibility=View.GONE
        curr.visibility=View.GONE
        holder.textView.setText("Commande "+data.get(i).numero.toString())
        /* holder.imageView.setImageResource(data.get(i).image)*/
        when (data.get(i).etat) {
            "A"-> holder.chip.setChipBackgroundColorResource(com.example.pharma.R.color.annul)
            "T"-> {holder.chip.setChipBackgroundColorResource(com.example.pharma.R.color.traite)
                   //ICI On fait le payement
                  //BOUTON PAYER
                   payer.visibility=View.VISIBLE
                  //LE MONTANT
                   mnt.visibility=View.VISIBLE
                   curr.visibility=View.VISIBLE
                    mnt.text = "1500"
                  payer.setOnClickListener {
                      commandFragment.amount = mnt.text.toString()
                      commandFragment.getToken(data.get(i),view)
                  }
            }
            "C"-> {holder.chip.setChipBackgroundColorResource(com.example.pharma.R.color.cours)

                anul.visibility=View.VISIBLE
                anul.setOnClickListener {
                    val cmdModel = ViewModelProviders.of(this.ctx as FragmentActivity).get(CmdModel::class.java)
                    cmdModel.annuler(ctx!!,data.get(i).numero)
                    MaterialDialog(ctx!!).show {
                        message(com.example.pharma.R.string.an_btn_msg)
                        positiveButton(com.example.pharma.R.string.ok) {
                            view.findNavController().navigate(com.example.pharma.R.id.action_commandeFragment_self)
                        }
                        onCancel {
                            view.findNavController().navigate(com.example.pharma.R.id.action_commandeFragment_self)
                        }

                    }

                }}
            "P"-> {holder.chip.setChipBackgroundColorResource(com.example.pharma.R.color.colorAccent)}
            }
        Glide.with(ctx).load(
            baseUrl+"upload/"+ data.get(i).photo).into( holder.image)
        holder.textView3.setText(data.get(i).pharma)
        holder.textView4.text = data.get(i).date




        return view
    }


    private class ViewHolder(val textView:TextView,val textView3:TextView,val textView4:TextView,val chip:Chip,val image:ImageView)
}