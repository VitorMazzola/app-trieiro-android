package br.com.hackccr.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.hackccr.R
import br.com.hackccr.features.tab.TabActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        routeToAppropriatePage()
    }

    private fun routeToAppropriatePage() {
        TabActivity.start(this)
        finish()
    }

}