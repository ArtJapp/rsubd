package ru.chronicker.rsubd.ui.base

abstract class ItemModel(
    open val id: Int
)

class DoubleItemModel(
    override val id: Int,
    val title: String,
    val subtitle: String
): ItemModel(id)

data class AloneItemModel(
    override val id: Int,
    val title: String
): ItemModel(id)
