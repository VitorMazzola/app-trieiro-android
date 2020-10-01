package br.com.hackccr.features.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.hackccr.App
import br.com.hackccr.BuildConfig
import br.com.hackccr.R
import br.com.hackccr.data.CategoryEnum
import br.com.hackccr.data.CityCovid
import br.com.hackccr.data.IncidenceEnum
import br.com.hackccr.data.Point
import br.com.hackccr.features.maps.di.DaggerMapComponent
import br.com.hackccr.features.maps.di.MapModule
import br.com.hackccr.features.tab.TabActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_maps.*
import javax.inject.Inject

class MapsFragment : Fragment(), MapView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @Inject
    lateinit var presenter: MapPresenter

    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mLastLocation: Location
    private lateinit var mLocationRequest: LocationRequest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDagger()
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

    }

    private fun initDagger() {
        DaggerMapComponent.builder()
            .applicationComponent(App.getAppComponent(activity!!))
            .mapModule(MapModule(this))
            .build()
            .inject(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
        buildGoogleApiClient()
        mMap.isMyLocationEnabled = true
        setupClickListeners()
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mGoogleApiClient.connect()
    }

    override fun onLocationChanged(location : Location) {
        mMap.clear()
        mLastLocation = location

        val latLng = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
        mMap.addMarker(MarkerOptions()
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)))
        presenter.onCreate(latLng)
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 60000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun configMapCovid(cities: List<CityCovid>?) {
        cities?.forEach {
            val latLng = LatLng(it.lat, it.lng)
            val marker = mMap.addMarker(MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.corona))))
                marker.title = "COVID"
                marker.tag = it

            when(it.getIncidence()) {
                IncidenceEnum.LOW -> addCircle(latLng, ContextCompat.getColor(requireContext(), R.color.map_blue))
                IncidenceEnum.MEDIUM -> addCircle(latLng, ContextCompat.getColor(requireContext(), R.color.map_yellow))
                IncidenceEnum.HIGH -> addCircle(latLng, ContextCompat.getColor(requireContext(), R.color.map_red))
            }
        }
    }

    private fun addCircle(latLng: LatLng, color: Int) {
        mMap.addCircle(CircleOptions()
            .center(latLng)
            .radius(2000.0)
            .strokeColor(color)
            .fillColor(color))
    }

    override fun configMapCategory(points: List<Point>?) {
        points?.forEach {
            val latLng = LatLng(it.lat.toDouble(), it.lng.toDouble())
            when(it.getCategory()) {
                CategoryEnum.GAS_STATION -> addCustomMarker(latLng, R.drawable.gas, it)
                CategoryEnum.RESTAURANT -> addCustomMarker(latLng, R.drawable.restaurante, it)
                CategoryEnum.MECHANICAL_WORKSHOP -> addCustomMarker(latLng, R.drawable.mecanico, it)
                CategoryEnum.BUTTERFLY -> addCustomMarker(latLng, R.drawable.mecanico, it)
                CategoryEnum.ACCOMMODATION -> addCustomMarker(latLng, R.drawable.dormir, it)
                CategoryEnum.STOP_POINT -> addCustomMarker(latLng, R.drawable.restaurante, it)
                else ->  addCustomMarker(latLng, R.drawable.restaurante, it)
            }

        }
    }

    private fun addCustomMarker(latLng: LatLng, icon: Int, point: Point) {
        val marker = mMap.addMarker(MarkerOptions()
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon))))

        marker.title = "POINT"
        marker.tag = point

    }

    private fun setupClickListeners() {
        mMap.setOnMarkerClickListener {
            val type = it.title

            if(type == "COVID") {
                val covid = it.tag as CityCovid
                openCovidDetail(covid)
            }

            if(type == "POINT") {
                val point = it.tag as Point
                openPointDetail(point)
            }

            true
        }
    }

    private fun openPointDetail(point: Point?) {
        point?.let {
            val pointBottomSheet = PointBottomSheet.newInstance(it)
            fragmentManager?.let { fragManager ->
                pointBottomSheet.show(fragManager, null)
            }
        }
    }

    private fun openCovidDetail(covid: CityCovid?) {
        covid?.let {
            val covidBottomSheet = CovidBottomSheet.newInstance(it)
            fragmentManager?.let { fragManager ->
                covidBottomSheet.show(fragManager, null)
            }
        }
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::presenter.isInitialized) {
            presenter.onDestroy()
        }
    }

    companion object {
        fun newInstance() = MapsFragment()
    }

}
