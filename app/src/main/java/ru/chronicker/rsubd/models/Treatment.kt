package ru.chronicker.rsubd.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

class Treatment : Entity(
    name = "Treatment",
    fields = mutableListOf(
        IntField(
            name = "ID",
            primaryKey = true,
            autoIncrement = true
        ),
        Field(
            name = "NAME",
            type = FieldType.TEXT
        ),
        IntField(
            name = "TREATMENT_START"
        ),
        IntField(
            name = "TREATMENT_END"
        ),
        IntField(
            name = "AMBULANCE_REQUIRED"
        )
    )
)