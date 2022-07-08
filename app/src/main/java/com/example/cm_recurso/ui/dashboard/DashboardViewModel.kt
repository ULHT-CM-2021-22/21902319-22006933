package com.example.cm_recurso.ui.dashboard

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FireRepository.getInstance()

    fun getAllFiresList(): List<FireParceLable> {
        return repository.getAllFiresList()
    }

    fun getStatistics() : Map<String, String> {
        val fires = getAllFiresList()
        var totalFires = 0
        var totalFireman = 0
        var totalTerrestrial = 0
        var totalAerial = 0

        val statistics = mutableMapOf(
            "totalFires" to "0",
            "totalFireman" to "0",
            "totalTerrestrial" to "0",
            "totalAerial" to "0",
        )

        for (fire in fires) {
            totalFires++
            totalFireman += fire.man.toInt()
            totalTerrestrial += fire.terrestrial.toInt()
            totalAerial += fire.aerial.toInt()
        }

        statistics["totalFires"] = totalFires.toString()
        statistics["totalFireman"] = totalFireman.toString()
        statistics["totalTerrestrial"] = totalTerrestrial.toString()
        statistics["totalAerial"] = totalAerial.toString()

        return statistics
    }
}