package br.com.hackccr

import android.app.Application
import android.content.Context
import br.com.hackccr.di.component.ApplicationComponent
import br.com.hackccr.di.component.DaggerApplicationComponent
import br.com.hackccr.di.module.ApplicationModule

class App : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    companion object {
        @JvmStatic
        fun getAppComponent(context: Context?): ApplicationComponent? {
            context?.let {
                return (context.applicationContext as App).component
            }
            return null
        }
    }
}