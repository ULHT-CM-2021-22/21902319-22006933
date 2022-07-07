package com.example.cm_recurso.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel : DashboardViewModel

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
        val statistics = dashboardViewModel.getStatistics()
        binding.firesTotal.text = statistics["totalFires"]
        binding.firesAereal.text = statistics["totalAerial"]
        binding.firesMan.text = statistics["totalFireman"]
        binding.firesTerrestrial.text = statistics["totalTerrestrial"]
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}