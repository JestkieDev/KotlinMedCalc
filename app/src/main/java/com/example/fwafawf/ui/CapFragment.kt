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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fwafawf.R
import com.example.fwafawf.db.MainDb
import com.example.fwafawf.db.MainDb.Companion.getINSTANCE
import com.example.fwafawf.db.Pacient
import com.example.fwafawf.db.PacientDao


class CapFragment : Fragment() {
    var mainDb: MainDb? = null
    var pacientDao: PacientDao? = null
    var pacients: List<Pacient?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        val view = inflater.inflate(R.layout.fragment_cap, container, false)
        val capET = view.findViewById<EditText>(R.id.capET)
        val mcapET = view.findViewById<EditText>(R.id.mcapET)
        val okcapBTN = view.findViewById<Button>(R.id.okcapBTN)
        val rescapTV = view.findViewById<TextView>(R.id.rescapTV)
        val backBTN = view.findViewById<Button>(R.id.backcapBTN)
        val capSPN = view.findViewById<Spinner>(R.id.capSPN)
        mainDb = getINSTANCE(requireContext())
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
        capSPN.adapter = arrayAdapter
        okcapBTN.setOnClickListener {
            if ((capET.text.toString().isEmpty() == false) and (mcapET.text.toString()
                    .isEmpty() == false) and (pacientDao!!.all!!.size > 0)
            ) {
                val cap = capET.text.toString().toInt()
                val mcap = mcapET.text.toString().toInt()
                val res = cap * 20 / mcap
                rescapTV.text = "$res капель в мин"
                val pacients1: MutableList<Pacient?> = ArrayList()
                pacients1.addAll(pacientDao!!.getWithName(capSPN.selectedItem.toString())!!)
                pacients1[0]!!.cap = res.toDouble()
                pacientDao!!.update(pacients1[0]!!)

            } else {
                Toast.makeText(context, "Введите данные!", Toast.LENGTH_LONG).show()
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