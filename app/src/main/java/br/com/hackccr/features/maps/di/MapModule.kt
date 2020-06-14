package br.com.hackccr.features.maps.di

import br.com.hackccr.features.maps.MapInteractor
import br.com.hackccr.features.maps.MapPresenter
import br.com.hackccr.features.maps.MapPresenterImpl
import br.com.hackccr.features.maps.MapView
import dagger.Module
import dagger.Provides

@Module
class MapModule(private val view: MapView) {

    @Provides
    fun providesView() : MapView {
        return view
    }

    @Provides
    fun providesPresenter(interactor: MapInteractor): MapPresenter {
        return MapPresenterImpl(view, interactor)
    }
}