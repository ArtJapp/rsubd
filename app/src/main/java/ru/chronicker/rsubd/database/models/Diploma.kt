package ru.chronicker.rsubd.database.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.IntField

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
)