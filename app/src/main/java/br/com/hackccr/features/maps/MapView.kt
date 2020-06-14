package br.com.hackccr.features.maps

import br.com.hackccr.data.Point

interface MapView {
    fun configMapCategory(points: List<Point>?)
    fun showError(error: Throwable)
}