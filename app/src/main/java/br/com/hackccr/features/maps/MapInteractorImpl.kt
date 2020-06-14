package br.com.hackccr.features.maps

import br.com.hackccr.api.Api
import br.com.hackccr.data.Point
import rx.Observable

class MapInteractorImpl(private val api: Api) : MapInteractor {

    override fun getPoints(): Observable<List<Point>> {
        return api.getPoints("SP", "Guarulhos")
    }
}