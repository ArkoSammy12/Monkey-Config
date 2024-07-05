package xd.arkosammy.monkeyconfig.settings

import xd.arkosammy.monkeyconfig.types.StringType
import xd.arkosammy.monkeyconfig.util.SettingLocation

open class StringSetting @JvmOverloads constructor(
    settingLocation: SettingLocation,
    comment: String? = null,
    defaultValue: String,
    value: String = defaultValue
) : ConfigSetting<String, StringType>(settingLocation, comment, value) {

    override val valueToSerializedConverter: (String) -> StringType
        get() = { string -> StringType(string) }

    override val serializedToValueConverter: (StringType) -> String
        get() = { stringType -> stringType.rawValue }

    open class Builder @JvmOverloads constructor(
        id: SettingLocation,
        comment: String? = null,
        defaultValue: String
    ) : ConfigSetting.Builder<StringSetting, String, StringType>(id, comment, defaultValue) {

        override fun build(): StringSetting =
            StringSetting(this.settingLocation, this.comment, this.defaultValue)

    }

}