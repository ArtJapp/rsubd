package ru.chronicker.rsubd

const val EMPTY_STRING = ""
const val EMPTY_INT = 0
const val EMPTY_LONG = 0L
const val EMPTY_REAL = 0.0F
val EMPTY_BLOB = Any()
const val SPACE = " "

object DBConstants {
    const val DB_NAME = "polyclinic_db"
    const val DB_VERSION = 1
}

object Scripts {
    const val PRIMARY_KEY = "PRIMARY KEY"
    const val AUTOINCREMENT = "AUTOINCREMENT"
    const val CREATE = "CREATE TABLE %s(%s);"
    const val DROP = "DROP TABLE IF EXISTS %s;"
    const val FOREIGN_KEY_INSTRUCTION = "FOREIGN KEY(%s) REFERENCES %s(%s)"
    const val CASCASE_UPDATE = "ON UPDATE CASCADE"
    const val CASCASE_DELETE = "ON DELETE CASCADE"
    const val SELECT = "SELECT %s FROM %s;"
    const val SELECT_WITH_CONDITION = "SELECT %s FROM %s WHERE %s ;"
    const val INSERT = "INSERT INTO %s(%s) VALUES(%s);"
    const val UPDATE = "UPDATE %s SET %s WHERE %s;"
    const val DELETE = "DELETE FROM %s WHERE %s;"
    const val GET_MAX_ID = "SELECT max(id) FROM %s;"
    const val CREATE_VIEW = "CREATE VIEW %s AS %s;"
    const val DROP_VIEW = "DROP VIEW IF EXISTS %s;"
    const val AUTH_QUERY = "SELECT ID FROM Person WHERE login = %s and password = %s;"
    const val GET_ROLE = "SELECT CASE\n" +
            "           WHEN ID = %s and ID in CURRENT_HEADS THEN 'ADMIN'\n" +
            "           WHEN ID = %s and ID in CURRENT_PATIENTS THEN 'PATIENT'\n" +
            "           WHEN ID = %s and ID in CURRENT_DOCTORS THEN 'DOCTOR'\n" +
            "           ELSE\n" +
            "               'UNDEFINED'\n" +
            "           END as ROLE\n" +
            "FROM Person;"
}

object Constants {
    const val ID = "ID"
    const val MODE = "MODE"
    const val ENTITY = "ENTITY"
    const val VALUES = "VALUES"
    const val DATE_PATTERN = "dd.MM.yyyy"
}