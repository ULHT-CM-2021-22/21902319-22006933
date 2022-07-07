package com.example.cm_recurso.ui.fires_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private val myAdapter = FireListAdapter()

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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter
        firesViewModel.getAllFires { updateList(it) }
    }

    private fun updateList(fireList : List<FireParceLable>){
        CoroutineScope(Dispatchers.Main).launch {
            myAdapter.updateItems(fireList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}