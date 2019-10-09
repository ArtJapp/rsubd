package ru.chronicker.rsubd.ui.screens.main.fragments.diseases

import android.view.View
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.AloneItemModel
import ru.chronicker.rsubd.ui.base.BaseAdapter
import ru.chronicker.rsubd.ui.base.BaseViewHolder
import ru.chronicker.rsubd.ui.base.BindableViewHolder

class DiseaseAdapter(
    private val onClickListener: (Int) -> Unit
): BaseAdapter<AloneItemModel>() {

    override val componentLayoutId: Int = R.layout.item_alone

    override fun createHolder(itemView: View): BaseViewHolder<AloneItemModel> {
        return DiseaseViewHolder(itemView).apply {
            this.setOnClickListener(onClickListener)
        }
    }

    inner class DiseaseViewHolder(itemView: View): BindableViewHolder(itemView)
}