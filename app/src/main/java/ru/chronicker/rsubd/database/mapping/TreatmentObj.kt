package ru.chronicker.rsubd.database.mapping

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.IntField
import ru.chronicker.rsubd.domain.Model
import ru.chronicker.rsubd.domain.Treatment
import java.util.*

/**
 * Маппинг-модель лечения пациента, включает в себя название лечения
 * даты начала и окончания и необходимость госпитализации
 */
class TreatmentObj : Entity(
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
) {
//    override fun convertToModel(): Treatment =
//        Treatment(
//            id = values["ID"] as Int,
//            name = values["NAME"] as String,
//            treatmentStart = Date().apply {
//                this.time = values["TREATMENT_START"] as Long
//            },
//            treatmentEnd = Date().apply {
//                this.time = values["TREATMENT_END"] as Long
//            },
//            ambulanceRequired = (values["AMBULANCE_REQUIRED"] as Int) != 0
//        )
}