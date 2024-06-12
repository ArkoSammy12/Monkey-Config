package xd.arkosammy.monkeyconfig.settings

import com.mojang.brigadier.arguments.*
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import xd.arkosammy.monkeyconfig.MonkeyConfig
import xd.arkosammy.monkeyconfig.commands.CommandControllableSetting
import xd.arkosammy.monkeyconfig.types.NumberType
import xd.arkosammy.monkeyconfig.util.SettingIdentifier
import kotlin.jvm.Throws
import kotlin.math.max
import kotlin.math.min

open class NumberSetting<T : Number> @JvmOverloads constructor(
    settingIdentifier: SettingIdentifier,
    comment: String? = null,
    defaultValue: T,
    value: T = defaultValue,
    private val lowerBound: T? = null,
    private val upperBound: T? = null) : ConfigSetting<T, NumberType<T>>(settingIdentifier, comment, defaultValue, value), CommandControllableSetting<T, ArgumentType<T>> {

    override var value: T
        get() = super.value
        set(value) {
            if (lowerBound != null && value < this.lowerBound) {
                MonkeyConfig.LOGGER.error("Value $value for setting ${this.settingIdentifier} is below the lower bound!")
                return
            }
            if (this.upperBound != null && value > this.upperBound) {
                MonkeyConfig.LOGGER.error("Value $value for setting ${this.settingIdentifier} is above the upper bound!")
                return
            }
            super.value = value
        }

    override fun setValueFromSerialized(serializedValue: NumberType<T>) {
        this.value = serializedValue.value
    }

    override val commandIdentifier: SettingIdentifier
        get() = settingIdentifier

    override val serializedValue: NumberType<T>
        get() = NumberType(this.value)

    override val serializedDefaultValue: NumberType<T>
        get() = NumberType(this.defaultValue)

    override val argumentType: ArgumentType<T>
        @Throws(IllegalArgumentException::class)
        get() = when (this.defaultValue) {
            is Byte -> getIntegerArgumentType()
            is Short -> getIntegerArgumentType()
            is Int -> getIntegerArgumentType()
            is Long -> getLongArgumentType()
            is Float -> getFloatArgumentType()
            is Double -> getDoubleArgumentType()
            else -> throw IllegalArgumentException("Unsupported number type: ${this.defaultValue::class.java}")
        }

    // The following unsafe cast operations in the getNumArgumentType methods are done under the assumption
    // that the primitive type parameter of this object can provide a corresponding ArgumentType<T>,
    // where T is one of the numerical data types.
    // The numerical argument types such as IntegerArgumentType are also assumed to implement ArgumentType<Integer> directly.
    // For example, if the type parameter of this class is Double,
    // then the branch taken in this method should result in a DoubleArgumentType being returned.
    // DoubleArgumentType implements ArgumentType<Double>, so the cast should be successful.
    @Suppress("UNCHECKED_CAST")
    private fun getIntegerArgumentType(): ArgumentType<T> {
        val min = max(Byte.MIN_VALUE.toInt(), lowerBound?.toInt() ?: Byte.MIN_VALUE.toInt())
        val max = min(Byte.MAX_VALUE.toInt(), upperBound?.toInt() ?: Byte.MAX_VALUE.toInt())
        return IntegerArgumentType.integer(min, max) as ArgumentType<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getLongArgumentType(): ArgumentType<T> {
        val min = max(Long.MIN_VALUE, lowerBound?.toLong() ?: Long.MIN_VALUE)
        val max = min(Long.MAX_VALUE, upperBound?.toLong() ?: Long.MAX_VALUE)
        return LongArgumentType.longArg(min, max) as ArgumentType<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getFloatArgumentType(): ArgumentType<T> {
        val min = max(Float.MIN_VALUE, lowerBound?.toFloat() ?: Float.MIN_VALUE)
        val max = min(Float.MAX_VALUE, upperBound?.toFloat() ?: Float.MAX_VALUE)
        return FloatArgumentType.floatArg(min, max) as ArgumentType<T>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getDoubleArgumentType(): ArgumentType<T> {
        val min = max(Double.MIN_VALUE, lowerBound?.toDouble() ?: Double.MIN_VALUE)
        val max = min(Double.MAX_VALUE, upperBound?.toDouble() ?: Double.MAX_VALUE)
        return DoubleArgumentType.doubleArg(min, max) as ArgumentType<T>
    }

    // TODO: Test this
    @Suppress("UNCHECKED_CAST")
    override fun getArgumentValue(ctx: CommandContext<ServerCommandSource>, argumentName: String): T {
        // The following unsafe cast operations are done under the assumption
        // that the type parameter of this object matches the argument type provided by this instance.
        // For example, a NumberSetting<Int> should return an IntegerArgumentType.
        // Similarly, this method should retrieve the argument value by using the IntegerArgumentType,
        // which will return an integer, which should be safely casted to Int
        return when (this.defaultValue) {
            is Byte, is Short, is Int -> IntegerArgumentType.getInteger(ctx, argumentName) as T
            is Long -> LongArgumentType.getLong(ctx, argumentName) as T
            is Float -> FloatArgumentType.getFloat(ctx, argumentName) as T
            is Double -> DoubleArgumentType.getDouble(ctx, argumentName) as T
            else -> throw IllegalArgumentException("Unsupported number type: ${this.defaultValue::class.java}")
        }
    }

    override fun toString(): String {
        return "${this::class.simpleName}{numType=${this.value::class.simpleName}, id=${this.settingIdentifier}, comment=${this.comment ?: "null"}, defaultValue=${this.defaultValue}}, value=${this.value}, serializedType=${this.serializedDefaultValue::class.simpleName}, lowerBound=${this.lowerBound ?: "null"}, upperBound=${this.upperBound ?: "null"}}"
    }

    class Builder<T : Number> @JvmOverloads constructor(id: SettingIdentifier, comment: String? = null, defaultValue: T) : ConfigSetting.Builder<T, NumberType<T>, NumberSetting<T>>(id, comment, defaultValue) {

        private var lowerBound: T? = null
        private var upperBound: T? = null

        fun withLowerBound(lowerBound: T): Builder<T> {
            this.lowerBound = lowerBound
            return this
        }

        fun withUpperBound(upperBound: T): Builder<T> {
            this.upperBound = upperBound
            return this
        }

        override fun build(): NumberSetting<T> {
            return NumberSetting(id, this.comment, defaultValue, defaultValue, lowerBound, upperBound)
        }

    }

}

operator fun Number.compareTo(other: Number) : Int {
    return when {
        this.toDouble() > other.toDouble() -> 1
        this.toDouble() < other.toDouble() -> -1
        else -> 0
    }
}
