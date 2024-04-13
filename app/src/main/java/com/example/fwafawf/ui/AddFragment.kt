package com.example.fwafawf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.fwafawf.R
import com.example.fwafawf.db.MainDb.Companion.getINSTANCE
import com.example.fwafawf.db.Pacient
import com.example.fwafawf.ui.dashboard.DashboardFragment


class AddFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val backBTN = view.findViewById<Button>(R.id.backaddBTN)
        val addBTN = view.findViewById<Button>(R.id.addBTN)
        val nameET = view.findViewById<EditText>(R.id.nameET)
        val ageET = view.findViewById<EditText>(R.id.ageET)
        val mainDb = getINSTANCE(requireContext())
        val pacientDao = mainDb!!.pacientDao()
        addBTN.setOnClickListener {
            val pacient = Pacient(nameET.text.toString(), ageET.text.toString().toInt())
            pacient.cap = 0.0
            pacient.def = 0.0
            pacient.nf = 0.0
            pacient.imt = 0.0
            pacientDao!!.insert(pacient)
        }
        backBTN.setOnClickListener {
            val dashboardFragment = DashboardFragment()
            val navController = findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_dashboard)
        }
        return view
    }
}