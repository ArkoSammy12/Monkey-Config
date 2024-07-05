package xd.arkosammy.monkeyconfig.settings

import xd.arkosammy.monkeyconfig.types.BooleanType
import xd.arkosammy.monkeyconfig.util.SettingLocation

open class BooleanSetting @JvmOverloads constructor(
    settingLocation: SettingLocation,
    comment: String? = null,
    defaultValue: Boolean,
    value: Boolean = defaultValue
) : ConfigSetting<Boolean, BooleanType>(settingLocation, comment, defaultValue, value) {

    override val valueToSerializedConverter: (Boolean) -> BooleanType
        get() = { boolean -> BooleanType(boolean) }

    override val serializedToValueConverter: (BooleanType) -> Boolean
        get() = { booleanType -> booleanType.rawValue }

    open class Builder @JvmOverloads constructor(
        settingLocation: SettingLocation,
        comment: String? = null,
        defaultValue: Boolean
    ) : ConfigSetting.Builder<BooleanSetting, Boolean, BooleanType>(settingLocation, comment, defaultValue) {

        override fun build(): BooleanSetting = BooleanSetting(this.settingLocation, this.comment, this.defaultValue)

    }
}