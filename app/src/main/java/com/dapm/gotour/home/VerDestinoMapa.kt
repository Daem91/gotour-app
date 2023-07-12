package com.dapm.gotour.home


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dapm.gotour.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class VerDestinoMapa : AppCompatActivity () , OnMapReadyCallback{


    private lateinit var mMap: GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION=0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_destino_mapa)



        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitudString = intent.getStringExtra("latitud")
        val longitudString = intent.getStringExtra("longitud")
        val nombre = intent.getStringExtra("nombre")

        val latitud = latitudString?.toDoubleOrNull() ?: 0.0
        val longitud = longitudString?.toDoubleOrNull() ?: 0.0


        val destinoLatLng = LatLng(latitud, longitud)
        mMap.addMarker(MarkerOptions().position(destinoLatLng).title(nombre))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLatLng, 15f))




        enableLocation()
    }

    private fun isLocationPermissionGranted() =ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    )==PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::mMap.isInitialized) return
        if(isLocationPermissionGranted()){
            mMap.isMyLocationEnabled=true
        } else{
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION-> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled=true
            } else{
                Toast.makeText(this,"Para activar la localizacion ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else ->{}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::mMap.isInitialized) return
        if(!isLocationPermissionGranted()){
            mMap.isMyLocationEnabled=false
            Toast.makeText(this,"Para activar la localizacion ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()

        }
    }
}