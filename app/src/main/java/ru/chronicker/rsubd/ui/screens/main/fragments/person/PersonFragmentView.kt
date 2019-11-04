package ru.chronicker.rsubd.ui.screens.main.fragments.person

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Person
import ru.chronicker.rsubd.ui.screens.form.PersonRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class PersonFragmentView : PrimitiveAloneFragmentView<Person, PersonRoute>(
    screenName = "PatientFragmentView",
    entity = Person(),
    route = PersonRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.findFIO()
    }
}

fun List<Pair<String, String>>.findFIO(): String {
    val firstName = this.find { it.first == "FIRST_NAME" }?.second ?: EMPTY_STRING
    val secondName = this.find { it.first == "SECOND_NAME" }?.second ?: EMPTY_STRING
    val lastName = this.find { it.first == "LAST_NAME" }?.second ?: EMPTY_STRING
    return "$firstName $secondName $lastName"
}