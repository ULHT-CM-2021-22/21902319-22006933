package com.example.cm_recurso.ui.fires

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresBinding

class FiresFragment : Fragment() {
    private var _binding: FragmentFiresBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fires, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
