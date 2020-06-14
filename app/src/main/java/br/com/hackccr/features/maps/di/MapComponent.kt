package br.com.hackccr.features.maps.di

import br.com.hackccr.di.component.ApplicationComponent
import br.com.hackccr.di.scope.FragmentScope
import br.com.hackccr.features.maps.MapsFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [(ApplicationComponent::class)], modules = [(MapModule::class)])
interface MapComponent {
    fun inject(fragment: MapsFragment)
}