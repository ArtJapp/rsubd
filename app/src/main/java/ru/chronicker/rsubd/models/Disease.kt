package ru.chronicker.rsubd.models

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField

class Disease : Entity(
    name = "Disease",
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