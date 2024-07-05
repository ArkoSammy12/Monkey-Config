package xd.arkosammy.monkeyconfig.settings

import xd.arkosammy.monkeyconfig.MonkeyConfig
import xd.arkosammy.monkeyconfig.types.NumberType
import xd.arkosammy.monkeyconfig.util.SettingLocation

open class NumberSetting<T : Number> @JvmOverloads constructor(
    settingLocation: SettingLocation,
    comment: String? = null,
    defaultValue: T,
    value: T = defaultValue,
    private val lowerBound: T? = null,
    private val upperBound: T? = null
) : ConfigSetting<T, NumberType<T>>(settingLocation, comment, defaultValue, value) {

    override var value: T
        get() = super.value
        set(value) {
            if (lowerBound != null && value < this.lowerBound) {
                MonkeyConfig.LOGGER.error("Value $value for setting ${this.settingLocation} is below the lower bound!")
                return
            }
            if (this.upperBound != null && value > this.upperBound) {
                MonkeyConfig.LOGGER.error("Value $value for setting ${this.settingLocation} is above the upper bound!")
                return
            }
            super.value = convertNumberToTypeT(value)
        }

    override val valueToSerializedConverter: (T) -> NumberType<T>
        get() = { number -> NumberType(number) }

    override val serializedToValueConverter: (NumberType<T>) -> T
        get() = { numberType -> numberType.rawValue }

    // The following unchecked cast is safe under the assumption
    // that T is always one of the numerical data types shown in this "when"
    // expression.
    // For instance, if the default value of this setting is of type "Double", and "number" is an Int,
    // then the "Double" branch should be taken, converting the number to a Double,
    // which should then be successfully casted to T, which in this case is Double.
    @Suppress("UNCHECKED_CAST")
    private fun convertNumberToTypeT(number: Number): T =
         when (this.defaultValue) {
            is Byte -> number.toByte()
            is Short -> number.toShort()
            is Int -> number.toInt()
            is Long -> number.toLong()
            is Float -> number.toFloat()
            is Double -> number.toDouble()
            else -> throw IllegalArgumentException("Unsupported number type: ${defaultValue::class.java}")
         } as T

    override fun toString(): String =
        "${this::class.simpleName}{numType=${this.value::class.simpleName}, location=${this.settingLocation}, comment=${this.comment ?: "null"}, defaultValue=${this.defaultValue}}, value=${this.value}, serializedType=${this.serializedDefaultValue::class.simpleName}, lowerBound=${this.lowerBound ?: "null"}, upperBound=${this.upperBound ?: "null"}}"

    open class Builder<T : Number> @JvmOverloads constructor(
        settingLocation: SettingLocation,
        comment: String? = null,
        defaultValue: T
    ) : ConfigSetting.Builder<NumberSetting<T>, T, NumberType<T>>(settingLocation, comment, defaultValue) {

        protected var lowerBound: T? = null
        protected var upperBound: T? = null

        fun withLowerBound(lowerBound: T): Builder<T> {
            this.lowerBound = lowerBound
            return this
        }

        fun withUpperBound(upperBound: T): Builder<T> {
            this.upperBound = upperBound
            return this
        }

        override fun build(): NumberSetting<T> =
            NumberSetting(settingLocation, this.comment, defaultValue, defaultValue, lowerBound, upperBound)

    }

}

operator fun Number.compareTo(other: Number) : Int {
    val thisAsDouble: Double = this.toDouble()
    val otherAsDouble: Double = other.toDouble()
    return when {
        thisAsDouble > otherAsDouble -> 1
        thisAsDouble < otherAsDouble -> -1
        else -> 0
    }
}
