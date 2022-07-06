package com.example.cm_recurso.ui.fires_map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds


class FiresMapFragment : Fragment() {
    private var _binding: FragmentFiresMapBinding? = null
    private val binding get() = _binding!!
    private val mapcallback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapLoadedCallback {
            val location = LatLng(38.723052962355865, -9.145511816566543)
            val location1 = LatLng(38.74776097850073, -9.159412772335338)
            val location2 = LatLng(38.747323479169985, -9.124492990045807)
            val location3 = LatLng(38.73184521555347, -9.11215186216838)
            val location4 = LatLng(38.75115150741277, -9.194893514982935)
            val bounds = LatLngBounds.builder()
            bounds.include(location)
            bounds.include(location1)
            bounds.include(location2)
            bounds.include(location3)
            bounds.include(location4)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiresMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(mapcallback)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
