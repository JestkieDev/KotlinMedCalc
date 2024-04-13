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
import java.text.DecimalFormat

class DefFragment : Fragment() {
    var mainDb: MainDb? = null
    var pacientDao: PacientDao? = null
    var pacients: List<Pacient?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        val view = inflater.inflate(R.layout.fragment_def, container, false)
        val calET = view.findViewById<EditText>(R.id.calET)
        val mdET = view.findViewById<EditText>(R.id.mdET)
        val okdefBTN = view.findViewById<Button>(R.id.okdefBTN)
        val resdefTV = view.findViewById<TextView>(R.id.resdefTV)
        val backBTN = view.findViewById<Button>(R.id.backdefBTN)
        val defSPN = view.findViewById<Spinner>(R.id.defSPN)
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
        defSPN.adapter = arrayAdapter
        okdefBTN.setOnClickListener {
            if ((calET.text.toString().isEmpty() == false) and (mdET.text.toString()
                    .isEmpty() == false) and (pacientDao!!.all!!.size > 0)
            ) {
                val cal = calET.text.toString().toDouble()
                val md = mdET.text.toString().toInt()
                val res = (5 - cal) * 0.2 * md
                val df = DecimalFormat("###.#")
                if (res > 0) {
                    resdefTV.text = df.format(res) + " ммоль(мл)"
                } else if (res == 0.0) {
                    resdefTV.text = "Нормокалиемия"
                } else if (res < 0) {
                    resdefTV.text = "Гиперкалиемия"
                }
                val pacients1: MutableList<Pacient?> = ArrayList()
                pacients1.addAll(pacientDao!!.getWithName(defSPN.selectedItem.toString())!!)
                pacients1[0]!!.def = res
                pacientDao!!.update(pacients1[0]!!)
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