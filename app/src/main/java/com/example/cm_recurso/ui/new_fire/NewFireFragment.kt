package com.example.cm_recurso.ui.new_fire

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentNewFireBinding
import com.example.cm_recurso.model.fire.FireDataBase
import com.example.cm_recurso.model.fire.FireModelRoom
import java.text.SimpleDateFormat
import java.util.*

class NewFireFragment : Fragment() {
    private lateinit var binding: FragmentNewFireBinding
    private lateinit var newFireViewModel : NewFireViewModel
    private lateinit var firemodelroom : FireModelRoom

    private var fotoSelected = false
    private var fotoURI: Uri? = null
    val PICK_IMAGE = 1

    private val lat = 0.0
    private val lng = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_new_fire, container, false
        )
        newFireViewModel = ViewModelProvider(this).get(NewFireViewModel::class.java)
        binding = FragmentNewFireBinding.bind(view)
        firemodelroom = FireModelRoom(FireDataBase.getInstance(requireContext()).fireDao())

        populateSpinner()
        binding.buttonSubmitForm.setOnClickListener{
            if (validateForm()) insertFire()
        }

        binding.buttonAddPhoto.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
        }

        return binding.root
    }

    private fun validateForm(): Boolean {
        val name = binding.newFirePersonName.text.toString()
        val cc = binding.newFirePersonCc.text.toString()
        val district = binding.spinner.selectedItem.toString()

        if (name.isEmpty() || cc.isEmpty()) {
            Toast.makeText(context,"Campos obrigatórios em falta",Toast.LENGTH_SHORT).show()
            return false
        }

        if (district.equals("Selecione o distrito")) {
            Toast.makeText(context,"Selecione o distrito",Toast.LENGTH_SHORT).show()
            return false
        }

        if (cc.length != 9) {
            Toast.makeText(context,"Cartão de cidadão inválido",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun insertFire() {
        val name = binding.newFirePersonName.text.toString()
        val cc = binding.newFirePersonCc.text.toString()
        val district = binding.spinner.selectedItem.toString()
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val hour = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val foto = getFotoURI()

        firemodelroom.addFire(
                name = name,
                cartaoCidadao = cc,
                distrito = district,
                conselho = "",
                frequesia = "",
                data = date,
                hora = hour,
                status = "Por confirmar",
                fotografia = foto,
                distance = "",
                man = "0",
                terrestrial = "0",
                aerial = "0",
                lat = lat,
                lng = lng,
                isRegistry = "true"
            )

        Toast.makeText(activity, "Incêndio inserido", Toast.LENGTH_SHORT).show()

        binding.newFirePersonCc.text.clear()
        binding.newFirePersonName.text.clear()
        binding.newFireObservations.text.clear()
        binding.spinner.setSelection(0)
        fotoSelected = false
    }

    private fun populateSpinner(){
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.district_list_new,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            fotoURI = data?.data
            binding.buttonAddPhoto.setImageURI(fotoURI)
            fotoSelected = true
        }
    }

    private fun getFotoURI() : String {
        return if(fotoSelected){
            fotoURI.toString()
        }else{
            ""
        }
    }

    private fun uriGenerator(draw : Int) : Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(draw))
            .appendPath(resources.getResourceTypeName(draw))
            .appendPath(resources.getResourceEntryName(draw))
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}