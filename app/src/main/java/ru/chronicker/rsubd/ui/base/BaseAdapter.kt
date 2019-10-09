package ru.chronicker.rsubd.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_double.view.*
import ru.chronicker.rsubd.EMPTY_INT

abstract class BaseAdapter<T: ItemModel> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract val componentLayoutId: Int

    private val values: MutableList<T> = mutableListOf()

    abstract fun createHolder(itemView: View): BaseViewHolder<T>

    fun setItems(values: List<T>) {
        this.values.clear()
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return createHolder(LayoutInflater.from(parent.context).inflate(componentLayoutId, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(values[position])
    }
}

abstract class BaseViewHolder<T: ItemModel>(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var onClickListener: ((Int) -> Unit)? = null
    protected var id: Int = EMPTY_INT

    init {
        itemView.setOnClickListener { onClickListener?.invoke(id) }
    }

    abstract fun bind(data: T)

    fun setOnClickListener(onClickListener: (Int) -> Unit) {
        this.onClickListener = onClickListener
    }
}

abstract class BindableViewHolder(itemView: View): BaseViewHolder<AloneItemModel>(itemView) {

    override fun bind(data: AloneItemModel) {
        id = data.id
        itemView.title_tv.text = data.title
    }
}

abstract class DoubleBindableViewHolder(itemView: View): BaseViewHolder<DoubleItemModel>(itemView) {

    override fun bind(data: DoubleItemModel) {
        id = data.id
        itemView.title_tv.text = data.title
        itemView.subtitle_tv.text = data.subtitle
    }
}