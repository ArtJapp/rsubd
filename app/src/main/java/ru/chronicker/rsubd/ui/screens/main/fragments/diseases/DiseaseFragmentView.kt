package ru.chronicker.rsubd.ui.screens.main.fragments.diseases

import android.content.Intent
import android.util.Log
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.ui.base.AloneItemModel
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.form.FormActivityView

class DiseaseFragmentView : BaseFragment<Disease, AloneItemModel>() {

    override val screenName: String = "DiseaseFragmentView"
    override val entity = Disease()

    override fun initViews() {
        initList()
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): AloneItemModel {
        return AloneItemModel(
            id = values.find { it.first == "ID" }?.second?.toInt() ?: EMPTY_INT,
            title = values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
        )
    }

    private fun initList() {
        setListAdapter(
            adapter = DiseaseAdapter {
                Log.d(screenName, "Disease id = $it")
                openDiseaseForm(it)
            }
        )
        loadData()
    }

    private fun openDiseaseForm(id: Int) {
        val intent = Intent(this.context, FormActivityView::class.java)
        val d = Disease()
        d.values["ID"] = id as Any
        d.values["NAME"] = "Попытка $id" as Any

        intent.putExtra(ENTITY, d)
        startActivity(intent)
    }
}