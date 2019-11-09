package ru.chronicker.rsubd.database

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ru.chronicker.rsubd.DBConstants.DB_NAME
import ru.chronicker.rsubd.DBConstants.DB_VERSION
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.*
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formDelete
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formInsert
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formQueryMaxId
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formSelect
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formUpdate
import kotlin.math.max

private const val DB_HELPER = "DB_HELPER"
private val UNKNOWN_ERROR = "Unknown Error"

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val entities = listOf(
        Disease(),
        State(),
        Treatment(),
        Diagnosis(),
        Person(),
        SocialStatus(),
        Patient(),
        Specialization(),
        Qualification(),
        Doctor(),
        Diploma(),
        History()
    )

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { database ->
            entities.forEach { entity ->
                ScriptConstructor.formCreate(entity)
                    .let { script ->
                        log(script)
                        database.execSQL(script)
                    }
            }
        }
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropAllDatabases(db)
        onCreate(db)
    }

    private fun dropAllDatabases(db: SQLiteDatabase?) {
        db?.let { database ->
            entities.map { "main." + it.name }
                .reversed()
                .forEach { entityName ->
                    ScriptConstructor.formDrop(entityName)
                        .let { script ->
                            log(script)
                            database.execSQL(script)
                        }
                }
        }
    }

    fun select(entityName: String, params: Map<String, String>? = null): List<QueryResult> {
        return entities.find { it.name == entityName }
            ?.let { entity -> select(entity, params) }
            ?: emptyList()
    }

    /**
     * Метод выборки данных по таблице/сущности
     */
    fun select(entity: Entity, params: Map<String, String>? = null): List<QueryResult> {
        val result = mutableListOf<QueryResult>()
        val query = formSelect(entity, params.takeIf { it != null } ?: emptyMap())
        log(query)
        val response = readableDatabase.rawQuery(query, null)
        if (response.moveToFirst()) {
            do {
                result.add(combineFieldsToValues(response, entity.fields))
            } while (response.moveToNext())
        }
        response.close()
        return result
    }

    /**
     * Метод загрузки данных сущности в базу данных
     */
    fun insert(entity: Entity, values: List<Value>, onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        val request = formInsert(entity, values)
        doRequest(request, onSuccess, onError)
    }

    fun update(entity: Entity, values: List<Value>, onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        val request = formUpdate(entity, values)
        doRequest(request, onSuccess, onError)
    }

    fun delete(entity: Entity, values: List<Value>, onSuccess: (() -> Unit)?, onError: ((String) -> Unit)?) {
        val request = formDelete(entity, values)
        doRequest(request, onSuccess, onError)
    }

    fun getEntityByName(name: String): Entity? {
        return entities.find { it.name == name }
    }

    fun getMaxId(entity: Entity): Long {
        val request = formQueryMaxId(entity)
        val response = readableDatabase.rawQuery(request, null)
        var result = -1L
        if (response.moveToFirst()) {
            do {
                response.getLong(0)
                    .let {
                        result = max(it, result)
                    }
            } while (response.moveToNext())
        }
        response.close()
        return result
    }

    fun clear() {
        dropAllDatabases(writableDatabase)
        onCreate(writableDatabase)
    }

    private fun doRequest(request: String, onSuccess: (() -> Unit)?, onError: ((String) -> Unit)?) {
        log(request)
        try {
            writableDatabase.execSQL(request)
            onSuccess?.invoke()
            writableDatabase.close()
        } catch (error: SQLException) {
            error.message?.let {
                log(it)
                onError?.invoke(it)
            }
        }
    }

    private fun combineFieldsToValues(resultSet: Cursor, fields: List<Field>): QueryResult {
        return QueryResult(
            fields = fields.mapIndexed { index, field ->
                field to when (field.type) {
                    FieldType.TEXT -> resultSet.getString(index)
                    FieldType.INTEGER -> resultSet.getLong(index)
                    FieldType.REAL -> resultSet.getFloat(index)
                    else -> EMPTY_INT
                }
            }
        )
    }

    private fun log(request: String) {
        Log.d(DB_HELPER, request)
    }
}

data class QueryResult(
    val fields: List<Pair<Field, Any>>
)