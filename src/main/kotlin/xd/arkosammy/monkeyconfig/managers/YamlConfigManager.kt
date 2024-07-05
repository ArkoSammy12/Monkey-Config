package xd.arkosammy.monkeyconfig.managers

import com.electronwill.nightconfig.yaml.YamlFormat
import xd.arkosammy.monkeyconfig.settings.ConfigSetting
import xd.arkosammy.monkeyconfig.groups.SettingGroup
import xd.arkosammy.monkeyconfig.groups.MutableSettingGroup
import java.nio.file.Path

// Internal class as it's not ready to be used yet
internal class YamlConfigManager : AbstractConfigManager {

    constructor(
        configName: String,
        settingGroups: List<SettingGroup>,
        configPath: Path
    ) : super(configName, settingGroups, YamlFormat.defaultInstance(), configPath.resolve("$configName.yaml"))

    constructor(
        configName: String,
        settingGroups: List<MutableSettingGroup>? = null,
        settingBuilders: List<ConfigSetting.Builder<*, *, *>>,
        configPath: Path
    ) : super(configName, settingGroups, settingBuilders, YamlFormat.defaultInstance(), configPath.resolve("$configName.yaml"))

}