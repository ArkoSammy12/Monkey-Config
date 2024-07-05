package xd.arkosammy.monkeyconfig.managers

import com.electronwill.nightconfig.json.JsonFormat
import xd.arkosammy.monkeyconfig.settings.ConfigSetting
import xd.arkosammy.monkeyconfig.groups.SettingGroup
import xd.arkosammy.monkeyconfig.groups.MutableSettingGroup
import java.nio.file.Path

open class JsonConfigManager : AbstractConfigManager {

    constructor(
        configName: String,
        settingGroups: List<SettingGroup>,
        configPath: Path
    ) : super(configName, settingGroups, JsonFormat.fancyInstance(), configPath.resolve("$configName.json"))

    constructor(
        configName: String,
        settingGroups: List<MutableSettingGroup>? = null,
        settingBuilders: List<ConfigSetting.Builder<*, *, *>>,
        configPath: Path
    ) : super(configName, settingGroups, settingBuilders, JsonFormat.fancyInstance(), configPath.resolve("$configName.json"))

}