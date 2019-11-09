package ru.chronicker.rsubd.ui.screens.main.fragments.views.disease_view

import ru.chronicker.rsubd.EMPTY_REAL
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.views.DiseaseView
import ru.chronicker.rsubd.ui.base.DiseaseViewItemModel
import ru.chronicker.rsubd.ui.screens.form.DiseaseViewRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveFragmentView

class DiseaseViewFragmentView : PrimitiveFragmentView<DiseaseView, DiseaseViewItemModel, DiseaseViewRoute>(
    entity = DiseaseView(),
    route = DiseaseViewRoute(),
    screenName = "DiseaseViewFragmentView"
) {

    override fun provideAdapter(): PrimitiveAdapter<DiseaseViewItemModel> {
        return DiseaseViewAdapter(
            onClickListener = { },
            onDeleteListener = { }
        )
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): DiseaseViewItemModel {
        return DiseaseViewItemModel(
            disease = provideDisease(values),
            treatment = provideTreatment(values),
            efficiency = provideEfficiency(values)
        )
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return EMPTY_STRING
    }

    private fun provideDisease(values: List<Pair<String, String>>): String {
        return values.find { it.first == "DISEASE_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideTreatment(values: List<Pair<String, String>>): String {
        return values.find { it.first == "TREATMENT_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideEfficiency(values: List<Pair<String, String>>): Float {
        return values.find { it.first == "EFFICIENCY" }?.second?.toFloat() ?: EMPTY_REAL
    }
}