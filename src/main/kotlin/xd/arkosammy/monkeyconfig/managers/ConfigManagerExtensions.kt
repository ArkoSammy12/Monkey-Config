package xd.arkosammy.monkeyconfig.managers

import xd.arkosammy.monkeyconfig.settings.*
import xd.arkosammy.monkeyconfig.settings.list.StringListSetting
import xd.arkosammy.monkeyconfig.util.SettingLocation

@JvmName("ConfigManagerExtensions")

inline fun <V, reified T : ConfigSetting<V, *>> ConfigManager.getTypedSetting(settingLocation: SettingLocation) : T? {
    return this.getTypedSetting(settingLocation, T::class.java)
}

@Throws(IllegalArgumentException::class)
inline fun <V : Any, reified T : ConfigSetting<V, *>> ConfigManager.getSettingValue(settingLocation: SettingLocation) : V {
    return this.getSettingValue(settingLocation, T::class.java)
}

@Throws(IllegalArgumentException::class)
fun <V : Any, T : ConfigSetting<V, *>> ConfigManager.getSettingValue(settingLocation: SettingLocation, settingClass: Class<T>) : V {
    val setting: ConfigSetting<V, *>? = this.getTypedSetting(settingLocation, settingClass)
    return setting?.value ?: throw IllegalArgumentException("No setting of type ${settingClass.simpleName} with location $settingLocation was found for ConfigManager with config file ${this.configName}")
}

@Throws(IllegalArgumentException::class)
fun <E : Enum<E>> ConfigManager.getEnumSettingValue(settingLocation: SettingLocation) : E {
    val setting: EnumSetting<E>? = this.getAsEnumSetting(settingLocation)
    return setting?.value ?: throw IllegalArgumentException("No enum setting with location $settingLocation for ConfigManager with config file ${this.configName}")
}

fun ConfigManager.getAsIntSetting(settingLocation: SettingLocation) : NumberSetting<Int>? = this.getTypedSetting<Int, NumberSetting<Int>>(settingLocation)

fun ConfigManager.getAsDoubleSetting(settingLocation: SettingLocation) : NumberSetting<Double>? = this.getTypedSetting<Double, NumberSetting<Double>>(settingLocation)

fun ConfigManager.getAsBooleanSetting(settingLocation: SettingLocation) : BooleanSetting? = this.getTypedSetting<Boolean, BooleanSetting>(settingLocation)

fun ConfigManager.getAsStringSetting(settingLocation: SettingLocation) : StringSetting? = this.getTypedSetting<String, StringSetting>(settingLocation)

fun ConfigManager.getAsStringListSetting(settingLocation: SettingLocation) : StringListSetting? = this.getTypedSetting<List<String>, StringListSetting>(settingLocation)

fun <E : Enum<E>> ConfigManager.getAsEnumSetting(settingLocation: SettingLocation) : EnumSetting<E>? = this.getTypedSetting<E, EnumSetting<E>>(settingLocation)