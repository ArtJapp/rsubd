package ru.chronicker.rsubd.ui.screens.patient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_double.view.*
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.R

class PatientAdapter(
    private val items: MutableList<PatientModel> = mutableListOf(),
    private val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PatientAdapter.PatientHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        return PatientHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_double, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        holder.inflate(items[position])
    }

    fun setItems(items: List<PatientModel>) {
        this.items.clear()
        this.items.addAll(items)
    }

    inner class PatientHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var id: Int = EMPTY_INT

        init {
            itemView.setOnClickListener { onClickListener.invoke(id) }
        }

        fun inflate(patient: PatientModel) {
            itemView.title_tv.text = patient.title
            itemView.subtitle_tv.text = patient.subtitle
            id = patient.id
        }
    }
}