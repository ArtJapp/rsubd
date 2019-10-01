package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

/**
 * Маппинг-модель специализации врача
 */
class SpecializationObj : Entity(
    name = "Specialization",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        Field(
            name = "NAME",
            type = FieldType.TEXT
        )
    )
)