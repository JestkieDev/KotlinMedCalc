package com.example.fwafawf.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.fwafawf.R


class HomeFragment : Fragment() {
    var imtBTN: Button? = null
    var infBTN: Button? = null
    var defBTN: Button? = null
    var capBTN: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        imtBTN = view.findViewById(R.id.imtBTN)
        infBTN = view.findViewById(R.id.infBTN)
        defBTN = view.findViewById(R.id.calBTN)
        capBTN = view.findViewById(R.id.capBTN)
        imtBTN?.setOnClickListener(View.OnClickListener {
            val navController =
                findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_imt)
        })
        infBTN?.setOnClickListener(View.OnClickListener {
            val navController =
                findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_inf)
        })
        defBTN?.setOnClickListener(View.OnClickListener {
            val navController =
                findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_def)
        })
        capBTN?.setOnClickListener(View.OnClickListener {
            val navController =
                findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_cap)
        })
        return view
    }
}