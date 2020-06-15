package br.com.hackccr.features.telemedicine

import br.com.hackccr.R
import br.com.hackccr.data.HealthItem

class HealthPresenterImpl(private val view: HealthView) : HealthPresenter {

    override fun onCreate() {
        val items = mutableListOf<HealthItem>()
        items.add(HealthItem(R.drawable.ic_health_calendar, "Agendar consulta online"))
        items.add(HealthItem(R.drawable.ic_health_stack, "Receitas médicas"))
        items.add(HealthItem(R.drawable.ic_patient, "Histórico de paciente"))
        items.add(HealthItem(R.drawable.ic_medical_request, "Pedidos médicos"))

        view.configureHealthItems(items)
    }

    override fun onDestroy() {
        // Implement when has backend integrating
    }
}