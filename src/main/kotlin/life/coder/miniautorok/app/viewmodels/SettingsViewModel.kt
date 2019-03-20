package life.coder.miniautorok.app.viewmodels

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

data class AppSettings(val androidSdk: String)

class AppSettingsModel: ItemViewModel<AppSettings>() {

    val KEY_ANDROID_SDK = "androidsdk"

    val androidSdk = bind { SimpleStringProperty(item?.androidSdk, "", config.string(KEY_ANDROID_SDK)) }

    override fun onCommit() {
        with(config) {
            set(KEY_ANDROID_SDK to androidSdk.value)
            save()
        }
    }
}