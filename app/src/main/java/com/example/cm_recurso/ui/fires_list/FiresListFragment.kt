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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FiresListFragment() : Fragment() {
    private lateinit var binding: FragmentFiresListBinding
    private lateinit var firesViewModel : FiresListViewModel
    private val myAdapter = FireListAdapter(onClick = ::onItemClick)

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

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        districtNames()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter
        firesViewModel.getAllFires { updateList(it) }
    }

    private fun updateList(fireList : List<FireParceLable>){
        CoroutineScope(Dispatchers.Main).launch {
            myAdapter.updateItems(fireList)
        }
    }

    private fun districtNames(){
        val spinner: Spinner = binding.spinner

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?,
                                        view: View,
                                        i: Int,
                                        l: Long)
            {
                updateList(getFiresByDistrict(spinner.selectedItem.toString()))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        })

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.district_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun getFiresByDistrict(district:String): List<FireParceLable> {
        if(district == "Todos os Distritos") {
            return firesViewModel.getAllFiresList()
        }

        val fires : MutableList<FireParceLable> = mutableListOf()
        for(fire in firesViewModel.getAllFiresList()) {
            if(fire.distrito == district) {
                fires.add(fire)
            }
        }

        return fires
    }

    private fun onItemClick(fire: FireParceLable) {
        val bundle = bundleOf("fire" to fire)
        findNavController().navigate(R.id.action_nav_fires_list_to_fireDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}