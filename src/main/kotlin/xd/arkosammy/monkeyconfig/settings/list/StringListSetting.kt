package xd.arkosammy.monkeyconfig.settings.list

import xd.arkosammy.monkeyconfig.settings.ListSetting
import xd.arkosammy.monkeyconfig.types.ListType
import xd.arkosammy.monkeyconfig.types.StringType
import xd.arkosammy.monkeyconfig.util.SettingIdentifier

open class StringListSetting(
    settingIdentifier: SettingIdentifier,
    comment: String? = null,
    defaultValue: List<String>,
    value: List<String> = defaultValue) : ListSetting<String, StringType>(settingIdentifier, comment, defaultValue, value) {

    override val valueAsSerialized: ListType<StringType>
        get() = ListType(this.value.toList().map { e -> StringType(e) })

    override val defaultValueAsSerialized: ListType<StringType>
        get() = ListType(this.defaultValue.toList().map { e -> StringType(e) })

    override fun setFromSerializedValue(serializedValue: ListType<StringType>) {
        this.value = serializedValue.value.toList().map { e -> e.value }
    }

    class Builder(id: SettingIdentifier, comment: String? = null, defaultValue: List<String>) : ListSetting.Builder<String, StringType>(id, comment, defaultValue) {

        override fun build(): StringListSetting {
            return StringListSetting(this.id, this.comment, this.defaultValue)
        }

    }

}