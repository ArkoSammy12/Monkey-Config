package xd.arkosammy.monkeyconfig.tables

abstract class AbstractConfigTable @JvmOverloads constructor(
    override val name: String,
    override val comment: String? = null,
    override val loadBeforeSave: Boolean = false,
    override val registerSettingsAsCommands: Boolean) : ConfigTable {

    protected var _isRegistered: Boolean = false
    override val isRegistered: Boolean
        get() = this._isRegistered

    override fun setAsRegistered() {
        this._isRegistered = true
    }

    override fun toString(): String {
        return "${this::class.simpleName}{name=${this.name}, comment=${this.comment ?: "null"}, settingAmount=${this.configSettings.size}, registered=$isRegistered, loadBeforeSave=$loadBeforeSave, registerSettingsAsCommands=$registerSettingsAsCommands}"
    }

}