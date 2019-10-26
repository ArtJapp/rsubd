package ru.chronicker.rsubd.ui.screens.main.fragments.patient

import android.view.View
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.BaseAdapter
import ru.chronicker.rsubd.ui.base.BaseViewHolder
import ru.chronicker.rsubd.ui.base.DoubleBindableViewHolder
import ru.chronicker.rsubd.ui.base.DoubleItemModel

class PatientAdapter(
    private val onClickListener: (Int) -> Unit
) : BaseAdapter<DoubleItemModel>() {

    override val componentLayoutId: Int = R.layout.item_double

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<DoubleItemModel> {
        return PatientHolder(itemView).apply {
            this.setOnClickListener(onClickListener)
        }
    }

    inner class PatientHolder(itemView: View) : DoubleBindableViewHolder(itemView)
}