package ru.chronicker.rsubd.ui.screens.main.fragments.diseases

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.ui.screens.form.DiseaseRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class DiseaseFragmentView : PrimitiveAloneFragmentView<Disease, DiseaseRoute>(
    screenName = "DiseaseFragmentView",
    entity = Disease(),
    route = DiseaseRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }
}