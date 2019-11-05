package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

private const val ID = "ID"
private const val NAME = "NAME"

/**
 * Маппинг-модель специализации врача
 */
class Specialization : Entity(
    name = "Specialization",
    fields = mutableListOf(
        IntField(
            name = ID,
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        Field(
            name = NAME,
            type = FieldType.TEXT,
            title = "Наименование"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        val id = values.find { it.first.name == ID }?.second?.toString() ?: EMPTY_STRING
        val name = values.find { it.first.name == NAME }?.second?.toString() ?: EMPTY_STRING
        return "$id. $name"
    }
}