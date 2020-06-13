package br.com.hackccr.di.component

import android.content.Context
import br.com.hackccr.App
import br.com.hackccr.di.module.ApiModule
import br.com.hackccr.di.module.ApplicationModule
import br.com.hackccr.di.module.InteractorModule
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        ApplicationModule::class,
        InteractorModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: App)

    fun gson() : Gson

    fun context(): Context
}