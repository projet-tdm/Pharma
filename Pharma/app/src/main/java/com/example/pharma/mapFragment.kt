package com.example.pharma


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
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
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class mapFragment : Fragment(),OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
/*override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map.onCreate(savedInstanceState)
    }
    override fun onResume() {
        super.onResume()
        map.onResume()
    }*/
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

    override fun onMapReady(googleMap: GoogleMap) {
        val rayon = 20000
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Context)
        mMap.isMyLocationEnabled = true
        if (ActivityCompat.checkSelfPermission(context as Context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                        val call = RetrofitService.endpoint.getPharmaciesGarde()
                        call.enqueue(object : Callback<List<Pharmacie>> {
                            override fun onResponse(
                                call: Call<List<Pharmacie>>?, response:
                                Response<List<Pharmacie>>?
                            ) {
                                if (response?.isSuccessful!!) {
                                    var pos : LatLng
                                    for (ph in response.body()!!){
                                        if (distanceBetweenEarthCoordinates(location.latitude,location.longitude,ph.lat,ph.lng)<rayon){
                                            pos = LatLng(ph.lat,ph.lng)
                                            mMap.addMarker(
                                                MarkerOptions()
                                                    .position(pos)
                                                    .title(ph.nom)
                                                    .snippet(ph.adresse)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow))
                                            )
                                        }
                                    }
                                } else {
                                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()
                                }

                            }
                            override fun onFailure(call: Call<List<Pharmacie>>?, t: Throwable?) {
                                Toast.makeText(
                                    activity,
                                    "Echec de la connexion au serveur ! Vérifiez votre connexion internet",
                                    Toast.LENGTH_LONG
                                ).show()
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
