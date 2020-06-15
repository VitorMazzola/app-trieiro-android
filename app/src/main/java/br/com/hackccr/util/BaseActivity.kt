package br.com.hackccr.util

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.hackccr.features.maps.MapsFragment
import br.com.hackccr.features.courses.CoursesFragment
import br.com.hackccr.features.profile.ProfileFragment
import br.com.hackccr.features.telemedicine.TelemedicineFragment

open class BaseActivity : AppCompatActivity() {

    fun configToolbar(frag: Fragment?, menu: MenuItem?) {
        frag?.let { fragment ->
            when (fragment) {
                is MapsFragment -> {
                    title = "Mapa"
                    isMenuVisible(menu, true)
                }
                is TelemedicineFragment -> {
                    title = "Saúde"
                    isMenuVisible(menu, false)
                }
                is CoursesFragment -> {
                    title = "Cursos"
                    isMenuVisible(menu, false)
                }
                is ProfileFragment -> {
                    title = "Prêmios"
                    isMenuVisible(menu, false)
                }
                else -> {}
            }

        }

    }

    open fun isMenuVisible(menu: MenuItem?, isVisible: Boolean) {
        menu?.isVisible = isVisible
    }

}