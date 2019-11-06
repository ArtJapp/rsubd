package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.*

/**
 * Маппинг-модель лечения пациента, включает в себя название лечения
 * даты начала и окончания и необходимость госпитализации
 */
class Treatment : Entity(
    name = "Treatment",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        Field(
            name = "NAME",
            type = FieldType.TEXT,
            title = "Наименование"
        ),
        IntField(
            name = "TREATMENT_START",
            title = "Начало лечения"
        ),
        IntField(
            name = "TREATMENT_END",
            title = "Окончание лечения"
        ),
        BooleanField(
            name = "AMBULANCE_REQUIRED",
            title = "Нужна госпитализация"
        )
    )
) {

    override fun convertToString(values: List<Pair<Field, Any>>): String {
        return values.find { it.first.name == "NAME" }?.second?.toString() ?: EMPTY_STRING
    }
}