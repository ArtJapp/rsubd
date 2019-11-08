package ru.chronicker.rsubd.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

private const val DEFAULT_DARK_COLOR_RATIO = 0.7

/**
 * Определяет, является ли цвет темным относительно коэфициента
 */
fun isDarkColor(@ColorInt color: Int, darkRatio: Double = DEFAULT_DARK_COLOR_RATIO) =
    ColorUtils.calculateLuminance(color).isSmallerThan(darkRatio)

/**
 * Красит статус бар в нужный цвет.
 * Работает с 21 SDK.
 * Так же, красит иконки статус-бара в серый цвет, если SDK >= 23 и устанавливаемый цвет слишком белый,
 * и красит иконки статус-бара в белый цвет, если устанавливаемый цвет темный
 */
@SuppressLint("InlinedApi")
fun Activity.setStatusBarColor(@ColorInt color: Int) {
    SdkUtils.runOnLollipop {
        window.statusBarColor = color

        SdkUtils.runOnMarshmallow {
            window.decorView.systemUiVisibility =
                if (!isDarkColor(color))
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else
                    0
        }
    }
}