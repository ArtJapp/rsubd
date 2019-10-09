package ru.chronicker.rsubd.ui.screens.patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntValue
import ru.chronicker.rsubd.database.base.TextValue
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.form.ENTITY
import ru.chronicker.rsubd.ui.screens.form.FormActivityView

class PatientFragmentView : BaseFragment() {

    private lateinit var dbHelper: DBHelper
    private var ids: Int = 0

    private val adapter = PatientAdapter {
        openForm(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_double_items, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            dbHelper = DBHelper(it)
        }
        initViews()
    }

    private fun initViews() {
        initList()
//        insertMoreData()
    }

    private fun initList() {
        items_rv.adapter = adapter
        loadData()
    }

    private fun generateItems() {
        adapter.setItems(mutableListOf(
                DoubleItemModel(1, "Попытка 1", "Сколько можно?!"),
                DoubleItemModel(2, "Попытка 2", "Сколько можно?!"),
                DoubleItemModel(3, "Попытка 3", "Сколько можно?!"),
                DoubleItemModel(4, "Попытка 4", "Сколько можно?!"),
                DoubleItemModel(5, "Попытка 5", "Сколько можно?!"),
                DoubleItemModel(6, "Попытка 6", "Сколько можно?!")
        ))
    }

    private fun openForm(id: Int) {
        val intent = Intent(this.context, FormActivityView::class.java)
        val d = Disease()
        d.values["ID"] = id as Any
        d.values["NAME"] = "Попытка $id" as Any

        intent.putExtra(ENTITY, d)
        startActivity(intent)
    }

    private fun getValue(value: Any, field: Field): String {
        return when(field.type) {
            FieldType.TEXT -> value as String
            FieldType.INTEGER -> (value as Int).toString()
            FieldType.REAL -> (value as Float).toString()
            else -> EMPTY_STRING
        }
    }

    private fun insertSomeData() {
        dbHelper.insert(Disease(), listOf(IntValue(1), TextValue("Вавка")))
        dbHelper.insert(Disease(), listOf(IntValue(2), TextValue("Ранка")))
        dbHelper.insert(Disease(), listOf(IntValue(3), TextValue("Болячка")))
    }

    private fun insertMoreData() {
        dbHelper.insert(Disease(), listOf(IntValue(ids), TextValue("Вавка")))
    }

    private fun loadData() {
        dbHelper.select(Disease())
            .also {
                ids = it.size + 1
            }
            .map { result ->
                result.fields
                    .map { (field, value) ->
                        Pair(field.name, getValue(value, field))
                    }
            }
            .map {pair ->
                ItemModel(
                    id = pair.find { it.first == "ID" }?.second?.toInt() ?: 0,
                    title = pair.find { it.first == "NAME" }?.second ?: EMPTY_STRING
                )
            }
            .let {
                adapter.setItems(it)
            }
    }


}
