package com.example.cm_recurso.ui.settings

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cm_recurso.MainActivity
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_settings, container, false
        )
        binding = FragmentSettingsBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"

        val main: MainActivity? = activity as MainActivity?
        if (main != null) {
            binding.riskZoneToggle.setChecked(main.getRiskZone())
        }

        binding.riskZoneToggle.setOnCheckedChangeListener { _, isChecked ->
            if (main != null) {
                main.setRiskZone(isChecked)
            }
        }


        return binding.root
    }

}
