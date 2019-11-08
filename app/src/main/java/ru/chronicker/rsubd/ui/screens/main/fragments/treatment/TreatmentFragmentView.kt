package ru.chronicker.rsubd.ui.screens.main.fragments.treatment

import android.annotation.SuppressLint
import ru.chronicker.rsubd.Constants.DATE_PATTERN
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.models.Treatment
import ru.chronicker.rsubd.ui.screens.form.TreatmentRoute
import ru.chronicker.rsubd.ui.screens.main.fragments.primitive.PrimitiveDoubleFragmentView
import java.text.SimpleDateFormat
import java.util.*

class TreatmentFragmentView : PrimitiveDoubleFragmentView<Treatment, TreatmentRoute>(
    screenName = "TreatmentFragmentView",
    entity = Treatment(),
    route = TreatmentRoute()
) {

    override fun convertToTitle(values: List<Pair<String, String>>): String {
        return values.find { it.first == "NAME" }?.second ?: EMPTY_STRING
    }

    override fun provideSubtitle(values: List<Pair<String, String>>): String {
        val start = (values.find { it.first == "TREATMENT_START" }?.second ?: "0")
            .let {
                Date(it.toLong()).toSimpleString()
            }
        val end = (values.find { it.first == "TREATMENT_END" }?.second ?: "0")
            .let {
                Date(it.toLong()).toSimpleString()
            }
        return "$start - $end"
    }
}

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date {
    return SimpleDateFormat(DATE_PATTERN).parse(this) ?: Date()
}