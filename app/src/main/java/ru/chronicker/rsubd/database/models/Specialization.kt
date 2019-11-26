package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

private const val ID = "ID"
private const val NAME = "NAME"
private const val IS_DEFAULT = "IS_DEFAULT"

/**
 * Маппинг-модель специализации врача
 *
 * @property IS_DEFAULT - показывает, является ли специализация основной (т.е. можно ли при перераспределении
 * пациентов на крайний случай назначить больных на них)
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
        ),
        BooleanField(
            name = IS_DEFAULT,
            title = "Основной специалист"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == NAME }?.second?.toString() ?: EMPTY_STRING
    }
}