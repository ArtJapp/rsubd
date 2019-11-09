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
import ru.chronicker.rsubd.database.base.*
import ru.chronicker.rsubd.database.models.*
import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formDelete
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formInsert
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formQueryMaxId
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formSelect
import ru.chronicker.rsubd.database.utils.ScriptConstructor.Companion.formUpdate
import ru.chronicker.rsubd.database.views.DoctorView
import ru.chronicker.rsubd.database.views.PatientView
import java.lang.Exception
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

    private val views = listOf(
        DoctorView(),
        PatientView()
    )

//    init {
//        dropDB(writableDatabase)
//        onCreate(writableDatabase)
//    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { database ->
            initializeTables(database)
            initializeViews(database)
        }
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropDB(db)
        onCreate(db)
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
    fun insert(
        entity: Entity,
        values: List<Value>,
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        val request = formInsert(entity, values)
        doRequest(request, onSuccess, onError)
    }

    fun update(
        entity: Entity,
        values: List<Value>,
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        val request = formUpdate(entity, values)
        doRequest(request, onSuccess, onError)
    }

    fun delete(
        entity: Entity,
        values: List<Value>,
        onSuccess: (() -> Unit)?,
        onError: ((String) -> Unit)?
    ) {
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

    private fun initializeTables(database: SQLiteDatabase) {
        entities.forEach { entity ->
            ScriptConstructor.formCreate(entity)
                .let { script ->
                    try {
                        log(script)
                        database.execSQL(script)
                    } catch (e: Exception) {
                        log(e.toString())
                    }
                }
        }
    }

    private fun initializeViews(database: SQLiteDatabase) {
        views.forEach { view ->
            doRequest(
                ScriptConstructor.formCreateView((view as Entity).name, (view as View).getBaseScript()),
                { },
                {
                    log(it)
                }
            )
        }
    }

    fun clear() {
        dropDB(writableDatabase)
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

    private fun dropDB(db: SQLiteDatabase?) {
        dropViews(db)
        dropAllTables(db)
    }

    private fun dropViews(db: SQLiteDatabase?) {
        db?.let { database ->
            views.reversed()
                .map { (it as Entity).name }
                .forEach { viewName ->
                    try {
                        ScriptConstructor.formDropView(viewName)
                            .let { script ->
                                log(script)
                                database.execSQL(script)
                            }
                    } catch (e: Exception) {
                        log(e.toString())
                    }
                }
        }
    }

    private fun dropAllTables(db: SQLiteDatabase?) {
        db?.let { database ->
            entities.reversed()
                .map { "main." + it.name }
                .forEach { entityName ->
                    try {
                        ScriptConstructor.formDrop(entityName)
                            .let { script ->
                                log(script)
                                database.execSQL(script)
                            }
                    } catch (e: Exception) {
                        log(e.toString())
                    }
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