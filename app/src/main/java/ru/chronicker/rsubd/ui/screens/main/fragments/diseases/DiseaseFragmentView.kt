package ru.chronicker.rsubd.ui.screens.main.fragments.diseases

import android.content.Intent
import android.util.Log
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntValue
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.ui.base.AloneItemModel
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.form.DiseaseRoute
import ru.chronicker.rsubd.ui.screens.form.FormActivityView
import ru.chronicker.rsubd.ui.screens.form.FormRoute

class DiseaseFragmentView : BaseFragment<Disease, AloneItemModel>() {

    override val screenName: String = "DiseaseFragmentView"
    override val entity = Disease()

    override fun initViews() {
        fab.setOnClickListener {
            DiseaseRoute(context = requireContext(), id = -1).createForm()
        }
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
        DiseaseRoute(requireContext(), id = id).updateForm()
    }
}