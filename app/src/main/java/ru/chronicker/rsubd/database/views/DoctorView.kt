package ru.chronicker.rsubd.database.views

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.View

private const val FIRST_NAME = "FIRST_NAME"
private const val SECOND_NAME = "SECOND_NAME"
private const val LAST_NAME = "LAST_NAME"
private const val QUALIFICATION = "QUALIFICATION"
private const val SPECIALIZATION = "SPECIALIZATION"

/**
 * Маппинг-модель представления врача
 */
class DoctorView : Entity(
    name = "DoctorView",
    fields = mutableListOf(
        Field(
            name = FIRST_NAME,
            type = FieldType.TEXT,
            title = "Имя"
        ),
        Field(
            name = SECOND_NAME,
            type = FieldType.TEXT,
            title = "Отчество"
        ),
        Field(
            name = LAST_NAME,
            type = FieldType.TEXT,
            title = "Фамилия"
        ),
        Field(
            name = QUALIFICATION,
            type = FieldType.TEXT,
            title = "Квалификация"
        ),
        Field(
            name = SPECIALIZATION,
            type = FieldType.TEXT,
            title = "Специализация"
        )
    )
), View {

    override fun getBaseScript(): String {
        return "SELECT P.FIRST_NAME, P.SECOND_NAME, P.LAST_NAME, Q.NAME as Qualification, S.NAME as Specialization\n" +
                "FROM Doctor\n" +
                "         LEFT JOIN Person P on Doctor.PERSON_ID = P.ID\n" +
                "         LEFT JOIN Qualification Q on Doctor.QUALIFICATION_ID = Q.ID\n" +
                "         INNER JOIN Diploma D on Doctor.ID = D.DOCTOR_ID\n" +
                "         LEFT JOIN Specialization S on D.SPECIALIZATION_ID = S.ID"
    }
}