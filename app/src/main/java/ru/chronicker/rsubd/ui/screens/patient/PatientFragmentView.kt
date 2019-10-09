package ru.chronicker.rsubd.ui.screens.patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.screens.form.ENTITY
import ru.chronicker.rsubd.ui.screens.form.FormActivityView

class PatientFragmentView : BaseFragment() {

    private val adapter = PatientAdapter {
        openForm(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_double_items, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initList()
    }

    private fun initList() {
        items_rv.adapter = adapter
        generateItems()
    }

    private fun generateItems() {
        adapter.setItems(mutableListOf(
                PatientModel(1, "Попытка 1", "Сколько можно?!"),
                PatientModel(2, "Попытка 2", "Сколько можно?!"),
                PatientModel(3, "Попытка 3", "Сколько можно?!"),
                PatientModel(4, "Попытка 4", "Сколько можно?!"),
                PatientModel(5, "Попытка 5", "Сколько можно?!"),
                PatientModel(6, "Попытка 6", "Сколько можно?!")
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
}
