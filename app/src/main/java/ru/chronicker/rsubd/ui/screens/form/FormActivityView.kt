package ru.chronicker.rsubd.ui.screens.form

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
//import com.thejuki.kformmaster.helper.*
//import com.thejuki.kformmaster.model.FormPickerDateElement
//import com.thejuki.kformmaster.view.FormSingleLineLockableEditTextViewBinder
//import com.thejuki.kformmaster.view.lockableField
//import kotlinx.android.synthetic.main.activity_form.*
//import kotlinx.android.synthetic.main.content_form.*
import ru.chronicker.rsubd.Constants.DATE_PATTERN
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.Constants.MODE
import ru.chronicker.rsubd.EMPTY_LONG
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.*
import ru.chronicker.rsubd.ui.base.ToolbarConfig
import ru.chronicker.rsubd.ui.screens.main.fragments.treatment.toDate
import ru.chronicker.rsubd.utils.setStatusBarColor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val UNEXPECTED_ERROR = "UNEXPECTED_ERROR"

class FormActivityView : AppCompatActivity() {

//    private lateinit var formBuilder: FormBuildHelper
//    private lateinit var dbHelper: DBHelper
//
//    private var screenMode = FormMode.CREATE
//    private lateinit var currentModel: Entity
//    private var id: Long = 0L
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_form)
//        dbHelper = DBHelper(this)
//        initData()
//        initToolbar()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.form_menu, menu)
//        return true
//    }
//
//    private fun initData() {
//        intent.getIntExtra(ID, 0).toLong()
//            .let { id = it }
//        intent.getSerializableExtra(ENTITY)
//            .let { it as Entity }
//            .also { currentModel = it }
//        intent.getSerializableExtra(MODE)
//            .let { it as FormMode }
//            .let { screenMode = it }
//        if (screenMode == FormMode.UPDATE) {
//            loadData()
//        } else {
//            updateId(currentModel)
//            setEntityData(currentModel, getEmptyValues(currentModel))
//        }
//    }
//
//    private fun initToolbar() {
//        ToolbarConfig.builder(this)
//            .setToolbarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//            .setDisplayHomeAsUpEnabled(true)
//            .enableBackAction(true)
//            .setHomeAsUpIndicatorId(R.drawable.ic_back)
//            .setHomeAsUpIndicatorColor(R.color.primaryTextColor)
//            .setTitleTextColor(R.color.primaryTextColor)
//            .setTitleText(currentModel.name)
//            .setOnMenuItemClickListener {
//                if (it.itemId == R.id.action_save) {
//                    save()
//                }
//            }
//            .apply()
//        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
//    }
//
//    private fun loadData() {
//        val values = mutableMapOf<String, Any>()
//        dbHelper.select(currentModel, mapOf(ID to "$id"))
//            .map {
//                it.fields.map { (field, value) ->
//                    values[field.name] = value
//                }
//            }
//        setEntityData(currentModel, values)
//    }
//
//    private fun setEntityData(entity: Entity, values: Map<String, Any>) {
//        setFields(entity.fields, sortValuesAsFields(values, entity.fields))
//    }
//
//    private fun updateId(model: Entity) {
//        dbHelper.getMaxId(model)
//            .let {
//                id = it + 1
//            }
//    }
//
//    private fun getEmptyValues(entity: Entity): Map<String, Any> {
//        return entity.values.mapValues { (key, value) -> (value as Value).value }
//    }
//
//    private fun sortValuesAsFields(values: Map<String, Any>, fields: List<Field>): List<Any?> {
//        val result = mutableListOf<Any?>()
//        fields.forEach {
//            result.add(values[it.name])
//        }
//        return result
//    }
//
//    private fun setFields(fields: List<Field>, values: List<Any?>) {
//        formBuilder = form(this, fields_rv) {
//            fields.forEachIndexed { index, field ->
//                when (field) {
//                    is ForeignKeyField -> {
//                        dropDown<String> {
//                            title = field.title
//                            dialogTitle = field.title
//                            options = getForeignItems(field.foreignTable, field.foreignKey)
//                            value =
//                                getInitialForeignKey(values[index].toString(), field.foreignTable)
//                            backgroundColor = getBackgroundColor()
//                            titleTextColor = getHintColor()
//                            valueTextColor = getFontColor()
//                        }
//                    }
//                    is BooleanField -> {
//                        checkBox<String> {
//                            title = field.title
//                            checkedValue = "1"
//                            unCheckedValue = "0"
//                            value = values[index].toString()
//                            backgroundColor = getBackgroundColor()
//                            titleTextColor = getHintColor()
//                            valueTextColor = getFontColor()
//                        }
//                    }
//                    is DateField -> {
//                        date {
//                            title = field.title
//                            value = FormPickerDateElement.DateHolder(
//                                Date(values[index].toString().toLong()),
//                                dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.US)
//                            )
//                            backgroundColor = getBackgroundColor()
//                            titleTextColor = getHintColor()
//                            valueTextColor = getFontColor()
//                        }
//                    }
//                    is IntField -> {
//                        lockableField(1) {
//                            value =
//                                getIntValue(field, values[index].toString().toLong()).toString()
//                            title = field.title
//                            backgroundColor = getBackgroundColor()
//                            titleTextColor = getHintColor()
//                            valueTextColor = getFontColor()
//                            enabled = checkFieldEnabled(field)
//                        }
//                    }
//                    else -> {
//                        text(1) {
//                            value = values[index].toString()
//                            title = field.title
//                            backgroundColor = getBackgroundColor()
//                            titleTextColor = getHintColor()
//                            valueTextColor = getFontColor()
//                        }
//                    }
//                }
//            }
//        }
//        formBuilder.registerCustomViewBinder(
//            FormSingleLineLockableEditTextViewBinder(
//                context = this,
//                formBuilder = formBuilder,
//                layoutID = formBuilder.formLayouts?.text
//            ).viewBinder
//        )
//    }
//
//    private fun getForeignItems(table: String, key: String): List<String> {
//        return dbHelper.select(table)
//            .map { it.fields }
//            .map { values ->
//                values.let {
//                    dbHelper.getEntityByName(table)
//                        ?.deepConvertToString(values, dbHelper)
//                        ?: EMPTY_STRING
//                }
//            }
//    }
//
//    private fun getInitialForeignKey(value: String, modelName: String): String {
//        return dbHelper.getEntityByName(modelName)
//            ?.let { model ->
//                dbHelper.select(model, mapOf(ID to value))
//                    .map { it.fields }
//                    .map { result ->
//                        model.deepConvertToString(result, dbHelper)
//                    }
//                    .firstOrNull()
//            }
//            ?: EMPTY_STRING
//    }
//
//    private fun getIntValue(field: Field, possibleValue: Long): Long {
//        return if (screenMode == FormMode.CREATE && field.name == ID) {
//            id
//        } else {
//            possibleValue
//        }
//    }
//
//    private fun checkFieldEnabled(field: Field): Boolean {
//        return field.name != ID
//    }
//
//    private fun save() {
//        try {
//            getValues().let {
//                when (screenMode) {
//                    FormMode.CREATE -> {
//                        doInsert(it)
//                    }
//                    FormMode.UPDATE -> {
//                        doUpdate(it)
//                    }
//                }
//            }
//        } catch (e: NotCompletedFieldsException) {
//            onError(e.message ?: UNEXPECTED_ERROR)
//        }
//    }
//
//    private fun doInsert(values: List<Value>) {
//        dbHelper.insert(
//            entity = currentModel,
//            values = values,
//            onSuccess = ::savingSucceed,
//            onError = ::onError
//        )
//    }
//
//    private fun doUpdate(values: List<Value>) {
//        dbHelper.update(
//            entity = currentModel,
//            values = values,
//            onSuccess = ::savingSucceed,
//            onError = ::onError
//        )
//    }
//
//    private fun savingSucceed() {
//        this.finish()
//    }
//
//    private fun onError(message: String) {
//        Snackbar.make(form_container, message, Snackbar.LENGTH_LONG).show()
//    }
//
//    private fun getValues(): List<Value> {
//        val values: MutableList<Value> = ArrayList()
//        currentModel.fields.forEachIndexed { index, field ->
//            when (field) {
//                is ForeignKeyField -> {
//                    formBuilder.getElementAtIndex(index)
//                        .value
//                        .toString()
//                        .trim()
//                        .takeIf { it.isNotBlank() || field.isRequired.not() }
//                        ?.let { convertSelectedToForeignKeyId(it, field.foreignTable) }
//                        ?.let { Value(it, FieldType.INTEGER) }
//                        ?: throw NotCompletedFieldsException()
//                }
//                is BooleanField -> {
//                    formBuilder.getElementAtIndex(index)
//                        .value
//                        .toString()
//                        .trim()
//                        .toLong()
//                        .let { Value(it, FieldType.INTEGER) }
//                }
//                is DateField -> {
//                    formBuilder.getElementAtIndex(index)
//                        .value
//                        .toString()
//                        .trim()
//                        .toDate()
//                        .time
//                        .let { Value(it, FieldType.INTEGER) }
//                }
//                is IntField -> {
//                    formBuilder.getElementAtIndex(index)
//                        .value
//                        .toString()
//                        .trim()
//                        .toLong()
//                        .let { Value(it, FieldType.INTEGER) }
//                }
//                else -> {
//                    formBuilder.getElementAtIndex(index)
//                        .value
//                        .toString()
//                        .trim()
//                        .takeIf { it.isNotBlank() || field.isRequired.not() }
//                        ?.let { Value(it, FieldType.TEXT) }
//                        ?: throw NotCompletedFieldsException()
//                }
//            }.let { values.add(it) }
//        }
//        return values
//    }
//
//    private fun convertSelectedToForeignKeyId(choice: String, entityName: String): Long {
//        return dbHelper.getEntityByName(entityName)
//            ?.let { entity ->
//                dbHelper.select(entityName)
//                    .asSequence()
//                    .map { it.fields }
//                    .map { it to entity.deepConvertToString(it, dbHelper) }
//                    .filter { it.second == choice }
//                    .map { it.first }
//                    .firstOrNull()
//                    ?.let { values ->
//                        values.find { it.first.name == ID }?.second as Long
//                    }
//            } ?: EMPTY_LONG
//    }
//
//    private fun getBackgroundColor(): Int {
//        return ContextCompat.getColor(this, R.color.colorSurface)
//    }
//
//    private fun getFontColor(): Int {
//        return ContextCompat.getColor(this, R.color.primaryTextColor)
//    }
//
//    private fun getHintColor(): Int {
//        return ContextCompat.getColor(this, R.color.secondaryTextColor)
//    }
}

private class NotCompletedFieldsException : Exception("Не все поля заполнены!")