package xd.arkosammy.monkeyconfig.managers

import com.electronwill.nightconfig.hocon.HoconFormat
import xd.arkosammy.monkeyconfig.settings.ConfigSetting
import xd.arkosammy.monkeyconfig.groups.SettingGroup
import xd.arkosammy.monkeyconfig.groups.MutableSettingGroup
import java.nio.file.Path

open class HoconConfigManager : AbstractConfigManager {

    constructor(
        configName: String,
        settingGroups: List<SettingGroup>,
        configPath: Path
    ) : super(configName, settingGroups, HoconFormat.instance(), configPath.resolve("$configName.conf"))

    constructor(
        configName: String,
        settingGroups: List<MutableSettingGroup>? = null,
        settingBuilders: List<ConfigSetting.Builder<*, *, *>>,
        configPath: Path
    ) : super(configName, settingGroups, settingBuilders, HoconFormat.instance(), configPath.resolve("$configName.conf"))

}