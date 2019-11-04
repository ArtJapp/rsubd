package ru.chronicker.rsubd.ui.screens.main.fragments.primitive

import android.view.View
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.*

abstract class PrimitiveAdapter<IM: ItemModel>(
    private val onClickListener: (Int) -> Unit
) : BaseAdapter<IM>()

class PrimitiveAloneAdapter(
    private val onClickListener: (Int) -> Unit
) : PrimitiveAdapter<AloneItemModel>(onClickListener) {

    override val componentLayoutId: Int = R.layout.item_alone

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<AloneItemModel> {
        return AloneHolder(itemView).apply {
            this.setOnClickListener(onClickListener)
        }
    }

    inner class AloneHolder(itemView: View) : BindableViewHolder(itemView)
}

class PrimitiveDoubleAdapter(
    private val onClickListener: (Int) -> Unit
) : PrimitiveAdapter<DoubleItemModel>(onClickListener) {

    override val componentLayoutId: Int = R.layout.item_double

    override fun createHolder(itemView: View, viewType: Int): BaseViewHolder<DoubleItemModel> {
        return DoubleHolder(itemView).apply {
            this.setOnClickListener(onClickListener)
        }
    }

    inner class DoubleHolder(itemView: View) : DoubleBindableViewHolder(itemView)
}