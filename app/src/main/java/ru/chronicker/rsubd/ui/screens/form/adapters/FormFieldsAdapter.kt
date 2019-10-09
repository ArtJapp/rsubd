package ru.chronicker.rsubd.ui.screens.form.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_field_text.view.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType

class FormFieldsAdapter(
    private val fields: MutableList<Field> = mutableListOf(),
    private val values: MutableList<Any?> = mutableListOf()
) : RecyclerView.Adapter<FormFieldsAdapter.TextFieldHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextFieldHolder {
        return TextFieldHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_field_text,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int = fields.size

    override fun onBindViewHolder(holder: TextFieldHolder, position: Int) {
        when (fields[position].type) {
            FieldType.TEXT -> holder.bindAsText(fields[position], values[position])
            // TODO: добавить остальные типы
            else -> holder.bind(fields[position])
        }
    }

    fun setData(fields: List<Field>, data: List<Any?>) {
        this.fields.clear()
        this.fields.addAll(fields)
        this.values.clear()
        this.values.addAll(data)
        notifyDataSetChanged()
    }

    inner class TextFieldHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO: выпилить после создания холдеров для остальных типов данных
        fun bind(field: Field) {
            itemView.field_til.hint = field.name
        }

        fun bindAsText(field: Field, data: Any?) {
            itemView.field_et.setText(data as String)
            itemView.field_til.hint = field.name
        }
    }
}