package br.com.hackccr.features.maps

import com.google.android.gms.maps.model.LatLng

interface MapPresenter {
    fun onCreate(latLng: LatLng)
    fun onDestroy()
}