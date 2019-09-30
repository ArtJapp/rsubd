package ru.chronicker.rsubd.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.base.IntField

class History : Entity(
    name = "History",
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
            name = "PATIENT_ID",
            type = FieldType.INTEGER,
            foreignTable = "Patient",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "DIAGNOSIS_ID",
            type = FieldType.INTEGER,
            foreignTable = "Diagnosis",
            foreignKey = "id"
        )
    )
)