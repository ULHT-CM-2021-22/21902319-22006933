package com.example.cm_recurso.ui.fires_list

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cm_recurso.R
import com.example.cm_recurso.databinding.FragmentFiresListBinding
import com.example.cm_recurso.model.fire.FireParceLable
import com.example.cm_recurso.ui.repository.FireRepository


class FiresListFragment() : Fragment() {
    private var _binding: FragmentFiresListBinding? = null
    private val binding get() = _binding!!
    private lateinit var firesViewModel : FiresListViewModel
    private lateinit var items: List<FireParceLable>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val teste = FireRepository.getInstance()
        Log.d(TAG, "ALJSNJLASLKALSAJLKSJKLAJKLSLJKASLK -> " + teste)
        firesViewModel = ViewModelProvider(this).get(FiresListViewModel::class.java)

        _binding = FragmentFiresListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.adapter = MyFiresAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onItemClick(position: Int) {
        Toast.makeText(this.context,"Item $position" , Toast.LENGTH_SHORT).show()
        val clickedItem: FireParceLable = items[position]
        val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.popup_detail,null)
        val mBuilder = AlertDialog.Builder(this.context).setView(mDialogView)

        var nome: TextView = mDialogView.findViewById(R.id.nomePop)
        var cc: TextView = mDialogView.findViewById(R.id.ccPop)
        var destrito: TextView = mDialogView.findViewById(R.id.destritoPop)
        var data: TextView = mDialogView.findViewById(R.id.dataPop)
        //var fotografia: TextView = mDialogView.findViewById(R.id.fotografia)

        nome.text = "Nome: " + clickedItem.name
        cc.text = "Documento de identificação. " + clickedItem.cartaoCidadao
        destrito.text = "Localização: " + clickedItem.distrito
        data.text = "Data do registo: " + clickedItem.data

        mBuilder.show()

        binding.recyclerView.adapter?.notifyItemChanged(position)
    }

}