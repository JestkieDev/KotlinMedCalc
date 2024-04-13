package com.example.fwafawf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fwafawf.R
import com.example.fwafawf.db.MainDb
import com.example.fwafawf.db.MainDb.Companion.getINSTANCE
import com.example.fwafawf.db.Pacient
import com.example.fwafawf.db.PacientDao

import java.text.DecimalFormat

class ImtFragment : Fragment() {
    var pacientDao: PacientDao? = null
    var pacients: List<Pacient?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_imt, container, false)
        val mET = view.findViewById<EditText>(R.id.mET)
        val hET = view.findViewById<EditText>(R.id.hET)
        val okimtBTN = view.findViewById<Button>(R.id.okimtBTN)
        val reimtTV = view.findViewById<TextView>(R.id.resimtTV)
        val backBTN = view.findViewById<Button>(R.id.backimtBTN)
        val imtSPN = view.findViewById<Spinner>(R.id.imtSPN)
        var mainDb: MainDb? = getINSTANCE(requireContext())
        val pacientNames = ArrayList<String>()
        pacientDao = mainDb!!.pacientDao()
        pacients = ArrayList()
        if (pacientDao!!.all != null) {
            pacients = pacientDao!!.all
        }
        for (i in pacients!!.indices) {
            pacientNames.add(pacients!![i]!!.name)
        }
        val arrayAdapter: ArrayAdapter<String>
        arrayAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            pacientNames
        )
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        imtSPN.adapter = arrayAdapter
        okimtBTN.setOnClickListener {
            if ((mET.text.toString().isEmpty() == false) and (hET.text.toString()
                    .isEmpty() == false)
            ) {
                val mass = mET.text.toString().toInt()
                val height = hET.text.toString().toInt().toDouble() / 100
                val res = mass / Math.pow(height, 2.0)
                val df = DecimalFormat("###.#")
                reimtTV.text = df.format(res)
                if (imtSPN.selectedItem.toString() != null) {
                    val pacients1: MutableList<Pacient?> = ArrayList()
                    pacients1.addAll(pacientDao!!.getWithName(imtSPN.selectedItem.toString())!!)
                    pacients1[0]!!.imt = res
                    pacientDao!!.update(pacients1[0]!!)
                } else {
                    Toast.makeText(context, "Введите значения!", Toast.LENGTH_LONG).show()
                }
            }
        }
        backBTN.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            }
        }
        return view
    }
}