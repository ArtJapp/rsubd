package ru.chronicker.rsubd.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.R
import ru.chronicker.rsubd.database.DBHelper
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.Field
import ru.chronicker.rsubd.database.base.FieldType
import ru.chronicker.rsubd.database.base.ForeignKeyField

abstract class BaseFragment<T : Entity, M : ItemModel> : Fragment() {

    abstract val screenName: String
    abstract val entity: T

    abstract fun initViews()
    abstract fun convertToItemModel(values: List<Pair<String, String>>): M

    val layoutId: Int = R.layout.fragment_list_of_double_items

    private var currentId: Int = 0
    private var adapter: BaseAdapter<M>? = null
        set(value) {
            field = value
            items_rv.adapter = value
        }

    protected lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            dbHelper = DBHelper(it)
        }
        initViews()
    }

    fun setListAdapter(adapter: BaseAdapter<M>) {
        this.adapter = adapter
        enableSwipeToDeleteAndUndo()
    }

    fun loadData() {
        dbHelper.select(entity)
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

    private fun getValue(value: Any, field: Field): String {
        if (field is ForeignKeyField) {
            val entity = dbHelper.getEntityByName(field.foreignTable)
            return dbHelper.select(field.foreignTable)
                .asSequence()
                .map { it.fields }
                .filter { list ->
                    list.find { it.second == value } != null
                }
                .firstOrNull()
                ?.let { entity?.convertToString(it) }
                ?: EMPTY_STRING
        }
        return when (field.type) {
            FieldType.TEXT -> value as String
            FieldType.INTEGER -> (value as Long).toString()
            FieldType.REAL -> (value as Float).toString()
            else -> EMPTY_STRING
        }
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                adapter?.deleteItem(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(items_rv)
    }
}