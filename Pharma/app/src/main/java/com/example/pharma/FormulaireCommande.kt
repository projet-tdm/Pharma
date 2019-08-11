package com.example.pharma


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
 import android.os.Bundle
 import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
  import android.widget.Toast
 import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
import java.io.IOException
import androidx.lifecycle.ViewModelProviders
import com.example.pharma.Entity.CmdModel


class FormulaireCommande : Fragment() {

    private val GALLERY = 1
    private val CAMERA = 2
    private var bitmap :Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_formulaire_commande, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn.setOnClickListener { showPictureDialog() }
        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)

        commander.setOnClickListener{ view ->
            cmdModel.addCommande(bitmap!!,activity!!)

            MaterialDialog(context!!).show {
                message(R.string.cmd_btn_msg)
                positiveButton(R.string.ok) {
                    view.findNavController().navigate(R.id.action_formulaireCommande_to_commandeFragment)
                }
                onCancel {
                    view.findNavController().navigate(R.id.action_formulaireCommande_to_commandeFragment)
                }

            }
        }






    }
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity!!)
        pictureDialog.setTitle("Choisir une action")
        val pictureDialogItems = arrayOf("A partir de Galerie", "Prendre une photo")
        pictureDialog.setItems(pictureDialogItems
        ) {_, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

     override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, contentURI)


                    input.setImageBitmap(bitmap)



                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity!!, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
             bitmap = data!!.extras!!.get("data") as Bitmap

            input.setImageBitmap(bitmap)
         }
    }


}


