package ru.chronicker.rsubd.database.views

import ru.chronicker.rsubd.database.base.*

private const val FIRST_NAME = "FIRST_NAME"
private const val SECOND_NAME = "SECOND_NAME"
private const val LAST_NAME = "LAST_NAME"
private const val BIRTH_DATE = "BIRTH_DATE"
private const val DISEASE_NAME = "D_NAME"
private const val SOCIAL_STATUS_NAME = "SS_NAME"
private const val STATE_NAME = "S_NAME"

/**
 * Маппинг-модель представления врача
 */
class PatientView : Entity(
    name = "PatientView",
    fields = mutableListOf(
        Field(
            name = "ID",
            type = FieldType.INTEGER,
            title = "id"
        ),
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
        DateField(
            name = BIRTH_DATE,
            title = "Дата рождения"
        ),
        Field(
            name = SOCIAL_STATUS_NAME,
            type = FieldType.TEXT,
            title = "Соц статус"
        ),
        Field(
            name = STATE_NAME,
            type = FieldType.TEXT,
            title = "Состояние здоровья"
        ),
        Field(
            name = DISEASE_NAME,
            type = FieldType.TEXT,
            title = "Последняя болезнь"
        )
    )
), View {

    override fun getBaseScript(): String {
        return "SELECT P.ID, P.FIRST_NAME, P.SECOND_NAME, P.LAST_NAME, BIRTH_DATE, SS.NAME AS SS_NAME, S.NAME as S_NAME, D2.NAME as D_NAME\n" +
                "FROM Patient LEFT JOIN Person P on Patient.PERSON_ID = P.id\n" +
                "LEFT JOIN SocialStatus SS on Patient.STATUS_ID = SS.ID\n" +
                "LEFT JOIN History H on Patient.ID = H.PATIENT_ID\n" +
                "LEFT JOIN Diagnosis D on H.DIAGNOSIS_ID = D.ID\n" +
                "LEFT JOIN State S on D.STATE_ID = S.ID\n" +
                "LEFT JOIN Disease D2 on D.DISEASE_ID = D2.ID"
    }
}