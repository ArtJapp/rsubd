package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

/**
 * Общая маппинг-модель человека
 */
class Person : Entity(
    name = "Person",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true,
            title = "id"
        ),
        Field(
            name = "FIRST_NAME",
            type = FieldType.TEXT,
            title = "Имя"
        ),
        Field(
            name = "SECOND_NAME",
            type = FieldType.TEXT,
            title = "Отчество"
        ),
        Field(
            name = "LAST_NAME",
            type = FieldType.TEXT,
            title = "Фамилия"
        )
    )
)