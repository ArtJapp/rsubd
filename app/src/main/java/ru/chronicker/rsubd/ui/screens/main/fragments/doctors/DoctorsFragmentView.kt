package ru.chronicker.rsubd.ui.screens.main.fragments.doctors

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Doctor
import ru.chronicker.rsubd.ui.screens.form.DoctorRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView

class DoctorsFragmentView : PrimitiveDoubleFragmentView<Doctor, DoctorRoute>(
    screenName = "DoctorsFragmentView",
    entity = Doctor(),
    route = DoctorRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "PERSON_ID" }?.second ?: EMPTY_STRING
    }

    override fun provideSubtitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "QUALIFICATION_ID" }?.second ?: EMPTY_STRING
    }
}