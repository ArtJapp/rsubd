package ru.chronicker.rsubd.ui.screens.main.fragments.social_status

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.SocialStatus
import ru.chronicker.rsubd.ui.screens.form.SocialStatusRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveAloneFragmentView

class SocialStatusFragmentView : PrimitiveAloneFragmentView<SocialStatus, SocialStatusRoute>(
    screenName = "SocialStatusFragmentView",
    entity = SocialStatus(),
    route = SocialStatusRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }
}