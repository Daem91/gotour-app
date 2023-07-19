package com.dapm.gotour.home


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.dapm.gotour.R
import com.dapm.gotour.databinding.ActivitySearchBinding
import com.dapm.gotour.databinding.ActivityVerDestinoMapaBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.math.roundToInt

class VerDestinoMapa : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityVerDestinoMapaBinding

    private var start: String = ""
    private var end: String = ""
    var poly: Polyline? = null
    var poly_caminante: Polyline? = null
    var distancia: Double? = null
    var tiempo: Double? = null


    var distancia_caminante: Double? = null
    var tiempo_caminante: Double? = null


    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerDestinoMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        poly?.remove()
        poly = null
        poly_caminante?.remove()
        poly_caminante = null


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

        end = "${longitud},${latitud}"


        enableLocation()
    }



    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun enableLocation() {
        if (!::mMap.isInitialized) return
        if (isLocationPermissionGranted()) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val currentLat = it.latitude
                    val currentLng = it.longitude
                    start = "${currentLng},${currentLat}"
                    createRoute()
                }
            }


        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.isMyLocationEnabled = true
                enableLocation()
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localizacion ve a ajustes y acepta los permisos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::mMap.isInitialized) return
        if (!isLocationPermissionGranted()) {
            mMap.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localizacion ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    fun createRoute() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getRoute(
                "5b3ce3597851110001cf624887ab3f427d7344ae99970f6276003169",
                start,
                end
            )
            val callwalk = getRetrofit().create(ApiService::class.java).getRouteWalking(
                "5b3ce3597851110001cf624887ab3f427d7344ae99970f6276003169",
                start,
                end
            )

            if (call.isSuccessful) {
                val colorCarro = Color.RED
                drawRoute(call.body(), colorCarro)


            }
            if (callwalk.isSuccessful) {
                val colorPersona = Color.BLUE
                drawRoute_walk(callwalk.body(), colorPersona)
            } else {
                Log.i("e", "KO")
            }
        }
    }

    private fun drawRoute(routeResponse: RouteResponse?, color: Int) {
        val polylineOptions = PolylineOptions()
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            polylineOptions.add(LatLng(it[1], it[0]))
        }
        distancia = routeResponse?.features?.first()?.properties?.summary?.distance
        distancia = distancia?.div(1000) ?: 0.0

        tiempo = routeResponse?.features?.first()?.properties?.summary?.duration


        polylineOptions.color(color)
        runOnUiThread {
            poly_caminante = mMap.addPolyline(polylineOptions)
            binding.apply {
                val distanciaString = "%.2f km".format(distancia ?: 0.0)
                eTkmCarro.text = distanciaString


                eTduracionCarro.text = formatSecondsToMinutes(tiempo)


            }
        }

    }

    private fun drawRoute_walk(routeResponse: RouteResponse?, color: Int) {
        val polylineOptions = PolylineOptions()
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            polylineOptions.add(LatLng(it[1], it[0]))
        }
        distancia_caminante = routeResponse?.features?.first()?.properties?.summary?.distance
        distancia_caminante = distancia_caminante?.div(1000) ?: 0.0

        tiempo_caminante = routeResponse?.features?.first()?.properties?.summary?.duration


        polylineOptions.color(color)

        runOnUiThread {
            poly = mMap.addPolyline(polylineOptions)
            binding.apply {
                val distanciaString = "%.2f km".format(distancia_caminante ?: 0.0)
                eTkmPersona.text = distanciaString


                eTduracionPersona.text = formatSecondsToMinutes(tiempo_caminante)


            }
        }

    }

    fun formatSecondsToMinutes(seconds: Double?): String {
        val roundedSeconds = ((seconds?.div(30))?.roundToInt() ?: 0) * 30
        val minutes = roundedSeconds / 60
        return String.format("%02d:%02d min", minutes, roundedSeconds % 60)
    }


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}