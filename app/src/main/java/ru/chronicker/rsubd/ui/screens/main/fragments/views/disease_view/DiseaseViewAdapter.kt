package ru.chronicker.rsubd.ui.screens.main.fragments.views.disease_view

import android.view.View
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.databinding.ItemDiseaseViewBinding
import ru.chronicker.rsubd.ui.base.BaseViewHolder
import ru.chronicker.rsubd.ui.base.DiseaseViewItemModel
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter
import kotlin.math.roundToInt

class DiseaseViewAdapter(
    private val onClickListener: (Int) -> Unit,
    private val onDeleteListener: (Int) -> Unit
) : PrimitiveAdapter<DiseaseViewItemModel>(onClickListener, onDeleteListener) {

    override val componentLayoutId: Int = R.layout.item_disease_view

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<DiseaseViewItemModel> {
        return DiseaseViewHolder(itemView)
    }

    inner class DiseaseViewHolder(itemView: View) : BaseViewHolder<DiseaseViewItemModel>(itemView) {
        private val binding = ItemDiseaseViewBinding.bind(itemView)
        override fun bind(data: DiseaseViewItemModel) {
            binding.diseaseTv.text = data.disease
            binding.treatmentTv.text = "Лечение: ${data.treatment}"
            data.efficiency
                .roundToInt()
                .let { efficiency ->
                    binding.efficiencyTv.text = "Эффективность лечения: $efficiency%"
                }
        }
    }
}