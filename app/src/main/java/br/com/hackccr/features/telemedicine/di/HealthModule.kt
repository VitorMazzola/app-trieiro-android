package br.com.hackccr.features.telemedicine.di

import br.com.hackccr.features.telemedicine.HealthPresenter
import br.com.hackccr.features.telemedicine.HealthPresenterImpl
import br.com.hackccr.features.telemedicine.HealthView
import dagger.Module
import dagger.Provides

@Module
class HealthModule(private val view: HealthView) {

    @Provides
    fun providesView() : HealthView {
        return view
    }

    @Provides
    fun providesPresenter(): HealthPresenter {
        return HealthPresenterImpl(view)
    }
}