package life.coder.miniautorok.app.scenes

import javafx.scene.control.TextField
import javafx.stage.FileChooser
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import tornadofx.*
import java.io.File

class SettingsView : View("Welcome to MiniAutoROK") {

    private lateinit var tfFileLocation: TextField
    private val model = AppSettingsModel()

    private val ef = arrayOf(FileChooser.ExtensionFilter("Adb file (nox_adb.exe)", "*adb.exe"))

    override val root = form {
        fieldset("Settings") {
            field("Android SDK Location") {
                hbox {
                    tfFileLocation = textfield(model.androidSdk)
                    button("Select") {
                        action {
                            val fn = chooseFile("Select adb.exe", ef, FileChooserMode.Single)
                            if (fn.isNotEmpty()) {
                                tfFileLocation.text = fn.first().absolutePath
                            }
                        }
                    }
                }

            }
        }

        button("Save") {
            action {
                model.commit()
                openMain()
            }
        }
    }

    private fun openMain() {
        val mainView = find<MainView>()
        replaceWith(mainView, sizeToScene = true, centerOnScreen = true)
    }
}