package ru.chronicker.rsubd.ui.screens.main.fragments.primitive

import android.util.Log
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_list_of_double_items.*
import ru.chronicker.rsubd.EMPTY_INT
import ru.chronicker.rsubd.EMPTY_STRING
import ru.chronicker.rsubd.database.base.Entity
import ru.chronicker.rsubd.database.base.IntValue
import ru.chronicker.rsubd.database.base.View
import ru.chronicker.rsubd.ui.base.AloneItemModel
import ru.chronicker.rsubd.ui.base.BaseFragment
import ru.chronicker.rsubd.ui.base.DoubleItemModel
import ru.chronicker.rsubd.ui.base.ItemModel
import ru.chronicker.rsubd.ui.screens.form.LazyRoute

abstract class PrimitiveFragmentView<M : Entity, IM: ItemModel, R : LazyRoute<M>>(
    override val screenName: String = EMPTY_STRING,
    override val entity: M,
    private val route: R
) : BaseFragment<M, IM>() {

    override fun initViews() {
        fab.setOnClickListener {
            openCreateForm()
        }
        if (entity is View) {
            fab.isVisible = false
        }
        initList(isSwipeToDeleteEnabled = true)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    /**
     * Метод преобразования полученных данных в заголовки элементов списка
     */
    abstract fun convertToTitle(values: List<Pair<String, String>>): String

    abstract fun provideAdapter(): PrimitiveAdapter<IM>

    protected fun openUpdateForm(id: Int) {
        route.updateForm(context = requireContext(), id = id)
    }

    private fun initList(isSwipeToDeleteEnabled: Boolean) {
        setListAdapter(
            adapter = provideAdapter(),
            isSwipeToDeleteEnabled = isSwipeToDeleteEnabled
        )
    }

    private fun openCreateForm() {
        route.createForm(context = requireContext())
    }
}

abstract class PrimitiveAloneFragmentView<M : Entity, R : LazyRoute<M>>(
    override val screenName: String = EMPTY_STRING,
    override val entity: M,
    val route: R
) : PrimitiveFragmentView<M, AloneItemModel, R>(entity = entity, route = route, screenName = screenName){

    override fun provideAdapter(): PrimitiveAdapter<AloneItemModel> {
        return PrimitiveAloneAdapter(
            onClickListener = { id ->
                openUpdateForm(id)
            },
            onDeleteListener = { id ->
                dbHelper.delete(entity, listOf(IntValue(id)), {}, {println("DELETED $id")})
            }
        )
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): AloneItemModel {
        return AloneItemModel(
            id = values.find { it.first == "ID" }?.second?.toInt() ?: EMPTY_INT,
            title = convertToTitle(values)
        )
    }
}

abstract class PrimitiveDoubleFragmentView<M : Entity, R : LazyRoute<M>>(
    override val screenName: String = EMPTY_STRING,
    override val entity: M,
    route: R
) : PrimitiveFragmentView<M, DoubleItemModel, R>(entity = entity, route = route, screenName = screenName){

    override fun provideAdapter(): PrimitiveAdapter<DoubleItemModel> {
        return PrimitiveDoubleAdapter(
            onClickListener = { id ->
                openUpdateForm(id)
            },
            onDeleteListener = { id ->
                dbHelper.delete(entity, listOf(IntValue(id)), {}, {println("DELETED $id")})
            }
        )
    }

    override fun convertToItemModel(values: List<Pair<String, String>>): DoubleItemModel {
        return DoubleItemModel(
            id = values.find { it.first == "ID" }?.second?.toInt() ?: EMPTY_INT,
            title = convertToTitle(values),
            subtitle = provideSubtitle(values)
        )
    }

    abstract fun provideSubtitle(values: List<Pair<String, String>>): String
}