package com.example.pharma


import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.customview.customView
import kotlinx.android.synthetic.main.fragment_inscription.*
import kotlin.math.sign


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Inscription :  Fragment() {
    /*override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        signin_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_inscription_to_identification)
        }
        signup_btn.setOnClickListener(){
            showNoticeDialog()
        }
    }
    fun showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = NoticeDialogFragment()
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }*/

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    /*override fun onDialogPositiveClick(dialog: DialogFragment) {
        // User touched the dialog's positive button
    }*/
/*
    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User touched the dialog's negative button
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inscription, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signin_textView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_inscription_to_identification)
        }
        signup_btn.setOnClickListener { view ->
            MaterialDialog(context!!).show {
                message(R.string.signup_btn_msg)
                positiveButton(R.string.ok) {
                    view.findNavController().navigate(R.id.action_inscription_to_identification)
                }
                onCancel {
                    view.findNavController().navigate(R.id.action_inscription_to_identification)
                }


            }
        }
    }
}
