package com.example.cm_recurso.ui.fires_map

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
            val location = LatLng(41.848573, -8.846538)
            val location1 = LatLng(42.137140, -8.202493)
            val location2 = LatLng(41.962788, -6.586416)
            val location3 = LatLng(41.575610, -6.192212)
            val location4 = LatLng(36.975305, -7.897062)
            val location5 = LatLng(38.779334, -9.497251)
            val bounds = LatLngBounds.builder()
            bounds.include(location)
            bounds.include(location1)
            bounds.include(location2)
            bounds.include(location3)
            bounds.include(location4)
            bounds.include(location5)
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
