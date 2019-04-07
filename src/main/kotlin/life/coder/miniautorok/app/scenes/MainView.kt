package life.coder.miniautorok.app.scenes

import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import tornadofx.*

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Settings
import javafx.stage.Stage
import life.coder.miniautorok.app.auto.GameController
import org.sikuli.basics.Debug
import org.sikuli.script.ImagePath
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService




class MainView : View("Mini Auto: RoK") {

    private val resourceTypes = FXCollections.observableArrayList("Food",
            "Wood", "Stone")
    private val resourceLevels = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6)
    private val availableArmies = FXCollections.observableArrayList(1,
            2, 3, 4, 5)
    private val autoTimes = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)


    private var device = ""

    private lateinit var adbScreen: ADBScreen

    private lateinit var cbRssType: ComboBox<String>
    private lateinit var cbRssLevel: ComboBox<Int>
    private lateinit var cbArmies: ComboBox<Int>
    private lateinit var cbTime: ComboBox<Int>

    private val model = AppSettingsModel()

    init {
        Settings.DebugLogs = true
        Settings.AutoWaitTimeout = 5.0f

        if (model.androidSdk.value != null) {
            adbScreen = ADBScreen(model.androidSdk.value)
            if (adbScreen.device != null) {
                device = adbScreen.device.toString()
            }
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
                    cbRssType = combobox {
                        addClass(Styles.combobox)
                        items = resourceTypes

                    }
                }

                field("Level:") {
                    cbRssLevel = combobox {
                        addClass(Styles.combobox)
                        items = resourceLevels
                    }
                }

                field("Number of armies:") {
                    cbArmies = combobox {
                        addClass(Styles.combobox)
                        items = availableArmies
                    }
                }

                field("Auto time (hours):") {
                    cbTime = combobox {
                        addClass(Styles.combobox)
                        items = autoTimes
                    }
                }

                field {
                    button("Start") {
                        action {
                            clickedButtonStart()
                        }
                    }
                    button("Stop") {
                        action {
                            clickedButtonStop()
                        }
                    }
                }
            }
        }
    }

    // Helper

    private fun setupGameController() {
        GameController.instance.setScreen(adbScreen)
    }

    // Actions

    private fun clickedButtonStart() {

        val type = cbRssType.selectedItem
        val level = cbRssLevel.selectedItem
        val armies = cbArmies.selectedItem
        val time = cbTime.selectedItem

        if (type == null || level == null || armies == null || time == null) {
            showAlert("Error", "Please input the auto configuration.")
            return
        }

        if (model.androidSdk.value.isEmpty()) {
            showAlert("Error", "Please setup the path to your nox_adb.exe!")
            return
        }

        if (adbScreen.device == null) {
            showAlert("Error", "Please make sure you set correctly the ADB path and your device is connected!")
            return
        }

        setupGameController()
        GameController.instance.autoGather(type, level, armies, time)

//        GameController.instance.verify()
    }

    private fun clickedButtonStop() {
        GameController.instance.stopAutoGather()
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