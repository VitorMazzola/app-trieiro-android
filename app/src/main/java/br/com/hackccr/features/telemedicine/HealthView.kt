package br.com.hackccr.features.telemedicine

import br.com.hackccr.data.HealthItem

interface HealthView {
    fun configureHealthItems(items: List<HealthItem>)
}