package br.com.hackccr.features.maps

import br.com.hackccr.api.Api
import br.com.hackccr.data.CityCovid
import br.com.hackccr.data.Point
import com.google.android.gms.maps.model.LatLng
import rx.Observable

class MapInteractorImpl(private val api: Api) : MapInteractor {

    override fun getPoints(latLng: LatLng): Observable<List<Point>> {
        return api.getPoints(latLng.latitude, latLng.longitude)
    }

    override fun getCovidCases(latLng: LatLng): Observable<List<CityCovid>> {
        return api.getCovidCases(latLng.latitude, latLng.longitude)
    }
}