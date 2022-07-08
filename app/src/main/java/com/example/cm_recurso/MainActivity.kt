package com.example.cm_recurso

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView


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
        "#BF2222")

    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

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
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
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
            num+= 1
            if (num == 5) {
                num = 0;
            }
            binding.appBarMain.zoneRisk.setBackgroundColor(Color.parseColor(riskColor[num]))
            binding.appBarMain.zoneRisk.text = risk[num]
            handler.postDelayed(this, 5000)
        }
    }

    private fun getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            latitude = location.latitude
            longitude = location.longitude
        }.addOnFailureListener {
            Toast.makeText(this, "Failed on getting current location", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Premission granted
                } else {
                    Toast.makeText(this, "You need to grant permission to access location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}