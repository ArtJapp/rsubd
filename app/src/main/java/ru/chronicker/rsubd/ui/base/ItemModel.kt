package ru.chronicker.rsubd.ui.base

abstract class ItemModel(
    open val id: Int
)

class DoctorViewItemModel(
    val firstName: String,
    val secondName: String,
    val lastName: String,
    val qualification: String,
    val specialization: String
): ItemModel(0)

class PatientViewItemModel(
    val firstName: String,
    val secondName: String,
    val lastName: String,
    val birthDate: String,
    val socialStatus: String,
    val state: String,
    val disease: String
): ItemModel(0)

class DiseaseViewItemModel(
    val disease: String,
    val treatment: String,
    val efficiency: Float
): ItemModel(0)

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