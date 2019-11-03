package ru.chronicker.rsubd.ui.screens.form

import android.content.Context
import android.content.Intent
import ru.chronicker.rsubd.Constants.ENTITY
import ru.chronicker.rsubd.Constants.ID
import ru.chronicker.rsubd.Constants.MODE
import ru.chronicker.rsubd.Constants.VALUES
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.Value
import ru.chronicker.rsubd.database.models.Disease

abstract class FormRoute<T : Entity>(private val context: Context) {

    abstract val entity: T
    protected abstract fun prepareIntent(intent: Intent): Intent

    private val intent: Intent

    init {
        Intent(context, FormActivityView::class.java)
//            .apply {
//                this.putExtra(ENTITY, entity)
//            }
            .also {
                intent = prepareIntent(it)
            }
    }

    fun withValues(values: Map<String, Value>) {
        values.forEach { (key, value) ->
            intent.putExtra(key, value.wrap())
        }
    }

    fun createForm() {
        intent.putExtra(MODE, FormMode.CREATE)
        startIntent()
    }

    fun updateForm() {
        intent.putExtra(MODE, FormMode.UPDATE)
        startIntent()
    }

    private fun startIntent() {
        context.startActivity(intent)
    }
}

enum class FormMode {
    CREATE,
    UPDATE
}

class DiseaseRoute(context: Context, private val id: Int = -1) : FormRoute<Disease>(context) {

    override val entity = Disease()

    override fun prepareIntent(intent: Intent): Intent =
        intent.apply {
            putExtra(ID, id)
            putExtra(ENTITY, Disease())
        }
}