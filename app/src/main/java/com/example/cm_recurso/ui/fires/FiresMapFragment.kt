package com.example.cm_recurso.ui.fires

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresListBinding
import com.example.cm_recurso.databinding.FragmentFiresMapBinding

class FiresMapFragment : Fragment() {
    private var _binding: FragmentFiresMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiresMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.firesList.setOnClickListener{
            Log.d("TAG", "message")
            findNavController().navigate(R.id.action_nav_fires_map_to_nav_fires_list)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
