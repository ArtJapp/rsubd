package ru.chronicker.rsubd.ui.screens.form

import android.app.LauncherActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.dropDown
import com.thejuki.kformmaster.helper.form
import com.thejuki.kformmaster.helper.text
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.base.Entity
import android.app.LauncherActivity.ListItem
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.ui.screens.form.adapters.FormFieldsAdapter

class FormActivityView : AppCompatActivity() {

//    private val adapter = FormFieldsAdapter()
    private lateinit var formBuilder: FormBuildHelper
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setSupportActionBar(toolbar)
        dbHelper = DBHelper(this)
        initViews()

        intent.getSerializableExtra(ENTITY)
            .let { it as Entity }
            .let { setEntityData(it) }
    }

    private fun initViews() {
        initContainer()
    }

    private fun initContainer() {
//        fields_rv.adapter = adapter
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
//        adapter.setData(fields, values)
        formBuilder = form(this, fields_rv) {
            fields.forEachIndexed { index, field ->
                if (field is ForeignKeyField) {
                    dropDown<String> {
                        title = field.title
                        dialogTitle = field.title
                        options = getForeignItems(field.foreignTable, field.foreignKey)
                        value = EMPTY_STRING
                    }
                }
                text(1) {
                    value = values[index].toString()
                    title = field.title

            }
        }

//            when (it) {
//
//            }
//            builder.createEditText()
        }
    }

    private fun getForeignItems(table: String, key: String): List<String> {
        return dbHelper.select(table)
            .flatMap { it.fields }
            .map { it.second.toString() }

    }
}

data class ForeignSelectableField(
    val id: Int,
    val title: String
)