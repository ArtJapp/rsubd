package ru.chronicker.rsubd.ui.screens.main.fragments.views.doctor_view

import android.view.View
import kotlinx.android.synthetic.main.item_doctor_view.view.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.BaseViewHolder
import ru.chronicker.rsubd.ui.base.DoctorViewItemModel
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter

class DoctorViewAdapter(
    private val onClickListener: (Int) -> Unit,
    private val onDeleteListener: (Int) -> Unit
) : PrimitiveAdapter<DoctorViewItemModel>(onClickListener, onDeleteListener) {

    override val componentLayoutId: Int = R.layout.item_doctor_view

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<DoctorViewItemModel> {
        return DoctorViewHolder(itemView)
    }

    inner class DoctorViewHolder(itemView: View) : BaseViewHolder<DoctorViewItemModel>(itemView) {
        override fun bind(data: DoctorViewItemModel) {
            itemView.name_tv.text = "${data.firstName} ${data.secondName} ${data.lastName}"
            itemView.qualification_tv.text = data.qualification
            itemView.specialization_tv.text = data.specialization
        }
    }
}