package ru.chronicker.rsubd.models

import ru.chronicker.rsubd.database.base.*

class Diagnosis : Entity(
    name = "Diagnosis",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        ForeignKeyField(
            name = "DISEASE_ID",
            type = FieldType.INTEGER,
            foreignTable = "Disease",
            foreignKey = "id"
        ),
        ForeignKeyField(
            name = "STATE_ID",
            type = FieldType.INTEGER,
            foreignTable = "State",
            foreignKey = "id"
        )
    )
)