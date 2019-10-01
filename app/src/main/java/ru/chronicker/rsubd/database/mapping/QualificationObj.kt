package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField
import ru.chronicker.rsubd.domain.Qualification

/**
 * Маппинг-модель квалификации врача
 */
class QualificationObj : Entity(
    name = "Qualification",
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
) {

//    override fun convertToModel(): Qualification =
//        Qualification(
//            id = values["ID"] as Int,
//            name = values["NAME"] as String
//        )
}