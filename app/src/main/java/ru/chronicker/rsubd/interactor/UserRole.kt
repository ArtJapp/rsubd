package ru.chronicker.rsubd.interactor

enum class UserRole(val title: String) {
    Patient("PATIENT"),
    Doctor("DOCTOR"),
    Admin("ADMIN"),
    Undefined("UNDEFINED");

    companion object {
        fun getByTitle(title: String): UserRole =
            values().firstOrNull { it.title == title } ?: Undefined
    }
}