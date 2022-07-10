package com.example.cm_recurso.ui.fires_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresMapBinding
import com.example.cm_recurso.model.fire.FireParceLable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class FiresMapFragment : Fragment() {
    private lateinit var binding: FragmentFiresMapBinding
    private lateinit var firesMapViewModel : FiresMapViewModel

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

        val fires = firesMapViewModel.getActiveFire()
        val markerColors = mapOf("Conclusão" to BitmapDescriptorFactory.HUE_GREEN, "Despacho de 1º Alerta" to BitmapDescriptorFactory.HUE_ORANGE, "Em Curso" to BitmapDescriptorFactory.HUE_RED, "Em Resolução" to BitmapDescriptorFactory.HUE_AZURE)

        for (fire in fires) {
            googleMap.addMarker(
                MarkerOptions()
                    .title(fire.status)
                    .position(LatLng(fire.lat, fire.lng))
                    .icon(
                        markerColors[fire.status]?.let {
                            BitmapDescriptorFactory
                                .defaultMarker(it)
                        })
            )
        }
        googleMap.setOnMarkerClickListener { marker ->
            val lat = marker.position.latitude
            val lng = marker.position.longitude
            val fire = getFire(fires, lat, lng)
            val bundle = bundleOf("fire" to fire)
            findNavController().navigate(R.id.action_nav_fires_map_to_fireDetailsFragment, bundle)

            true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_fires_map, container, false
        )
        firesMapViewModel = ViewModelProvider(this).get(FiresMapViewModel::class.java)
        binding = FragmentFiresMapBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(mapcallback)

        return binding.root
    }

    private fun getFire(fires : List<FireParceLable>, latitute : Double, longitute : Double): FireParceLable? {
        for (fire in fires) {
            if (fire.lat.equals(latitute) && fire.lng.equals(longitute)) return fire
        }
        return null
    }
}
