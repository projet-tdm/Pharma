package com.example.pharma


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.example.pharma.Entity.Pharmacie
import com.example.pharma.Retrofit.RetrofitService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.custom_info_window.*
import kotlinx.android.synthetic.main.fragment_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.fragment_info.*
import com.example.pharma.Entity.MyModel
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast

class mapFragment : Fragment(),OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    val rayon = 20000
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var collection : HashMap<Marker,Int> = HashMap()

    override fun onInfoWindowClick(marker : Marker) {
        var bundle = Bundle()
        bundle.putInt("id",collection.get(marker)!!)
        bundle.putInt("map",1)
        this.findNavController().navigate(R.id.action_mapFragment_to_detailPharma,bundle)
    }

    fun  degreesToRadians(degrees:Double):Double{
    return degrees * Math.PI / 180
    }


    fun distanceBetweenEarthCoordinates(lat1:Double, lon1:Double, lat2:Double, lon2:Double):Double {
        val earthRadiusKm = 6371
        var dLat = degreesToRadians(lat2 - lat1)
        var dLon = degreesToRadians(lon2 - lon1)
        var lat11 = degreesToRadians(lat1)
        var lat22 = degreesToRadians(lat2)
        var a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat11) * Math.cos(
                lat22
            )
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadiusKm * c *1000
    }
    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        private val window: View = layoutInflater.inflate(R.layout.custom_info_window, null)

        override fun getInfoWindow(marker: Marker): View? {
            render(marker, window)
            return window
        }

        override fun getInfoContents(marker: Marker): View? {
            render(marker, window)
            return window
        }

        private fun render(marker: Marker, view: View) {
            view.findViewById<ImageView>(R.id.badge).setImageResource(R.drawable.pharma_icon)
            view.findViewById<TextView>(R.id.title).text = marker.title
            view.findViewById<TextView>(R.id.snippet).text = marker.snippet
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())
        mMap.setOnInfoWindowClickListener(this@mapFragment)
        mMap.isMyLocationEnabled = true

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Context)

        val vm= ViewModelProviders.of(activity!!).get(MyModel::class.java)

        if (ActivityCompat.checkSelfPermission(context as Context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                        var pos : LatLng
                        var snippet : String
                        var marker: Marker
                        var ph : Pharmacie
                        val call = RetrofitService.endpoint.getPharmaciesGarde()
                        call.enqueue(object : Callback<ArrayList<Pharmacie>> {
                            override fun onResponse(call: Call<ArrayList<Pharmacie>>?, response: Response<ArrayList<Pharmacie>>?) {
                                if (response?.isSuccessful!!) {
                                    vm.listGarde = response.body()
                                    for (i in 0..vm.listGarde!!.size-1){
                                        ph = vm.listGarde!!.get(i)
                                        if (distanceBetweenEarthCoordinates(location.latitude,location.longitude,ph.lat,ph.lng)<rayon){
                                            pos = LatLng(ph.lat,ph.lng)
                                            snippet = ph.adresse + "\n" + ph.tel
                                            marker = mMap.addMarker(
                                                MarkerOptions()
                                                    .position(pos)
                                                    .title(ph.nom)
                                                    .snippet(snippet)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow))
                                            )
                                            collection.put(marker,i)
                                        }
                                    }
                                } else {
                                    act.toast("Une erreur s'est produite")
                                }
                            }
                            override fun onFailure(call: Call<ArrayList<Pharmacie>>?, t: Throwable?) {
                                act.toast("Une erreur s'est produite")
                            }
                        })
                    }
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


}
