package life.coder.miniautorok.app.scenes

import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import org.sikuli.android.ADBClient
import tornadofx.*

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Settings

class MainView : View("Mini Auto: RoK") {

    val resourceTypes = FXCollections.observableArrayList("Food",
            "Wood", "Stone", "Gold")
    val resourceLevels = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6)

    var devices = FXCollections.emptyObservableList<String>()

    init {
        Settings.DebugLogs = true
        val adbScreen = ADBScreen("D:\\Program Files\\Nox\\bin\\nox_adb.exe")

        ADBClient.get

        devices = FXCollections.observableArrayList(adbScreen.devices.map { it.toString() })
        print(adbScreen.devices)
        print(adbScreen.device.toString())
    }

    private val model = AppSettingsModel()

    override val root = vbox {
        addClass(Styles.container)

        imageview("images/rok_header.png") {
            addClass(Styles.heading)

            vboxConstraints {
                marginBottom = 10.0
                vGrow = Priority.ALWAYS
            }
        }

        form {
            addClass(Styles.content)
            fieldset("Settings") {
                field("Android SDK:") {
                    label(model.androidSdk)

                    button("Select") {
                        action {
                            openSettings()
                        }
                    }
                }

                field("Select Device:") {
                    combobox<String> {
                        items = devices
                    }
                }
            }

            fieldset("Gathering") {
                field("Type:") {
                    combobox<String> {
                        items = resourceTypes
                    }
                }

                field("Level:") {
                    combobox<Int> {
                        items = resourceLevels
                    }
                }

                field("Auto time:") {
                    textfield {

                    }
                }

                field {
                    button("Start") {

                    }
                }
            }
        }
    }

    private fun openSettings() {
        val settingsView = find<SettingsView>()
        replaceWith(settingsView, sizeToScene = true, centerOnScreen = true)
    }
}