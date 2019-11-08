package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Диплом №"

class Diploma : Entity(
    name = "Diploma",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        ForeignKeyField(
            name = "DOCTOR_ID",
            type = FieldType.INTEGER,
            foreignTable = "Doctor",
            foreignKey = "id",
            title = "Доктор",
            isCascade = true
        ),
        ForeignKeyField(
            name = "SPECIALIZATION_ID",
            type = FieldType.INTEGER,
            foreignTable = "Specialization",
            foreignKey = "id",
            title = "Специализация",
            isCascade = true
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == "ID" }
            ?.second
            ?.let {
                TITLE + it.toString()
            }
            ?: EMPTY_STRING
    }
}