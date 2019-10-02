package ru.chronicker.rsubd.ui.screens.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_patients.*
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.ui.base.BaseFragment

class PatientFragmentView : BaseFragment() {

    private val adapter = PatientAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patients, container, false)
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
                PatientModel("Попытка 1", "Сколько можно?!"),
                PatientModel("Попытка 2", "Сколько можно?!"),
                PatientModel("Попытка 3", "Сколько можно?!"),
                PatientModel("Попытка 4", "Сколько можно?!"),
                PatientModel("Попытка 5", "Сколько можно?!"),
                PatientModel("Попытка 6", "Сколько можно?!")
        ))
    }
}
