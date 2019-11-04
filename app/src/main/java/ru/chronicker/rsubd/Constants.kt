package ru.chronicker.rsubd

const val EMPTY_STRING = ""
const val EMPTY_INT = 0
const val EMPTY_REAL = 0.0
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
    const val SELECT = "SELECT %s FROM %s;"
    const val SELECT_WITH_CONDITION = "SELECT %s FROM %s WHERE %s ;"
    const val INSERT = "INSERT INTO %s VALUES(%s);"
    const val UPDATE = "UPDATE %s SET %s WHERE %s;"
}

object Constants {
    const val ID = "ID"
    const val MODE = "MODE"
    const val ENTITY = "ENTITY"
    const val VALUES = "VALUES"
}