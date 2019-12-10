package ru.chronicker.rsubd.ui.screens.main.fragments.history

import ru.chronicker.rsubd.database.models.History
import ru.chronicker.rsubd.ui.screens.form.HistoryRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class HistoryFragmentView : PrimitiveAloneFragmentView<History, HistoryRoute>(
    screenName = "HistoryFragmentView",
    entity = History(),
    route = HistoryRoute()
) {

    override fun shouldPlusBeEnabledForDoctor(): Boolean {
        return true
    }

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return entity.convertMappedToString(values)
    }
}