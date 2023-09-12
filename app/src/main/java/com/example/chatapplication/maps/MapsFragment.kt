@file:Suppress("DEPRECATION")

package com.example.chatapplication.maps

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.chatapplication.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.fragment_maps.*


@Suppress("DEPRECATION", "UNREACHABLE_CODE")
 class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleApiClient.OnConnectionFailedListener {

    private  val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private var hospitalMarkers: MutableList<Marker> = mutableListOf()
    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        super.onCreate(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        mapView.getMapAsync(this)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)

        Places.initialize(requireContext(), "YOUR_API_KEY")
        placesClient = Places.createClient(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnMapType.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)

            popupMenu.apply {
                menuInflater.inflate(R.menu.map_type_menu, popupMenu.menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {

                        R.id.Normal -> {
                            map.mapType = GoogleMap.MAP_TYPE_NORMAL
                            Toast.makeText(context, "Default", Toast.LENGTH_SHORT)

                        }
                        R.id.Satellite ->{
                            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                            Toast.makeText(context, "Satellite", Toast.LENGTH_SHORT)

                        }
                        R.id.Terrain ->{
                            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                           Toast.makeText(context, "Terrain", Toast.LENGTH_SHORT)
                        }
                    }
                    true
                }

                show()
            }
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        current_location.setOnClickListener {

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLong = LatLng(location.latitude, location.longitude)
                    placeMarkerOnMap(currentLatLong)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 14f))

                } else{
                    Toast.makeText(context,"provide location",Toast.LENGTH_SHORT).show()
                }
            }
        }

        btGetNearByPlace.setOnClickListener {
            getHospitalNearby()
        }


        button_search_location.setOnClickListener{
            if (search_location.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please insert a location", Toast.LENGTH_SHORT).show()
            } else {
                searchLocation()
            }
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun getHospitalNearby() {

    }


    private fun searchLocation() {

        val location = search_location.text.toString().trim()

        var addressList: List<Address>? = null

            val geoCoder = Geocoder(requireContext())
            addressList = geoCoder.getFromLocationName(location, 1)
                if (addressList?.isNotEmpty()!!) {
                 val address = addressList[0]
                  val latLng = LatLng(address.latitude, address.longitude)
                  map.addMarker(MarkerOptions().position(latLng).title(location))
                  map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
                }else{
                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                }

    }


    override fun onMapReady(googleMap: GoogleMap) {
       map = googleMap
        map.setOnMarkerClickListener(this)
        googleMap.uiSettings.isZoomControlsEnabled = true
        setUpMap()

    }


    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)
            != PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
            map.isMyLocationEnabled = true
    }


    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        map.addMarker(markerOptions)

    }

    override fun onMarkerClick(p0: Marker) = false

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}
