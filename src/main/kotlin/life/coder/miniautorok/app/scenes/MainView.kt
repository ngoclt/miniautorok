package life.coder.miniautorok.app.scenes

import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.layout.Priority
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import tornadofx.*

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Settings
import javafx.stage.Stage
import life.coder.miniautorok.app.auto.GameController


class MainView : View("Mini Auto: RoK") {

    private val resourceTypes = FXCollections.observableArrayList("Food",
            "Wood", "Stone", "Gold")
    private val resourceLevels = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6)

    private var device = ""

    private lateinit var adbScreen: ADBScreen

    private val model = AppSettingsModel()

    init {
        Settings.DebugLogs = true
        Settings.MinSimilarity = 0.9

        if (model.androidSdk.value.isNotEmpty()) {
            adbScreen = ADBScreen(model.androidSdk.value)
            device = adbScreen.device.toString()
        }
    }


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

                field("Connected Device:") {
                    label(if (device.isEmpty()) "Please connect device" else device)
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
                        action {
                            clickedButtonStart()
                        }
                    }
                }
            }
        }
    }

    // Helper

    private fun setupGameController() {
        GameController.instance.setScreen(adbScreen)
        GameController.instance.switchToMap()
    }

    // Actions

    private fun clickedButtonStart() {
        if (model.androidSdk.value.isEmpty()) {
            showAlert("Error", "Please config the path to your nox_adb.exe!")
            return
        }

        if (adbScreen.device == null) {
            showAlert("Error", "Please make sure you set correctly the ADB path and your device is connected!")
            return
        }

        setupGameController()
    }

    // Navigation

    private fun openSettings() {
        val settingsView = find<SettingsView>()
        replaceWith(settingsView, sizeToScene = true, centerOnScreen = true)
    }

    private fun showAlert(title: String, message: String) {
        val alert = Alert(Alert.AlertType.ERROR)
        val stage = alert.dialogPane.scene.window as Stage
        stage.isAlwaysOnTop = true
        alert.title = title
        alert.contentText = message
        alert.show()
    }
}