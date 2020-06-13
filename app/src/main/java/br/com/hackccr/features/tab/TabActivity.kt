package br.com.hackccr.features.tab

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.hackccr.BuildConfig
import br.com.hackccr.R
import br.com.hackccr.features.maps.MapsFragment
import br.com.hackccr.features.news.NewsFragment
import br.com.hackccr.features.profile.ProfileFragment
import br.com.hackccr.features.telemedicine.TelemedicineFragment
import br.com.hackccr.util.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity: BaseActivity(), TabView, FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.content)
    override val numberOfRootFragments: Int = 8

    companion object {
        const val PARAM_ACTIVE_TAB = "PARAM_ACTIVE_TAB"

        const val MAP_TAB = FragNavController.TAB1
        const val TELEMEDICINE_TAB = FragNavController.TAB2
        const val NEWS_TAB = FragNavController.TAB3
        const val PROFILE_TAB = FragNavController.TAB4

        const val MAP_VALUE = "map"
        const val TELEMEDICINE_VALUE = "telemedicine"
        const val NEWS_VALUE = "news"
        const val PROFILE_VALUE = "profile"

        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, TabActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        fragNavController.apply {
            rootFragmentListener = this@TabActivity
            transactionListener = this@TabActivity
            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().allowStateLoss(true).build()
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {}
            }
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.selectTabAtPosition(index)
                }
            })
        }

        fragNavController.initialize(MAP_TAB, savedInstanceState)

        if (savedInstanceState == null && intent.hasExtra(PARAM_ACTIVE_TAB)) {
            bottomBar.selectTabAtPosition(intent.extras?.getInt(PARAM_ACTIVE_TAB) ?: MAP_TAB)
        } else {
            bottomBar.selectTabAtPosition(MAP_TAB)
        }

        try {
            fragNavController.executePendingTransactions()
        } catch (ignored: Exception) {
        }

        bottomBar.setOnTabSelectListener {tabId ->
            when(tabId) {
                R.id.navigation_map -> {
                    checkPermissions()
                }
                R.id.navigation_telemedicine -> fragNavController.switchTab(TELEMEDICINE_TAB)
                R.id.navigation_news -> fragNavController.switchTab(NEWS_TAB)
                R.id.navigation_profile -> fragNavController.switchTab(PROFILE_TAB)
            }
        }
    }

    override fun goToTabWithId(tabId: Int) {
        bottomBar.selectTabWithId(tabId)
    }

    override fun getFragmentController(): FragNavController? {
        return fragNavController
    }

    override fun getRootFragment(index: Int): Fragment {
        when(index) {
            MAP_TAB -> return MapsFragment.newInstance()
            TELEMEDICINE_TAB -> return TelemedicineFragment.newInstance()
            NEWS_TAB -> return NewsFragment.newInstance()
            PROFILE_TAB -> return ProfileFragment.newInstance()
        }
        throw IllegalStateException("TabActivity - Invalid fragment called:$index")
    }

    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {}

    override fun onTabTransaction(fragment: Fragment?, index: Int) {}

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun checkPermissions() {
        Dexter.withActivity(this)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        fragNavController.switchTab(MAP_TAB)
                    }

                    if(report.isAnyPermissionPermanentlyDenied) {
                        showDialogSettings()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken) {
                    token.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()
    }

    private fun showDialogSettings() {
        AlertDialog.Builder(this)
            .setTitle("Não temos acesso a sua localização")
            .setMessage("Você o pode mudar o acesso à sua localização nos Ajustes do seu aparelho")
            .setPositiveButton("Ir para ajustes") { _, _ ->
                startActivity(getIntentPermissionSettings())
            }
            .setNegativeButton("Agora não", null)
            .create()
            .show()
    }

    private fun getIntentPermissionSettings() : Intent {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        return intent
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.settings_menu) {
            Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}