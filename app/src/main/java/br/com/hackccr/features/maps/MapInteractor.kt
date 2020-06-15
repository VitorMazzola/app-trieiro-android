package br.com.hackccr.features.maps

import br.com.hackccr.data.CityCovid
import br.com.hackccr.data.Point
import com.google.android.gms.maps.model.LatLng
import rx.Observable

interface MapInteractor {
    fun getPoints(latLng: LatLng) : Observable<List<Point>>
    fun getCovidCases(latLng: LatLng) : Observable<List<CityCovid>>
}