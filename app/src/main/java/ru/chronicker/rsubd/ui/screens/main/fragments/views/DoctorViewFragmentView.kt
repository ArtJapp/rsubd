package ru.chronicker.rsubd.ui.screens.main.fragments.views

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.views.DoctorView
import ru.chronicker.rsubd.ui.base.DoctorViewItemModel
import ru.chronicker.rsubd.ui.screens.form.DoctorViewRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveFragmentView
import ru.chronicker.rsubd.ui.screens.main.fragments.views.doctor_view.DoctorViewAdapter

class DoctorViewFragmentView : PrimitiveFragmentView<DoctorView, DoctorViewItemModel, DoctorViewRoute>(
    entity = DoctorView(),
    route = DoctorViewRoute(),
    screenName = "DoctorViewFragmentView"
) {

    override fun provideAdapter(): PrimitiveAdapter<DoctorViewItemModel> {
        return DoctorViewAdapter(
            onClickListener = { },
            onDeleteListener = { }
        )
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): DoctorViewItemModel {
        return DoctorViewItemModel(
            lastName = provideLastName(values),
            firstName = provideFirstName(values),
            secondName = provideSecondName(values),
            qualification = provideQualification(values),
            specialization = provideSpecialization(values)
        )
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return EMPTY_STRING
    }

    private fun provideSpecialization(values: List<Pair<String, String>>): String {
        return values.find { it.first == "SPECIALIZATION" }?.second ?: EMPTY_STRING
    }

    private fun provideQualification(values: List<Pair<String, String>>): String {
        return values.find { it.first == "QUALIFICATION" }?.second ?: EMPTY_STRING
    }

    private fun provideFirstName(values: List<Pair<String, String>>): String {
        return values.find { it.first == "FIRST_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideSecondName(values: List<Pair<String, String>>): String {
        return values.find { it.first == "SECOND_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideLastName(values: List<Pair<String, String>>): String {
        return values.find { it.first == "LAST_NAME" }?.second ?: EMPTY_STRING
    }
}