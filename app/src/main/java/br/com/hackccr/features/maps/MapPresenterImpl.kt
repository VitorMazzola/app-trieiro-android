package br.com.hackccr.features.maps

import br.com.hackccr.util.RxComposer
import com.google.android.gms.maps.model.LatLng
import rx.internal.util.SubscriptionList

class MapPresenterImpl(private val view: MapView,
                       private val interactor: MapInteractor) : MapPresenter {

    private val subscriptions: SubscriptionList = SubscriptionList()

    override fun onCreate(latLng: LatLng) {
        getPointsMaps(latLng)
        getCovidCases(latLng)
    }

    private fun getPointsMaps(latLng: LatLng) {
        subscriptions.add(interactor.getPoints(latLng)
            .compose(RxComposer.ioThread())
            .toList()
            .subscribe({
                it.forEach { response ->
                    view.configMapCategory(response)
                }
            }) { e ->
                view.showError(e)
            }
        )
    }

    private fun getCovidCases(latLng: LatLng) {
        subscriptions.add(interactor.getCovidCases(latLng)
            .compose(RxComposer.ioThread())
            .toList()
            .subscribe({
                it.forEach { response ->
                    view.configMapCovid(response)
                }
            }) { e ->
                view.showError(e)
            }
        )
    }

    override fun onDestroy() {
        if(subscriptions.hasSubscriptions()) {
            subscriptions.unsubscribe()
        }
    }

}