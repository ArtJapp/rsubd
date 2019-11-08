package ru.chronicker.rsubd.ui.screens.main.fragments.history

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.History
import ru.chronicker.rsubd.ui.screens.form.HistoryRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView

class HistoryFragmentView : PrimitiveAloneFragmentView<History, HistoryRoute>(
    screenName = "HistoryFragmentView",
    entity = History(),
    route = HistoryRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return entity.convertMappedToString(values)
    }
//
//    override fun provideSubtitle(values: List<Pair<String, String>>): String {
//        return values.find { it.first == "DIAGNOSIS_ID" }?.second ?: EMPTY_STRING
//    }
}