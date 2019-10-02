package ru.chronicker.rsubd.database.operations.select

import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.Scripts
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.domain.Model
import java.sql.ResultSet

/**
 * Подготовка запроса на простое чтение сущности, которая может иметь внешние ключи.
 *
 * @param entity - считываемая сущность
 * @param fields - список выбираемых полей.
 * Не обрабатывается явно случай совпадения названий полей при объединении таблиц,
 * но это можно учесть в списке полей [fields] (например, явно указать, из какой
 * таблицы будет браться значение)
 */
fun readWithJoins(entity: Entity, fields: List<String> = listOf("*")): String {
    return entity.fields
        .filter { it is ForeignKeyField }
        // выбираем все внешние ключи таблицы, объединяем их в связку Left-Join-ов
        .map { it as ForeignKeyField }.joinToString(separator = " ") {
            Scripts.LEFT_JOIN.format(
                it.foreignTable,
                it.foreignTable,
                it.foreignKey,
                entity.name,
                it.name
            )
        }
        .let { joins ->
            // формируем SELECT запрос с учетом связки Left-Join-ов
            Scripts.SELECT.format(
                fields.joinToString(", "),
                "${entity.name} $joins"
            )
        }
}

/**
 * Основной метод чтения сущности.
 *
 * @param entity - считываемая таблица/сущность/маппинг-модель
 * @param executor выполняет запрос в базу данных. Подает непосредственно запрос
 * на выполнение, ожидает выборку данных типа [ResultSet]
 * @param filter - фильтр, накладываемый на выборку данных. По сути, программный WHERE
 * @param combiner объединяет связку полей и их значений для одной строки
 * в единую конечную модель
 */
fun select(
    entity: Entity,
    executor: (String) -> ResultSet?,
    filter: (List<Pair<Field, Any>>) -> Boolean = { true },
    combiner: (List<Pair<Field, Any>>) -> Model
): List<Model> {
    ScriptConstructor.formSelect(entity).let(executor)
        ?.also {
            val results = mutableListOf<Model>()
            // итерация по каждому элементу списка
            while(it.next()) {
                combineFieldsToValues(it, entity.fields).also {combinedFields ->
                    if (filter(combinedFields)) {
                        results.add(combiner.invoke(combinedFields))
                    }
                }
            }
            return results
        }
    return emptyList()
}

private fun combineFieldsToValues(resultSet: ResultSet, fields: List<Field>): List<Pair<Field, Any>> {
    return fields.map {
        it to when(it.type) {
            FieldType.TEXT -> resultSet.getString(it.name)
            FieldType.INTEGER -> resultSet.getInt(it.name)
            FieldType.REAL -> resultSet.getFloat(it.name)
            else -> EMPTY_INT
        }
    }
}