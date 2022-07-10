package com.example.cm_recurso.ui.fires_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresListBinding
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.location.FusedLocation
import com.example.cm_recurso.ui.location.OnLocationChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FiresListFragment() : Fragment(), OnLocationChangedListener{
    private lateinit var binding: FragmentFiresListBinding
    private lateinit var firesViewModel : FiresListViewModel
    private var currentLat = 0.0
    private var currentLng = 0.0
    private val adapter = FireListAdapter(onClick = ::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_fires_list, container, false
        )

        firesViewModel = ViewModelProvider(this).get(FiresListViewModel::class.java)
        binding = FragmentFiresListBinding.bind(view)
        FusedLocation.registerListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        setupSpinner()
        //spinnerOrganize()
        firesViewModel.getAllFires { updateList(it)}
    }

    private fun updateList(fireList : List<FireParceLable>){
        CoroutineScope(Dispatchers.Main).launch {
            adapter.updateItems(fireList)
        }
    }

    private fun setupSpinner() {
        val spinnerDistrict: Spinner = binding.spinnerDistrict
        val spinnerOrganize: Spinner = binding.spinnerOrganize

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.district_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDistrict.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.organize_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOrganize.adapter = adapter
        }

        spinnerDistrict.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                        updateList(firesViewModel.getFiresByDistrict(spinnerDistrict.selectedItem.toString(),
                            spinnerOrganize.selectedItem.toString(), currentLat, currentLng))
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    return
                }
            }
        )

        spinnerOrganize.setOnItemSelectedListener(
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                    updateList(firesViewModel.getFiresByDistrict(spinnerDistrict.selectedItem.toString(),
                        spinnerOrganize.selectedItem.toString(), currentLat, currentLng))
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    return
                }
            }
        )

    }


    private fun onItemClick(fire: FireParceLable) {
        val bundle = bundleOf("fire" to fire)
        findNavController().navigate(R.id.action_nav_fires_list_to_fireDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onLocationChanged(latitude: Double, longitude: Double) {
        currentLat = latitude
        currentLng = longitude
    }

}