package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField
import ru.chronicker.rsubd.domain.SocialStatus

/**
 * Маппинг-модель социального статуса пациента
 */
class SocialStatusObj : Entity(
    name = "SocialStatus",
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

//    override fun convertToModel(): SocialStatus =
//        SocialStatus(
//            id = values["ID"] as Int,
//            name = values["NAME"] as String
//        )
}