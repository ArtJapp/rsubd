package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.IntField

class DiplomaObj : Entity(
    name = "Diploma",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        ForeignKeyField(
            name = "DOCTOR_ID",
            type = FieldType.INTEGER,
            foreignTable = "Doctor",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "SPECIALIZATION_ID",
            type = FieldType.INTEGER,
            foreignTable = "Specialization",
            foreignKey = "id"
        )
    )
)