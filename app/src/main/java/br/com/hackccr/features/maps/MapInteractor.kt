package br.com.hackccr.features.maps

import br.com.hackccr.data.Point
import rx.Observable

interface MapInteractor {
    fun getPoints() : Observable<List<Point>>
}