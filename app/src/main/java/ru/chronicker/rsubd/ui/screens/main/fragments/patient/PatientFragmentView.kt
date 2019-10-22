package ru.chronicker.rsubd.ui.screens.main.fragments.patient

import android.content.Intent
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.database.models.Patient
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.base.DoubleItemModel
import ru.chronicker.rsubd.ui.screens.form.FormActivityView

class PatientFragmentView : BaseFragment<Patient, DoubleItemModel>() {

    override val screenName: String = "PatientFragmentView"
    override val entity = Patient()

    override fun convertToItemModel(values: List<Pair<String, String>>): DoubleItemModel {
        return DoubleItemModel(
            id = values.find { it.first == "ID" }?.second?.toInt() ?: EMPTY_INT,
            title = values.find { it.first == "" }?.second ?: EMPTY_STRING,
            subtitle = values.find { it.first == "" }?.second ?: EMPTY_STRING
        )
    }

    override fun initViews() {
        initList()
    }

    private fun initList() {
        setListAdapter(
            adapter = PatientAdapter {
                openForm(it)
            }
        )
        loadData()
    }

    private fun openForm(id: Int) {
        val intent = Intent(this.context, FormActivityView::class.java)
        val d = Disease()
        d.values["ID"] = id as Any
        d.values["NAME"] = "Попытка $id" as Any

        intent.putExtra(ENTITY, d)
        startActivity(intent)
    }

    /*private fun insertSomeData() {
        dbHelper.insert(Disease(), listOf(IntValue(1), TextValue("Вавка")))
        dbHelper.insert(Disease(), listOf(IntValue(2), TextValue("Ранка")))
        dbHelper.insert(Disease(), listOf(IntValue(3), TextValue("Болячка")))
    }

    private fun insertMoreData() {
        dbHelper.insert(Disease(), listOf(IntValue(ids), TextValue("Вавка")))
    }*/
}
