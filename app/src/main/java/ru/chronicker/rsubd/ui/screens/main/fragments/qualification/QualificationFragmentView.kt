package ru.chronicker.rsubd.ui.screens.main.fragments.qualification

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Qualification
import ru.chronicker.rsubd.ui.screens.form.QualificationRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class QualificationFragmentView : PrimitiveAloneFragmentView<Qualification, QualificationRoute>(
    screenName = "QualificationFragmentView",
    entity = Qualification(),
    route = QualificationRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }
}