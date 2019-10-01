package ru.chronicker.rsubd.test

import ru.chronicker.rsubd.database.utils.ScriptConstructor
import ru.chronicker.rsubd.database.mapping.*

/**
 * Генерация скрипта для создания всех таблиц базы данных
 * Необходимо соблюдать именно такой порядок
 */
fun createConstructionTest() {
    println(ScriptConstructor.formCreate(DiseaseObj()))
    println(ScriptConstructor.formCreate(StateObj()))
    println(ScriptConstructor.formCreate(TreatmentObj()))
    println(ScriptConstructor.formCreate(DiagnosisObj()))

    println(ScriptConstructor.formCreate(PersonObj()))
    println(ScriptConstructor.formCreate(SocialStatusObj()))
    println(ScriptConstructor.formCreate(PatientObj()))

    println(ScriptConstructor.formCreate(SpecializationObj()))
    println(ScriptConstructor.formCreate(QualificationObj()))
    println(ScriptConstructor.formCreate(DoctorObj()))
    println(ScriptConstructor.formCreate(DiplomaObj()))

    println(ScriptConstructor.formCreate(HistoryObj()))
}