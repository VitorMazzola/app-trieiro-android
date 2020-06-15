package br.com.hackccr.features.maps.detail

import br.com.hackccr.data.Comment

interface PointDetailView {
    fun configureComments(comments: List<Comment>?)
}