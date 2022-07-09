package com.example.cm_recurso

import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cm_recurso.databinding.ActivityMainBinding
import com.example.cm_recurso.model.fire.FireApi
import com.example.cm_recurso.model.fire.FireDataBase
import com.example.cm_recurso.model.fire.FireModelRoom
import com.example.cm_recurso.ui.location.FusedLocation
import com.example.cm_recurso.ui.repository.FireRepository
import com.example.cm_recurso.ui.repository.RetrofitBuilder
import com.google.android.material.navigation.NavigationView

var changeZoneRisk = true

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var num = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var risk: ArrayList<String>
    private var riskColor = arrayListOf(
        "#23D30F",
        "#D7EC0C",
        "#F4C63E",
        "#B36C02",
        "#BF2222",
        "#828384")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireRepository.init(this,
            FireModelRoom(FireDataBase.getInstance(this).fireDao()),
            FireApi(RetrofitBuilder.getInstance("https://api-dev.fogos.pt"))
        )
        FusedLocation.start(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard, R.id.nav_fires_list, R.id.nav_fires_map, R.id.nav_new_fire
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        risk = arrayListOf(
            getString(R.string.risk_low),
            getString(R.string.risk_moderate),
            getString(R.string.risk_high),
            getString(R.string.risk_very_high),
            getString(R.string.risk_max),
            ""
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val pendingIntent = NavDeepLinkBuilder(this.applicationContext)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.settingsFragment2)
                .createPendingIntent()

            pendingIntent.send()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(ativo, 5000)
    }

    private val ativo = object: Runnable {
        override fun run() {

            val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
            val batLevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

            if (batLevel <= 20 && changeZoneRisk) {
                binding.appBarMain.zoneRisk.setBackgroundColor(Color.parseColor(riskColor[5]))
                binding.appBarMain.zoneRisk.text = risk[5]
                handler.postDelayed(this, 5000)
            } else {
                num+= 1
                if (num == 5) {
                    num = 0;
                }
                binding.appBarMain.zoneRisk.setBackgroundColor(Color.parseColor(riskColor[num]))
                binding.appBarMain.zoneRisk.text = risk[num]
                handler.postDelayed(this, 5000)
            }

        }
    }

    public fun getRiskZone(): Boolean {
        return changeZoneRisk
    }

    public fun setRiskZone(risk: Boolean) {
        changeZoneRisk = risk
    }
}