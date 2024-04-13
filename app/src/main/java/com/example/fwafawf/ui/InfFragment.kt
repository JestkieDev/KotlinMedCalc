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

class InfFragment : Fragment() {
    var mainDb: MainDb? = null
    var pacientDao: PacientDao? = null
    var pacients: List<Pacient?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inf, container, false)
        val mgET = view.findViewById<EditText>(R.id.mgET)
        val mlET = view.findViewById<EditText>(R.id.mlET)
        val minfET = view.findViewById<EditText>(R.id.minfET)
        val dozET = view.findViewById<EditText>(R.id.dozET)
        val okinfBTN = view.findViewById<Button>(R.id.okinfBTN)
        val resinfTV = view.findViewById<TextView>(R.id.resinfTV)
        val backBTN = view.findViewById<Button>(R.id.backinfBTN)
        val infSPN = view.findViewById<Spinner>(R.id.infSPN)
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
        infSPN.adapter = arrayAdapter
        okinfBTN.setOnClickListener {
            if ((mgET.text.toString().isEmpty() == false) and (mlET.text.toString()
                    .isEmpty() == false) and (minfET.text.toString()
                    .isEmpty() == false) and (dozET.text.toString().isEmpty() == false)
            ) {
                val mg = mgET.text.toString().toInt()
                val ml = mlET.text.toString().toInt()
                val minf = minfET.text.toString().toInt()
                val doz = dozET.text.toString().toInt()
                val res = minf.toDouble() * doz / (mg * (1000.0 / ml)) * 60
                val df = DecimalFormat("###.##")
                resinfTV.text = df.format(res) + " мл/ч"
                if (infSPN.selectedItem.toString() != null) {
                    val pacients1: MutableList<Pacient?> = ArrayList()
                    pacients1.addAll(pacientDao!!.getWithName(infSPN.selectedItem.toString())!!)
                    pacients1[0]!!.nf = res
                    pacientDao!!.update(pacients1[0]!!)
                }
            } else {
                Toast.makeText(context, "Введите значения!", Toast.LENGTH_LONG).show()
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