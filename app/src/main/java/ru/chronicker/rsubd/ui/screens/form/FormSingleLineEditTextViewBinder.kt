package com.thejuki.kformmaster.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.state.FormEditTextViewState

/**
 * Form SingleLine EditText ViewBinder
 *
 * View Binder for [FormSingleLineLockableEditTextElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormSingleLineLockableEditTextViewBinder(
    private val context: Context,
    private val formBuilder: FormBuildHelper, @LayoutRes private val layoutID: Int?
) : BaseFormViewBinder() {
    val viewBinder = ViewBinder(layoutID
        ?: R.layout.form_element,
        FormSingleLineLockableEditTextElement::class.java,
        { model, finder, _ ->
            val textViewTitle = finder.find(R.id.formElementTitle) as? AppCompatTextView
            val mainViewLayout = finder.find(R.id.formElementMainLayout) as? LinearLayout
            val textViewError = finder.find(R.id.formElementError) as? AppCompatTextView
            val dividerView = finder.find(R.id.formElementDivider) as? View
            val itemView = finder.getRootView() as View
            baseSetup(model, dividerView, textViewTitle, textViewError, itemView, mainViewLayout)

            val editTextValue =
                finder.find(R.id.formElementValue) as com.thejuki.kformmaster.widget.ClearableEditText

            editTextValue.setText(model.valueAsString)
            editTextValue.hint = model.hint ?: ""

            model.editView = editTextValue

            // Single Line
            editTextValue.maxLines = 1

            // If an InputType is provided, use it instead
            model.inputType?.let { editTextValue.setRawInputType(it) }

            // If imeOptions are provided, use them instead of actionNext
            model.imeOptions?.let { editTextValue.imeOptions = it }

            setEditTextFocusEnabled(editTextValue, itemView)
            setOnFocusChangeListener(context, model, formBuilder)
            addTextChangedListener(model, formBuilder)
            setOnEditorActionListener(model, formBuilder)
            setClearableListener(model)

        },
        object : ViewStateProvider<FormSingleLineLockableEditTextElement, ViewHolder> {
            override fun createViewStateID(model: FormSingleLineLockableEditTextElement): Int {
                return model.id
            }

            override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
                return FormEditTextViewState(holder)
            }
        })

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            if (itemView.isFocusable) {
                editTextValue.requestFocus()
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                editTextValue.setSelection(editTextValue.text?.length ?: 0)
                imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}

class FormSingleLineLockableEditTextElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    override var enabled: Boolean = true
        set(value) {
            field = value
            itemView?.isEnabled = value
            titleView?.isEnabled = value
            editView?.isEnabled = value
            editView?.isFocusable = value

            onEnabled(value)
        }

    override fun onEnabled(enable: Boolean) {
        super.onEnabled(enable)
        if (!enable) {
            editView?.clearFocus()
        }
    }
}

/** FormBuildHelper extension to add a FormSingleLineEditTextElement */
fun FormBuildHelper.lockableField(
    tag: Int = -1,
    init: FormSingleLineLockableEditTextElement.() -> Unit
): FormSingleLineLockableEditTextElement {
    return addFormElement(FormSingleLineLockableEditTextElement(tag).apply(init))
}