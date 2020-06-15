package br.com.hackccr.features.maps.detail.di

import br.com.hackccr.features.maps.detail.PointDetailPresenter
import br.com.hackccr.features.maps.detail.PointDetailPresenterImpl
import br.com.hackccr.features.maps.detail.PointDetailView
import dagger.Module
import dagger.Provides

@Module
class PointDetailModule(private val view: PointDetailView) {

    @Provides
    fun providesView() : PointDetailView {
        return view
    }

    @Provides
    fun providesPresenter(): PointDetailPresenter {
        return PointDetailPresenterImpl(view)
    }
}