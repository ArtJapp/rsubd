package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.IntField

/**
 * Маппинг-модель врача
 */
class Doctor : Entity(
    name = "Doctor",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        ForeignKeyField(
            name = "PERSON_ID",
            type = FieldType.INTEGER,
            foreignTable = "Person",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "QUALIFICATION_ID",
            type = FieldType.INTEGER,
            foreignTable = "Qualification",
            foreignKey = "id"
        )
    )
)