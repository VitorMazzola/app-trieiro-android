package br.com.hackccr.features.telemedicine.di

import br.com.hackccr.di.component.ApplicationComponent
import br.com.hackccr.di.scope.FragmentScope
import br.com.hackccr.features.telemedicine.TelemedicineFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [(ApplicationComponent::class)], modules = [(HealthModule::class)])
interface HealthComponent {
    fun inject(fragment: TelemedicineFragment)
}