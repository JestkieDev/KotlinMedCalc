package com.example.fwafawf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.fwafawf.R
import com.example.fwafawf.db.MainDb
import com.example.fwafawf.db.MainDb.Companion.getINSTANCE
import com.example.fwafawf.db.Pacient
import com.example.fwafawf.db.PacientDao
import java.text.DecimalFormat

class PacientFragment : Fragment() {
    var mainDb: MainDb? = null
    var pacientDao: PacientDao? = null
    var pacients: List<Pacient?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pacient, container, false)
        val pnameTv = view.findViewById<TextView>(R.id.pnameTV)
        val pageTv = view.findViewById<TextView>(R.id.pageTV)
        val pimtTv = view.findViewById<TextView>(R.id.pimtTV)
        val pinfTv = view.findViewById<TextView>(R.id.pinfTV)
        val pcapTv = view.findViewById<TextView>(R.id.pcapTV)
        val pdefTv = view.findViewById<TextView>(R.id.pdefTV)
        val backaddBTN = view.findViewById<Button>(R.id.backpacBTN)
        val df = DecimalFormat("###.##")
        val nme: String
        mainDb = getINSTANCE(requireContext())
        pacientDao = mainDb!!.pacientDao()
        pacients = ArrayList()
        val bundle = this.arguments
        if (bundle != null) {
            nme = bundle.getString("nme", "egor")
            pacients = pacientDao!!.getWithName(nme)
        }
        pnameTv.text = "ФИО: " + pacients!![0]!!.name
        pageTv.text = "Возраст: " + pacients!![0]!!.age.toString() + " лет"
        val n = pacients!![0]!!.age.toString().length
        if (n == 1) {
            when (pacients!![0]!!.age.toString()[n - 1]) {
                '1' -> pageTv.text = pacients!![0]!!.age.toString() + " год"
                '2', '3', '4' -> pageTv.text = pacients!![0]!!.age.toString() + " года"
                '5', '6', '7', '8', '9' -> pageTv.text = pacients!![0]!!.age.toString() + " лет"
            }
        } else if (pacients!![0]!!.age < 21) {
            pageTv.text = pacients!![0]!!.age.toString() + " лет"
        } else {
            when (pacients!![0]!!.age.toString()[n - 1]) {
                '1' -> pageTv.text = pacients!![0]!!.age.toString() + " год"
                '2', '3', '4' -> pageTv.text = pacients!![0]!!.age.toString() + " года"
                '5', '6', '7', '8', '9', '0' -> pageTv.text =
                    pacients!![0]!!.age.toString() + " лет"
            }
        }
        pimtTv.text = "ИМТ: " + df.format(pacients!![0]!!.imt)
        pinfTv.text = "Скорость инфузии препарата: " + df.format(pacients!![0]!!.nf) + " мл/ч"
        pcapTv.text =
            "Скорость внутривенного капельного введения препарата: " + df.format(pacients!![0]!!.cap) + " капель в мин"
        pdefTv.text = "Дефицит: " + df.format(pacients!![0]!!.def) + " ммоль(мл)"
        if (pacients!![0]!!.def > 0) {
            pdefTv.text = "Дефицит: " + df.format(pacients!![0]!!.def) + " ммоль(мл)"
        } else if (pacients!![0]!!.def == 0.0) {
            pdefTv.text = "Дефицит: " + "Нормокалиемия"
        } else if (pacients!![0]!!.def < 0) {
            pdefTv.text = "Дефицит: " + "Гиперкалиемия"
        }
        backaddBTN.setOnClickListener {
            val navController = findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_dashboard)
        }
        return view
    }
}