package br.com.hackccr.features.maps.detail.di

import br.com.hackccr.di.component.ApplicationComponent
import br.com.hackccr.di.scope.ActivityScope
import br.com.hackccr.features.maps.detail.PointDetailActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [(ApplicationComponent::class)], modules = [(PointDetailModule::class)])
interface PointDetailComponent {
    fun inject(activity: PointDetailActivity)
}