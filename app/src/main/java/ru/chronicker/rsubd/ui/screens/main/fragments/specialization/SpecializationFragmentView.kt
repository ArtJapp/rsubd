package ru.chronicker.rsubd.ui.screens.main.fragments.specialization

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Specialization
import ru.chronicker.rsubd.ui.screens.form.SpecializationRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class SpecializationFragmentView : PrimitiveAloneFragmentView<Specialization, SpecializationRoute>(
    screenName = "SpecializationFragmentView",
    entity = Specialization(),
    route = SpecializationRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }
}