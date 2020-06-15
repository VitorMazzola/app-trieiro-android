package br.com.hackccr.features.splash

import android.content.Intent
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
        startActivity(Intent(this, TabActivity::class.java))
        finish()
    }

}