package ru.chronicker.rsubd.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.chronicker.rsubd.EMPTY_LONG
import ru.chronicker.rsubd.EMPTY_REAL
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField
import ru.chronicker.rsubd.database.views.PatientView
import ru.chronicker.rsubd.databinding.FragmentListOfDoubleItemsBinding
import ru.chronicker.rsubd.interactor.UserRole
import ru.chronicker.rsubd.utils.storage.ConfigurationStorage

abstract class BaseFragment<T : Entity, M : ItemModel> : Fragment() {

    abstract val screenName: String
    abstract val entity: T

    abstract fun initViews()
    abstract fun convertToItemModel(values: List<Pair<String, String>>): M

    open fun shouldPlusBeEnabledForDoctor(): Boolean = false

    val layoutId: Int = R.layout.fragment_list_of_double_items
    private lateinit var binding : FragmentListOfDoubleItemsBinding

    private var currentId: Int = 0
    private var adapter: BaseAdapter<M>? = null
        set(value) {
            field = value
            binding.itemsRv.adapter = value
        }

    protected lateinit var dbHelper: DBHelper
    protected lateinit var configurationStorage: ConfigurationStorage
    var plusEnabled: Boolean = false
        set(value) {
            field = value
            binding.fab.isGone = !value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfDoubleItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            dbHelper = DBHelper(it)
            configurationStorage =
                ConfigurationStorage(it.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE))
            plusEnabled =
                configurationStorage.userRole == UserRole.Admin || (configurationStorage.userRole == UserRole.Doctor && shouldPlusBeEnabledForDoctor())
        }
        initViews()
    }

    fun setListAdapter(adapter: BaseAdapter<M>, isSwipeToDeleteEnabled: Boolean) {
        this.adapter = adapter
        if (isSwipeToDeleteEnabled) {
            enableSwipeToDeleteAndUndo()
        }
    }

    fun loadData() {
        (
                if (entity is PatientView
                    && configurationStorage.userRole in setOf(
                        UserRole.Patient,
                        UserRole.Undefined
                    )
                ) {
                    dbHelper.select(entity, mapOf("ID" to configurationStorage.id))
                } else {
                    dbHelper.select(entity)
                }
                )
            .also {
                currentId = it.size + 1
            }
            .map { result ->
                result.fields
                    .map { (field, value) ->
                        field.name to getValue(value, field)
                    }
            }
            .map { pair ->
                convertToItemModel(pair)
            }
            .let {
                adapter?.setItems(it)
            }
    }

    private fun getValue(value: Any?, field: Field): String {
        if (field is ForeignKeyField) {
            val entity = dbHelper.getEntityByName(field.foreignTable)
            return (
                    if (entity is PatientView
                        && configurationStorage.userRole in setOf(
                            UserRole.Patient,
                            UserRole.Undefined
                        )
                    ) {
                        dbHelper.select(field.foreignTable, mapOf("ID" to configurationStorage.id))
                    } else {
                        dbHelper.select(field.foreignTable)
                    }
                    ).asSequence()
                .map { it.fields }
                .filter { list ->
                    list.find { it.second == value } != null
                }
                .firstOrNull()
                ?.let { entity?.deepConvertToString(it, dbHelper) }
                ?: EMPTY_STRING
        }
        return when (field.type) {
            FieldType.TEXT -> transformToText(value)
            FieldType.INTEGER -> transformToLong(value).toString()
            FieldType.REAL -> transformToReal(value).toString()
            else -> EMPTY_STRING
        }
    }

    private fun transformToText(value: Any?): String {
        return EMPTY_STRING.takeIf { value == null }
            ?: value as String
    }

    private fun transformToLong(value: Any?): Long {
        return EMPTY_LONG.takeIf { value == null }
            ?: value as Long
    }

    private fun transformToReal(value: Any?): Float {
        return EMPTY_REAL.takeIf { value == null }
            ?: value as Float
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                adapter?.deleteItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        if (plusEnabled) {
            itemTouchHelper.attachToRecyclerView(binding.itemsRv)
        }
    }
}