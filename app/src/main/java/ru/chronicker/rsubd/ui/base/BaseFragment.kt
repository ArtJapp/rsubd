package ru.chronicker.rsubd.ui.base

import androidx.fragment.app.Fragment
import ru.chronicker.rsubd.EMPTY_STRING

abstract class BaseFragment : Fragment() {

    fun getName(): String = EMPTY_STRING
}