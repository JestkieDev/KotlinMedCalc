package com.example.fwafawf.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fwafawf.R
import com.example.fwafawf.db.MainDb
import com.example.fwafawf.db.Pacient
import com.example.fwafawf.db.PacientDao
import com.example.fwafawf.ui.PacientsAdapter


class DashboardFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var pacients: ArrayList<Pacient?>? = null
    var pacientsAdapter: PacientsAdapter? = null
    var mainDb: MainDb? = null
    var pacientDao: PacientDao? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val addBTN = view.findViewById<Button>(R.id.addBTN)
        recyclerView = view.findViewById(R.id.pacientsRV)
        mainDb = MainDb.getINSTANCE(requireContext())
        pacientDao = mainDb?.pacientDao()
        if (pacientDao?.all != null) {
            pacients = ArrayList(pacientDao?.all)!!
        }
        pacientsAdapter = PacientsAdapter(requireActivity(), pacients!!)
        pacientsAdapter!!.notifyDataSetChanged()
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setAdapter(pacientsAdapter)
        addBTN.setOnClickListener {
            val navController =
                findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_addpacients)
        }
        return view
    }
}