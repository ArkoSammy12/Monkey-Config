package xd.arkosammy.monkeyconfig.settings

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import xd.arkosammy.monkeyconfig.commands.CommandControllableSetting
import xd.arkosammy.monkeyconfig.types.StringType
import xd.arkosammy.monkeyconfig.util.SettingIdentifier

open class StringSetting @JvmOverloads constructor(
    settingIdentifier: SettingIdentifier,
    comment: String? = null,
    defaultValue: String,
    value: String = defaultValue) : ConfigSetting<String, StringType>(settingIdentifier, comment, value), CommandControllableSetting<String, StringArgumentType> {

    override val serializedValue: StringType
        get() = StringType(this.value)

    override val serializedDefaultValue: StringType
        get() = StringType(this.defaultValue)

    override fun setValueFromSerialized(serializedValue: StringType) {
        this.value = serializedValue.value
    }

    override val commandIdentifier: SettingIdentifier
        get() = this.settingIdentifier

    override fun getArgumentValue(ctx: CommandContext<ServerCommandSource>, argumentName: String): String {
        return StringArgumentType.getString(ctx, argumentName)
    }

    override val argumentType: StringArgumentType
        get() = StringArgumentType.string()

    class Builder @JvmOverloads constructor(id: SettingIdentifier, comment: String? = null, defaultValue: String) : ConfigSetting.Builder<String, StringType, StringSetting>(id, comment, defaultValue) {

        override fun build(): StringSetting {
            return StringSetting(this.id, this.comment, this.defaultValue)
        }

    }

}