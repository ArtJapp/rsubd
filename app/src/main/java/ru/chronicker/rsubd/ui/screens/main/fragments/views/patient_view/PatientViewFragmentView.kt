package ru.chronicker.rsubd.ui.screens.main.fragments.views.patient_view

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.views.PatientView
import ru.chronicker.rsubd.ui.base.PatientViewItemModel
import ru.chronicker.rsubd.ui.screens.form.PatientViewRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAdapter
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveFragmentView
import ru.chronicker.rsubd.ui.screens.main.fragments.treatment.toSimpleString
import java.util.*

class PatientViewFragmentView : PrimitiveFragmentView<PatientView, PatientViewItemModel, PatientViewRoute> (
    screenName = "PatientViewFragmentView",
    entity = PatientView(),
    route = PatientViewRoute()
) {
    override fun provideAdapter(): PrimitiveAdapter<PatientViewItemModel> {
        return PatientViewAdapter(
            onClickListener = { },
            onDeleteListener = { }
        )
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): PatientViewItemModel {
        return PatientViewItemModel(
            lastName = provideLastName(values),
            firstName = provideFirstName(values),
            secondName = provideSecondName(values),
            disease = provideDisease(values),
            socialStatus = provideSocialStatus(values),
            state = provideState(values),
            birthDate = provideBirthdate(values)
        )
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return EMPTY_STRING
    }

    private fun provideSocialStatus(values: List<Pair<String, String>>): String {
        return values.find { it.first == "SS_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideBirthdate(values: List<Pair<String, String>>): String {
        return values.find { it.first == "BIRTH_DATE" }
            ?.let {
                Date(it.second.toLong()).toSimpleString()
            } ?: EMPTY_STRING
    }

    private fun provideState(values: List<Pair<String, String>>): String {
        return values.find { it.first == "S_NAME" }?.second ?: EMPTY_STRING
    }

    private fun provideDisease(values: List<Pair<String, String>>): String {
        return values.find { it.first == "D_NAME" }?.second ?: EMPTY_STRING
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