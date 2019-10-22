package ru.chronicker.rsubd.ui.screens.form.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_field_text.view.*
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.ui.base.*
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.Value


private const val SELECTABLE_FIELD = 1
private const val TEXT_FIELD = 2

class FormFieldsAdapter(
    private val fields: MutableList<Field> = mutableListOf(),
    private val values: MutableList<Any?> = mutableListOf()
) : RecyclerView.Adapter<FormFieldsAdapter.BaseFieldHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFieldHolder {
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

    override fun onBindViewHolder(holder: BaseFieldHolder, position: Int) {
         holder.bind(fields[position],values[position] ?: EMPTY_STRING)
    }

    override fun getItemViewType(position: Int): Int {
        return when(fields[position]) {
            is ForeignKeyField -> SELECTABLE_FIELD
            else -> TEXT_FIELD
        }
    }

    fun setData(fields: List<Field>, data: List<Any?>) {
        this.fields.clear()
        this.fields.addAll(fields)
        this.values.clear()
        this.values.addAll(data)
        notifyDataSetChanged()
    }

    abstract class BaseFieldHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: Field, value: Any)
    }

    inner class TextFieldHolder(itemView: View) : BaseFieldHolder(itemView) {
        private lateinit var field: Field

        override fun bind(data: Field, value: Any) {
            field = data
            with (itemView) {
                text_field_til.visibility = View.VISIBLE
                text_field_til.hint = field.title
                field_et.setText(value.toString())
            }
        }
    }

    inner class SelectableFieldHolder(itemView: View): BaseFieldHolder(itemView) {
        private lateinit var field: Field

        override fun bind(data: Field, value: Any) {
            field = data
            with(itemView) {
                this.spinner_field_til.visibility = View.VISIBLE
                val adapter = ArrayAdapter(
                    itemView.context,
                    android.R.layout.simple_spinner_item,
                    value as List<String>
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_field_til.adapter = adapter
                spinner_field_til.prompt = field.title
            }
        }
    }

    inner class OtherFieldHolder(itemView: View) : BaseFieldHolder(itemView) {
        private lateinit var field: Field

        override fun bind(data: Field, value: Any) {
            field = data
            with (itemView) {
                text_field_til.visibility = View.VISIBLE
                text_field_til.hint = field.title
            }
        }
    }
}