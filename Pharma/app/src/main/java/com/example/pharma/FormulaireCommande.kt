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
import com.example.pharma.Entity.MyResponse
 import com.example.pharma.Retrofit.RetrofitServiceUpload
 import kotlinx.android.synthetic.main.fragment_formulaire_commande.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.toast
 import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
 import java.io.File
import java.io.IOException
import android.graphics.BitmapFactory
import android.util.Base64
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream


class FormulaireCommande : Fragment() {

    private val GALLERY = 1
    private val CAMERA = 2
    private var bitmap = null
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

        commander.setOnClickListener{ view ->
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
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
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
                    val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, contentURI)
                    addCommande(bitmap)

                    Toast.makeText(activity!!, "Image Saved!", Toast.LENGTH_LONG).show()

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
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            addCommande(thumbnail)

            input.setImageBitmap(thumbnail)
            Toast.makeText(activity!!, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

private fun ImageToString(bitmap:Bitmap):String
{
    var str =ByteArrayOutputStream()
    var bitmapC=getResizedBitmap(bitmap,700)
    bitmapC.compress(Bitmap.CompressFormat.JPEG,100,str)
    return Base64.encodeToString(str.toByteArray(),Base64.DEFAULT)


}
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun addCommande(myBitmap: Bitmap) {

        var filestr =ImageToString(myBitmap)
        val call = RetrofitServiceUpload.endpoint.addCmd(filestr,"C",npha.text.toString(),"12/02/2019")
        call.enqueue(object : Callback<MyResponse>{
            override fun onResponse(call: Call<MyResponse> ?, response: Response<MyResponse> ?) {
                if (response?.isSuccessful!!) {
                    toast("l'ordonnance est sauvgardé")
                } else {
                    toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<MyResponse> ?, t: Throwable?) {
                toast("Une erreur s'est produite")
            }


        })
    }
}


