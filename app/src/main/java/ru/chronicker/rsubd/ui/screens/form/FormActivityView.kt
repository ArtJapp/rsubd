package ru.chronicker.rsubd.ui.screens.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.ui.screens.form.adapters.FormFieldsAdapter

class FormActivityView : AppCompatActivity() {

    private val adapter = FormFieldsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setSupportActionBar(toolbar)
        initViews()

        intent.getSerializableExtra(ENTITY)
            .let { it as Entity }
            .let { setEntityData(it) }
    }

    private fun initViews() {
        initContainer()
    }

    private fun initContainer() {
        fields_rv.adapter = adapter
    }

    private fun setEntityData(entity: Entity) {
        setFields(entity.fields, sortValuesAsFields(entity.values, entity.fields))
    }

    private fun sortValuesAsFields(values: Map<String, Any>, fields: List<Field>): List<Any?> {
        val result = mutableListOf<Any?>()
        fields.forEach {
            result.add(values[it.name])
        }
        return result
    }

    private fun setFields(fields: List<Field>, values: List<Any?>) {
        adapter.setData(fields, values)
    }
}
