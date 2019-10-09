package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.*

/**
 * Маппинг-модель пациента
 */
class Patient : Entity(
    name = "Patient",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        IntField(
            name = "BIRTH_DATE"
        ),
        ForeignKeyField(
            name = "PERSON_ID",
            type = FieldType.INTEGER,
            foreignTable = "Person",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "STATUS_ID",
            type = FieldType.INTEGER,
            foreignTable = "SocialStatus",
            foreignKey = "id"
        )
    )
)