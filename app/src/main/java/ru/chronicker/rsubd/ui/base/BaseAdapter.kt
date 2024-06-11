package ru.chronicker.rsubd.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.databinding.ItemAloneBinding
import ru.chronicker.rsubd.databinding.ItemDoubleBinding

abstract class BaseAdapter<T : ItemModel>(
    private val onDeleteAction: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract val componentLayoutId: Int

    private val values: MutableList<T> = mutableListOf()

    abstract fun createHolder(itemView: View, viewType: Int = 0): BaseViewHolder<T>

    fun setItems(values: List<T>) {
        this.values.clear()
        this.values.addAll(values)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return createHolder(
            LayoutInflater.from(parent.context).inflate(componentLayoutId, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(values[position])
    }

    fun deleteItem(position: Int) {
        onDeleteAction?.invoke(values[position].id)
        values.removeAt(position)
        notifyDataSetChanged()
    }
}

abstract class BaseViewHolder<T : ItemModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

abstract class BindableViewHolder(itemView: View) : BaseViewHolder<AloneItemModel>(itemView) {

    private val binding = ItemAloneBinding.bind(itemView)

    override fun bind(data: AloneItemModel) {
        id = data.id
        binding.titleTv.text = data.title
    }
}

abstract class DoubleBindableViewHolder(itemView: View) :
    BaseViewHolder<DoubleItemModel>(itemView) {

    private val binding = ItemDoubleBinding.bind(itemView)

    override fun bind(data: DoubleItemModel) {
        id = data.id
        binding.titleTv.text = data.title
        binding.subtitleTv.text = data.subtitle
    }
}