package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

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
        IntField(
            name = "AMBULANCE_REQUIRED",
            title = "Нужна госпитализация"
        )
    )
)