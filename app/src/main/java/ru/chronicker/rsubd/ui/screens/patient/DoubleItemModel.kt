package ru.chronicker.rsubd.ui.screens.patient

import ru.chronicker.rsubd.EMPTY_STRING

open class DoubleItemModel(
    open val id: Int,
    open val title: String,
    open val subtitle: String
)

data class ItemModel(
    override val id: Int,
    override val title: String
): DoubleItemModel(id, title, EMPTY_STRING)
