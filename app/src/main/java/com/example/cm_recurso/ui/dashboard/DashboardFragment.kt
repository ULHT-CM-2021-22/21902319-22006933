package com.example.cm_recurso.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentDashboardBinding
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel : DashboardViewModel
    private val repository = FireRepository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_dashboard, container, false
        )
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = FragmentDashboardBinding.bind(view)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        repository.getAllFires { updateDashboard(it) }
    }

    private fun updateDashboard(fireList : List<FireParceLable>){
        CoroutineScope(Dispatchers.Main).launch {
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

            for (fire in fireList) {
                totalFires++
                totalFireman += fire.man.toInt()
                totalTerrestrial += fire.terrestrial.toInt()
                totalAerial += fire.aerial.toInt()
            }

            statistics["totalFires"] = totalFires.toString()
            statistics["totalFireman"] = totalFireman.toString()
            statistics["totalTerrestrial"] = totalTerrestrial.toString()
            statistics["totalAerial"] = totalAerial.toString()

            binding.firesTotal.text = statistics["totalFires"]
            binding.firesAereal.text = statistics["totalAerial"]
            binding.firesMan.text = statistics["totalFireman"]
            binding.firesTerrestrial.text = statistics["totalTerrestrial"]
        }
    }
}