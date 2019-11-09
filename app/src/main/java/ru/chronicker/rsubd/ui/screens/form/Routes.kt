package ru.chronicker.rsubd.ui.screens.form

import android.content.Context
import android.content.Intent
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.Constants.MODE
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.*
import ru.chronicker.rsubd.database.views.DoctorView
import ru.chronicker.rsubd.database.views.PatientView

private const val NON_EXISTING_ID = -1

abstract class FormRoute<T : Entity> {

    abstract val entity: T
    protected abstract fun prepareIntent(intent: Intent, id: Int): Intent

    private var intent: Intent? = null

    fun withValues(values: Map<String, Value>) {
        values.forEach { (key, value) ->
            intent?.putExtra(key, value.wrap())
        }
    }

    fun createForm(context: Context) {
        Intent(context, FormActivityView::class.java)
            .putExtra(MODE, FormMode.CREATE)
            .let { prepareIntent(it, NON_EXISTING_ID) }
            .let {
                intent = it
            }
        startIntent(context)
    }

    fun updateForm(context: Context, id: Int) {
        Intent(context, FormActivityView::class.java)
            .putExtra(MODE, FormMode.UPDATE)
            .let { prepareIntent(it, id) }
            .let {
                intent = it
            }
        startIntent(context)
    }

    private fun startIntent(context: Context) {
        context.startActivity(intent)
    }
}

enum class FormMode {
    CREATE,
    UPDATE
}

abstract class LazyRoute<T : Entity>(
    override val entity: T
) : FormRoute<T>() {

    override fun prepareIntent(intent: Intent, id: Int): Intent =
        intent.apply {
            putExtra(ID, id)
            putExtra(ENTITY, entity)
        }
}

class PersonRoute : LazyRoute<Person>(Person())
class DoctorRoute : LazyRoute<Doctor>(Doctor())
class DiplomRoute : LazyRoute<Diploma>(Diploma())
class SpecializationRoute : LazyRoute<Specialization>(Specialization())
class QualificationRoute : LazyRoute<Qualification>(Qualification())
class PatientRoute : LazyRoute<Patient>(Patient())
class StateRoute : LazyRoute<State>(State())
class SocialStatusRoute : LazyRoute<SocialStatus>(SocialStatus())
class DiagnosisRoute : LazyRoute<Diagnosis>(Diagnosis())
class HistoryRoute : LazyRoute<History>(History())
class TreatmentRoute : LazyRoute<Treatment>(Treatment())
class DiseaseRoute : LazyRoute<Disease>(Disease())

class DoctorViewRoute : LazyRoute<DoctorView>(DoctorView())
class PatientViewRoute : LazyRoute<PatientView>(PatientView())
