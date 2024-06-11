package ru.chronicker.rsubd.ui.screens.main.fragments.views.patient_view

import android.view.View
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.databinding.ItemPatientViewBinding
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
        private val binding = ItemPatientViewBinding.bind(itemView)
        override fun bind(data: PatientViewItemModel) {
            binding.nameTv.text = "${data.firstName} ${data.secondName} ${data.lastName}"
            binding.birthdayTv.text = "Дата рождения: ${data.birthDate}"
            binding.socialStatusTv.text = "Социальный статус: ${data.socialStatus}"
            binding.stateTv.text = "Текущее состояние: ${data.state}"
            binding.lastDiseaseTv.text =  if (data.state == "Лечится") {
                "Текущая болезнь: ${data.disease}"
            } else {
                "Последняя болезнь: ${data.disease}"
            }
        }
    }
}