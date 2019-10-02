package ru.chronicker.rsubd.database.operations.select

import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.mapping.*
import ru.chronicker.rsubd.domain.*
import java.sql.ResultSet

class ReadingOperations {

    private fun readState() {
        select(
            entity = StateObj(),
            executor = {
                executeWithReturningResult(it)
            },
            combiner = { fields ->
                State(
                    id = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT,
                    name = fields.find { it.first.name == "NAME" }?.second as? String
                        ?: EMPTY_STRING
                )
            }
        )
    }

    private fun readDiploma(doctorId: Int = EMPTY_INT): List<Diploma> {
        return select(
            entity = DiplomaObj(),
            executor = {
                executeWithReturningResult(it)
            },
            filter = { fields ->
                true.takeIf { doctorId == EMPTY_INT }
                        ?: (fields.find { it.first.name == "DOCTOR_ID" }?.second as? Int
                    ?: EMPTY_INT) == doctorId
            },
            combiner = { fields ->
                Diploma(
                    id = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT,
                    specialization = (fields.find { it.first.name == "SPECIALIZATION_ID" }?.second as? Int
                        ?: EMPTY_INT)
                        .let { specializationId ->
                            readSpecialization().first { it.id == specializationId }
                        }
                )
            }
        ).map { it as Diploma }
    }

    private fun readSpecialization(): List<Specialization> {
        return select(
            entity = SpecializationObj(),
            executor = {
                executeWithReturningResult(it)
            },
            combiner = { fields ->
                Specialization(
                    id = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT,
                    name = fields.find { it.first.name == "NAME" }?.second as? String
                        ?: EMPTY_STRING
                )
            }
        ).map { it as Specialization }
    }

    private fun readQualification(): List<Qualification> {
        return select(
            entity = QualificationObj(),
            executor = {
                executeWithReturningResult(it)
            },
            combiner = { fields ->
                Qualification(
                    id = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT,
                    name = fields.find { it.first.name == "NAME" }?.second as? String
                        ?: EMPTY_STRING
                )
            }
        ).map { it as Qualification }
    }

    private fun readPerson(): List<Person> {
        return select(
            entity = PersonObj(),
            executor = {
                executeWithReturningResult(it)
            },
            combiner = { fields ->
                Person(
                    id = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT,
                    firstName = fields.find { it.first.name == "FIRST_NAME" }?.second as? String
                        ?: EMPTY_STRING,
                    secondName = fields.find { it.first.name == "SECOND_NAME" }?.second as? String
                        ?: EMPTY_STRING,
                    lastName = fields.find { it.first.name == "LAST_NAME" }?.second as? String
                        ?: EMPTY_STRING
                )
            }
        ).map { it as Person }
    }

    private fun readDoctor(): List<Doctor> {
        return select(
            entity = DoctorObj(),
            executor = {
                executeWithReturningResult(it)
            },
            combiner = { fields ->
                val personId =
                    fields.find { it.first.name == "PERSON_ID" }?.second as? Int ?: EMPTY_INT
                val person = readPerson().find { it.id == personId }
                val doctorId = fields.find { it.first.name == "ID" }?.second as? Int ?: EMPTY_INT
                val diploma = readDiploma(doctorId).first()
                val qualificationId =
                    fields.find { it.first.name == "QUALIFICATION_ID" }?.second as? Int ?: EMPTY_INT
                val qualification = readQualification().find { it.id == qualificationId }
                Doctor(
                    id = doctorId,
                    firstName = person?.firstName ?: EMPTY_STRING,
                    secondName = person?.secondName ?: EMPTY_STRING,
                    lastName = person?.lastName ?: EMPTY_STRING,
                    diploma = diploma,
                    qualification = qualification ?: Qualification()
                )
            }
        ).map { it as Doctor }
    }

    private fun executeWithReturningResult(sqlQuery: String): ResultSet {
        return ResultSet()
    }
}