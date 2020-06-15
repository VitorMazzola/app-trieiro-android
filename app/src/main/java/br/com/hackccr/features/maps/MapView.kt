package br.com.hackccr.features.maps

import br.com.hackccr.data.CityCovid
import br.com.hackccr.data.Point

interface MapView {
    fun configMapCategory(points: List<Point>?)
    fun configMapCovid(cities: List<CityCovid>?)
    fun showError(error: Throwable)
}