package br.com.hackccr.features.maps

import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.hackccr.App
import br.com.hackccr.R
import br.com.hackccr.data.CategoryEnum
import br.com.hackccr.data.Point
import br.com.hackccr.features.maps.di.DaggerMapComponent
import br.com.hackccr.features.maps.di.MapModule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
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

        presenter.onCreate()
    }

    private fun initDagger() {
        DaggerMapComponent.builder()
            .applicationComponent(App.getAppComponent(activity!!))
            .mapModule(MapModule(this))
            .build()
            .inject(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
        buildGoogleApiClient()
        mMap.isMyLocationEnabled = true

//        val latLng = LatLng(-46.58489, -23.19081)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
//        mMap.addMarker(MarkerOptions()
//            .position(latLng)
//            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck_map)))
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

//        val latLng = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        val latLng = LatLng(-23.4286353, -46.4119596)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        mMap.addMarker(MarkerOptions()
            .position(latLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck_map)))
        presenter.onCreate()
    }

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

    override fun configMapCategory(points: List<Point>?) {
        points?.forEach {
            val latLng = LatLng(it.lat.toDouble(), it.lng.toDouble())
            when(it.getCategory()) {
                CategoryEnum.GAS_STATION -> addCustomMarker(latLng, R.drawable.gas, it.fantasyName)
                CategoryEnum.RESTAURANT -> addCustomMarker(latLng, R.drawable.restaurante, it.fantasyName)
                CategoryEnum.MECHANICAL_WORKSHOP -> addCustomMarker(latLng, R.drawable.mecanico, it.fantasyName)
                CategoryEnum.BUTTERFLY -> addCustomMarker(latLng, R.drawable.mecanico, it.fantasyName)
                CategoryEnum.ACCOMMODATION -> addCustomMarker(latLng, R.drawable.dormir, it.fantasyName)
                CategoryEnum.STOP_POINT -> addCustomMarker(latLng, R.drawable.restaurante, it.fantasyName)
                else ->  mMap.addMarker(MarkerOptions().position(latLng).title(it.fantasyName))
            }

        }
    }

    private fun addCustomMarker(latLng: LatLng, icon: Int, title: String) {
        mMap.addMarker(MarkerOptions()
            .position(latLng)
            .title(title)
            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon))))
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
