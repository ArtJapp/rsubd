package ru.chronicker.rsubd.ui.screens.main.fragments.patient

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Disease
import ru.chronicker.rsubd.database.models.Patient
import ru.chronicker.rsubd.ui.screens.form.DiseaseRoute
import ru.chronicker.rsubd.ui.screens.form.PatientRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView

class PatientFragmentView : PrimitiveDoubleFragmentView<Patient, PatientRoute>(
    screenName = "PatientFragmentView",
    entity = Patient(),
    route = PatientRoute()
) {

    override fun shouldPlusBeEnabledForDoctor(): Boolean {
        return true
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "PERSON_ID" }?.second ?: EMPTY_STRING
    }

    override fun provideSubtitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "STATUS_ID" }?.second ?: EMPTY_STRING
    }
}
