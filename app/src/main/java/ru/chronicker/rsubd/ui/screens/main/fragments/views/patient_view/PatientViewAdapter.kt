package ru.chronicker.rsubd.ui.screens.main.fragments.views.patient_view

import android.view.View
import kotlinx.android.synthetic.main.item_patient_view.view.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.BaseViewHolder
import ru.chronicker.rsubd.ui.base.PatientViewItemModel
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter

class PatientViewAdapter(
    private val onClickListener: (Int) -> Unit,
    private val onDeleteListener: (Int) -> Unit
) : PrimitiveAdapter<PatientViewItemModel>(onClickListener, onDeleteListener) {

    override val componentLayoutId: Int = R.layout.item_patient_view

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<PatientViewItemModel> {
        return PatientViewHolder(itemView)
    }

    inner class PatientViewHolder(itemView: View) : BaseViewHolder<PatientViewItemModel>(itemView) {

        override fun bind(data: PatientViewItemModel) {
            itemView.name_tv.text = "${data.firstName} ${data.secondName} ${data.lastName}"
            itemView.birthday_tv.text = "Дата рождения: ${data.birthDate}"
            itemView.social_status_tv.text = "Социальный статус: ${data.socialStatus}"
            itemView.state_tv.text = "Текущее состояние: ${data.state}"
            itemView.last_disease_tv.text =  if (data.state == "Лечится") {
                "Текущая болезнь: ${data.disease}"
            } else {
                "Последняя болезнь: ${data.disease}"
            }
        }
    }
}