package ru.chronicker.rsubd.database.views

import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.View

private const val DISEASE_NAME = "DISEASE_NAME"
private const val TREATMENT_NAME = "TREATMENT_NAME"
private const val EFFICIENCY = "EFFICIENCY"

/**
 * Маппинг-модель представления болезни и ее лечения
 */
class DiseaseView : Entity(
    name = "DiseaseView",
    fields = mutableListOf(
        Field(
            name = DISEASE_NAME,
            type = FieldType.TEXT,
            title = "Название болезни"
        ),
        Field(
            name = TREATMENT_NAME,
            type = FieldType.TEXT,
            title = "Название лечения"
        ),
        Field(
            name = EFFICIENCY,
            type = FieldType.REAL,
            title = "Эффективность"
        )
    )
), View {

    override fun getBaseScript(): String {
        return "SELECT DISTINCT Disease.NAME                                 AS DISEASE_NAME,\n" +
                "                TREATMENT.NAME                               AS TREATMENT_NAME,\n" +
                "                100.0 * R.saves / (R.deaths + R.saves) AS EFFICIENCY\n" +
                "FROM Diagnosis\n" +
                "         LEFT JOIN Disease on Diagnosis.DISEASE_ID = Disease.ID\n" +
                "         LEFT JOIN Treatment on Treatment.id = TREATMENT\n" +
                "         LEFT JOIN State S on Diagnosis.STATE_ID = S.ID\n" +
                "         LEFt JOIN (\n" +
                "    SELECT TREATMENT.NAME,\n" +
                "           sum(case State.NAME when 'Здоров' then 1 else 0 end) as saves,\n" +
                "           sum(case State.NAME when 'Умер' then 1 else 0 end)   as deaths\n" +
                "    FROM Diagnosis\n" +
                "             LEFT JOIN Treatment on Treatment.id = Diagnosis.TREATMENT\n" +
                "             Inner JOIN State on Diagnosis.STATE_ID = State.ID\n" +
                "    GROUP BY TREATMENT.NAME\n" +
                ") as R\n" +
                "                   ON TREATMENT.NAME = R.NAME\n" +
                "WHERE s.NAME = 'Здоров'\n" +
                "   or s.NAME = 'Умер'"
    }
}