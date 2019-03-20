package life.coder.miniautorok.app.scenes

import javafx.scene.control.TextField
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import tornadofx.*

class SettingsView : View("Welcome to MiniAutoROK") {

    private lateinit var tfFileLocation: TextField
    private val model = AppSettingsModel()

    override val root = form {
        fieldset("Settings") {
            field("Android SDK Location") {
                hbox {
                    tfFileLocation = textfield(model.androidSdk)
                    button("Select") {
                        action {
                            val fn = chooseDirectory("Select Android SDK directory")
                            if (fn != null) {
                                tfFileLocation.text = "${fn}"
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