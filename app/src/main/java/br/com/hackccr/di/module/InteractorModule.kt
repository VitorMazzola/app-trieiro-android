package br.com.hackccr.di.module

import br.com.hackccr.api.Api
import br.com.hackccr.features.maps.MapInteractor
import br.com.hackccr.features.maps.MapInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {
    @Provides
    fun providesMapInteractor(api: Api) : MapInteractor {
        return MapInteractorImpl(api)
    }
}