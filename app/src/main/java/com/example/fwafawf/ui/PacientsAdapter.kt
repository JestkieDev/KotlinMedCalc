package com.example.fwafawf.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fwafawf.R
import com.example.fwafawf.db.Pacient


class PacientsAdapter(var context: FragmentActivity, var pacients: ArrayList<Pacient?>) :
    RecyclerView.Adapter<PacientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rc_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pacient = pacients[position]
        holder.pacientnameTV.text = pacient?.name
        val n = pacient?.age.toString().length
        if (n == 1) {
            when (pacient?.age.toString()[n - 1]) {
                '1' -> holder.pacientageTV.text = pacient?.age.toString() + " год"
                '2', '3', '4' -> holder.pacientageTV.text = pacient?.age.toString() + " года"
                '5', '6', '7', '8', '9' -> holder.pacientageTV.text =
                    pacient?.age.toString() + " лет"
            }
        } else if (pacient?.age!! < 21) {
            holder.pacientageTV.text = pacient?.age.toString() + " лет"
        } else {
            when (pacient?.age.toString()[n - 1]) {
                '1' -> holder.pacientageTV.text = pacient?.age.toString() + " год"
                '2', '3', '4' -> holder.pacientageTV.text = pacient?.age.toString() + " года"
                '5', '6', '7', '8', '9', '0' -> holder.pacientageTV.text =
                    pacient.age.toString() + " лет"
            }
        }
        val name = pacient?.name
        holder.itemView.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            val pacientFragment = PacientFragment()
            bundle.putString("nme", name)
            pacientFragment.arguments = bundle
            val navController = findNavController(context, R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_pacient, bundle)
        })
    }

    override fun getItemCount(): Int {
        return pacients.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pacientnameTV: TextView
        var pacientageTV: TextView

        init {
            pacientnameTV = itemView.findViewById(R.id.pacientname)
            pacientageTV = itemView.findViewById(R.id.pacientage)
        }
    }
}