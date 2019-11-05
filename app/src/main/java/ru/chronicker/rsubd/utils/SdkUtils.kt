package ru.chronicker.rsubd.utils

import android.os.Build

/**
 * Утилиты для проверки версии Api
 */
object SdkUtils {

    fun isPreLollipop(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP

    fun isAtLeastLollipop(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    fun isAtLeastMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isAtLeastNougat(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    fun isAtLeastOreo(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun isAtLeastPie(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    /**
     * Запускает блок кода на устройствах с андроид версии KitKat и ниже
     *
     * @param block запускаемый блок кода
     */
    fun runOnPreLollipop(block: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            block()
        }
    }

    /**
     * Запускает блок кода на устройствах с андроид версии Lollipop и выше
     *
     * @param block запускаемый блок кода
     */
    fun runOnLollipop(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            block()
        }
    }

    /**
     * Запускает блок кода на устройствах с андроид версии Marshmallow и выше
     *
     * @param block запускаемый блок кода
     */
    fun runOnMarshmallow(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            block()
        }
    }

    /**
     * Запускает блок кода на устройствах с андроид версии Noughat и выше
     *
     * @param block запускаемый блок кода
     */
    fun runOnNoughat(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            block()
        }
    }

    /**
     * Запускает блок кода на устройствах с андроид версии Oreo и выше
     *
     * @param block запускаемый блок кода
     */
    fun runOnOreo(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            block()
        }
    }

    /**
     * Запускает блок кода на устройствах с андроид версии Pie и выше
     *
     * @param block запускаемый блок кода
     */
    fun runOnPie(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            block()
        }
    }

    /**
     * Запускает ту, или иную лямбду в зависимости какая версия api на устройстве
     */
    fun doIfSdk(atLeast: Boolean, ifTrue: () -> Unit, ifFalse: () -> Unit) =
        if (atLeast) ifTrue() else ifFalse()

    //region Deprecated
    @Deprecated(
        message = "Необходимо заменить на метод SdkUtils.isPreLollipop()",
        replaceWith = ReplaceWith("Sdk.isPreLollipop()")
    )
    val isPreLollipop: Boolean
        @JvmName("isPreLollipopDeprecated")
        get() = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP

    @Deprecated(
        message = "Необходимо заменить на SdkUtils.isAtLeastLollipop()",
        replaceWith = ReplaceWith("SdkUtils.isAtLeastLollipop()")
    )
    val isAtLeastLollipop: Boolean
        @JvmName("isAtLeastLollipopDeprecated")
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    @Deprecated(
        message = "Необходимо заменить на SdkUtils.isAtLeastMarshmallow()",
        replaceWith = ReplaceWith("SdkUtils.isAtLeastMarshmallow()")
    )
    val isAtLeastMarshmallow: Boolean
        @JvmName("isAtLeastMarshmallowDeprecated")
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @Deprecated(
        message = "Необходимо заменить на SdkUtils.isAtLeastNougat()",
        replaceWith = ReplaceWith("SdkUtils.isAtLeastNougat()")
    )
    val isAtLeastNougat: Boolean
        @JvmName("isAtLeastNougatDeprecated")
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @Deprecated(
        message = "Необходимо заменить на SdkUtils.isAtLeastOreo()",
        replaceWith = ReplaceWith("SdkUtils.isAtLeastOreo()")
    )
    val isAtLeastOreo: Boolean
        @JvmName("isAtLeastOreoDeprecated")
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    //endregion
}
