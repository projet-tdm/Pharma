package com.example.pharma


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
 import android.os.Bundle
 import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
  import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
import java.io.IOException
import androidx.lifecycle.ViewModelProviders
import com.example.pharma.Entity.CmdModel
import com.example.pharma.Entity.MyModel
 import kotlinx.android.synthetic.main.fragment_ville.*
import org.jetbrains.anko.support.v4.toast


class FormulaireCommande : Fragment() {

    private val GALLERY = 1
    private val CAMERA = 2
    private var bitmap :Bitmap? = null
    var name:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_formulaire_commande, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn.setOnClickListener {


            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(activity!!,
                    Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                        Manifest.permission.CAMERA)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.CAMERA),
                        101)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                 showPictureDialog()
            }

        }
        val model = ViewModelProviders.of(activity!!).get(MyModel::class.java)

        // If the list of cities is null, load the list from DB

        model.loadDataall(activity!!)

        android_material_design_spinner1.setDialogTitle("Trouver une pharmacie")
        android_material_design_spinner1.setDismissText("Quitter")
        val cmdModel = ViewModelProviders.of(activity!!).get(CmdModel::class.java)
        val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
        val nss = pref.getInt("nss", 0)
        commander.setOnClickListener{ view ->
            cmdModel.addCommande(bitmap!!,activity!!,nss,name!!)

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

                    name=contentURI.path.substringAfterLast("/")
                    toast("file "+name)
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
            if (data != null)
            {
                 try {
                    bitmap = data.extras!!.get("data") as Bitmap
                     name = (0..100000).random().toString()+".jpg"
                    toast("file " + name)
                    input.setImageBitmap(bitmap)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity!!, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
         }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


}


