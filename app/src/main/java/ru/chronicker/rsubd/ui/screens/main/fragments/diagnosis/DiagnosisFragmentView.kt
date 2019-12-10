package ru.chronicker.rsubd.ui.screens.main.fragments.diagnosis

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Diagnosis
import ru.chronicker.rsubd.ui.screens.form.DiagnosisRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView

class DiagnosisFragmentView : PrimitiveDoubleFragmentView<Diagnosis, DiagnosisRoute>(
    screenName = "DiagnosisFragmentView",
    entity = Diagnosis(),
    route = DiagnosisRoute()
) {

    override fun shouldPlusBeEnabledForDoctor(): Boolean {
        return true
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return entity.convertMappedToString(values)
    }

    override fun provideSubtitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "DISEASE_ID" }?.second ?: EMPTY_STRING
    }
}
