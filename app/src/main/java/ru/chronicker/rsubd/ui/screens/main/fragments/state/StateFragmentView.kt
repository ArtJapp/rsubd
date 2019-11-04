package ru.chronicker.rsubd.ui.screens.main.fragments.state

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.State
import ru.chronicker.rsubd.ui.screens.form.StateRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class StateFragmentView : PrimitiveAloneFragmentView<State, StateRoute>(
    screenName = "StateFragmentView",
    entity = State(),
    route = StateRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }
}