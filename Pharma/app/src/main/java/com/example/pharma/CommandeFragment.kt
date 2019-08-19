package com.example.pharma


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.NotificationModel
import kotlinx.android.synthetic.main.fragment_commande.*


class CommandeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commande, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val nss = pref.getInt("nss", 0)
        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)
        cmdModel.loadData(activity!!,nss)
        val notifModel = ViewModelProviders.of(activity!!).get(NotificationModel::class.java)
        notifModel.loadData(activity!!,nss)



        add.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_formulaireCommande)
        }
        button5.setOnClickListener {
            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
            pref.edit {
                putBoolean("connected",false)
            }
            view?.findNavController()?.navigate(R.id.action_commandeFragment_to_identification)
        }
        val button = view?.findViewById<Button>(R.id.notif)
        button?.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(activity!!,notif)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            for (item in notifModel.list!!)
            {
                popupMenu.getMenu().add("La commande "+item.commande.toString()+"est traité date : "+item.date)

            }

            /*popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {

                 }
                true
            })*/
            popupMenu.show()
        }
    }

}
