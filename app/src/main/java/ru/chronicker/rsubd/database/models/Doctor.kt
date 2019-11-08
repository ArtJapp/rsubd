package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val TITLE = "Доктор "

/**
 * Маппинг-модель врача
 */
class Doctor : Entity(
    name = "Doctor",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        ForeignKeyField(
            name = "PERSON_ID",
            type = FieldType.INTEGER,
            foreignTable = "Person",
            foreignKey = "id",
            title = "Человек",
            isCascade = true
        ),
        ForeignKeyField(
            name = "QUALIFICATION_ID",
            type = FieldType.INTEGER,
            foreignTable = "Qualification",
            foreignKey = "id",
            title = "Квалификация",
            isCascade = true
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == "PERSON_ID" }
            ?.second
            ?.let {
                TITLE + it.toString()
            }
            ?: EMPTY_STRING
    }
}