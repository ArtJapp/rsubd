package ru.chronicker.rsubd.ui.screens.main.fragments.diploma

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Diploma
import ru.chronicker.rsubd.ui.screens.form.DiplomRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView

class DiplomaFragmentView : PrimitiveDoubleFragmentView<Diploma, DiplomRoute>(
    screenName = "DiplomaFragmentView",
    entity = Diploma(),
    route = DiplomRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "DOCTOR_ID" }?.second ?: EMPTY_STRING
    }

    override fun provideSubtitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "SPECIALIZATION_ID" }?.second ?: EMPTY_STRING
    }
}