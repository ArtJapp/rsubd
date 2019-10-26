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

abstract class InputItemModel(
    override val id: Int,
    open val title: String
): ItemModel(id)

data class TextInputItemModel(
    override val id: Int,
    override val title: String,
    val value: String
): InputItemModel(id, title)

data class SelectableInputItemModel(
    override val id: Int,
    override val title: String,
    val values: List<String>,
    val selectedValue: String
): InputItemModel(id, title)