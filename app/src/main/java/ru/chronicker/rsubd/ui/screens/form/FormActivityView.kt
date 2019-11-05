package ru.chronicker.rsubd.ui.screens.form

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.helper.dropDown
import com.thejuki.kformmaster.helper.form
import com.thejuki.kformmaster.helper.text
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.Constants.MODE
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_LONG
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.*
import ru.chronicker.rsubd.ui.base.ToolbarConfig

class FormActivityView : AppCompatActivity() {

    private lateinit var formBuilder: FormBuildHelper
    private lateinit var dbHelper: DBHelper

    private var screenMode = FormMode.CREATE
    private lateinit var currentModel: Entity
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        dbHelper = DBHelper(this)
        initData()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_menu, menu)
        return true
    }

    private fun initData() {
        intent.getIntExtra(ID, 0)
            .let { id = it }
        intent.getSerializableExtra(ENTITY)
            .let { it as Entity }
            .also { currentModel = it }
        intent.getSerializableExtra(MODE)
            .let { it as FormMode }
            .let { screenMode = it }
        if (screenMode == FormMode.UPDATE) {
            loadData()
        } else {
            setEntityData(currentModel, getEmptyValues(currentModel))
        }
    }

    private fun initViews() {
        initToolbar()
        initContainer()
    }

    private fun initToolbar() {
        ToolbarConfig.builder(this)
            .setToolbarBackgroundColor(ContextCompat.getColor(this, R.color.bright_blue_10))
            .setDisplayHomeAsUpEnabled(true)
            .enableBackAction(true)
            .setHomeAsUpIndicatorId(R.drawable.ic_back)
            .setHomeAsUpIndicatorColor(R.color.primaryTextColor)
            .setTitleTextColor(R.color.primaryTextColor)
            .setTitleText(currentModel.name)
            .setOnMenuItemClickListener {
                if (it.itemId == R.id.action_save) {
                    save()
                }
            }
            .apply()
    }

    private fun initContainer() {
//        fields_rv.adapter = adapter
    }

    private fun loadData() {
        val values = mutableMapOf<String, Any>()
        dbHelper.select(currentModel, mapOf(ID to "$id"))
            .map {
                it.fields.map { (field, value) ->
                    values[field.name] = value
                }
            }
        setEntityData(currentModel, values)
    }

    private fun setEntityData(entity: Entity, values: Map<String, Any>) {
        setFields(entity.fields, sortValuesAsFields(values, entity.fields))
    }

    private fun getEmptyValues(entity: Entity): Map<String, Any> {
        return entity.values.mapValues { (key, value) -> (value as Value).value }
    }

    private fun sortValuesAsFields(values: Map<String, Any>, fields: List<Field>): List<Any?> {
        val result = mutableListOf<Any?>()
        fields.forEach {
            result.add(values[it.name])
        }
        return result
    }

    private fun setFields(fields: List<Field>, values: List<Any?>) {
        formBuilder = form(this, fields_rv) {
            fields.forEachIndexed { index, field ->
                if (field is ForeignKeyField) {
                    dropDown<String> {
                        title = field.title
                        dialogTitle = field.title
                        options = getForeignItems(field.foreignTable, field.foreignKey)
                        value = values[index].toString()
                        backgroundColor = getBackgroundColor()
                        titleTextColor = getHintColor()
                        valueTextColor = getFontColor()
                    }
                } else {
                    text(1) {
                        value = values[index].toString()
                        title = field.title
                        backgroundColor = getBackgroundColor()
                        titleTextColor = getHintColor()
                        valueTextColor = getFontColor()
                    }
                }
            }
        }
    }

    private fun getForeignItems(table: String, key: String): List<String> {
        return dbHelper.select(table)
            .map { it.fields }
            .map { values ->
                values.let {
                    dbHelper.getEntityByName(table)
                        ?.convertToString(values)
                        ?: EMPTY_STRING
                }
            }
    }

    private fun save() {
        getValues().let {
            when (screenMode) {
                FormMode.CREATE -> {
                    println("AAA create $it")
                    doInsert(it)
                }
                FormMode.UPDATE -> {
                    println("AAA update $it")
                    doUpdate(it)
                }
            }
        }
    }

    private fun doInsert(values: List<Value>) {
        dbHelper.insert(
            entity = currentModel,
            values = values,
            onSuccess = ::savingSucceed,
            onError = ::onError
        )
    }

    private fun doUpdate(values: List<Value>) {
        dbHelper.update(
            entity = currentModel,
            values = values,
            onSuccess = ::savingSucceed,
            onError = ::onError
        )
    }

    private fun savingSucceed() {
        this.finish()
    }

    private fun onError(message: String) {
        Snackbar.make(form_container, message, Snackbar.LENGTH_LONG)
    }

    private fun getValues(): List<Value> {
        val values: MutableList<Value> = ArrayList()
        currentModel.fields.forEachIndexed { index, field ->
            when (field) {
                is ForeignKeyField -> {
                    formBuilder.getElementAtIndex(index)
                        .value
                        .toString()
                        .let { convertSelectedToForeignKeyId(it, field.foreignTable) }
                        .let { Value(it, FieldType.INTEGER) }
                }
                is IntField -> {
                    formBuilder.getElementAtIndex(index)
                        .value
                        .toString()
                        .toLong()
                        .let { Value(it, FieldType.INTEGER) }
                }
                else -> {
                    formBuilder.getElementAtIndex(index)
                        .value
                        .toString()
                        .let { Value(it, FieldType.TEXT) }
                }
            }.let { values.add(it) }
        }
        return values
    }

    private fun convertSelectedToForeignKeyId(choice: String, entityName: String): Long {
        return dbHelper.getEntityByName(entityName)
            ?.let { entity ->
                dbHelper.select(entityName)
                    .asSequence()
                    .map { it.fields }
                    .map { it to entity.convertToString(it) }
                    .filter { it.second == choice }
                    .map { it.first }
                    .firstOrNull()
                    ?.let {  values ->
                        values.find { it.first.name == ID }?.second as Long
                    }
            } ?: EMPTY_LONG
    }

    private fun getBackgroundColor(): Int {
        return ContextCompat.getColor(this, R.color.colorSurface)
    }

    private fun getFontColor(): Int {
        return ContextCompat.getColor(this, R.color.primaryTextColor)
    }

    private fun getHintColor(): Int {
        return ContextCompat.getColor(this, R.color.secondaryTextColor)
    }
}

data class ForeignSelectableField(
    val id: Int,
    val title: String
)