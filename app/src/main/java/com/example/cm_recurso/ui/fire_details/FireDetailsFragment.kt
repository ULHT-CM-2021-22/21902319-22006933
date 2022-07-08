package com.example.cm_recurso.ui.fire_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiredetailsBinding
import com.example.cm_recurso.model.fire.FireParceLable


class FireDetailsFragment : Fragment() {
    private lateinit var fire : FireParceLable
    private lateinit var binding : FragmentFiredetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_firedetails, container, false
        )
        binding = FragmentFiredetailsBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.title = "Detalhes do incêndio"
        fire = arguments?.getParcelable<FireParceLable>("fire")!!
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        fire.let{
            binding.fireStatusInput.text = it.status
            binding.fireLocationInput.text = it.distrito
            binding.fireLocation1Input.text = it.conselho
            binding.fireLocation2Input.text = it.frequesia
            binding.fireDateInput.text = it.data
            binding.fireHourInput.text = it.hora
            binding.fireManInput.text = it.man
            binding.fireCarInput.text = it.terrestrial
            binding.firePlainInput.text = it.aerial
        }
    }
}
