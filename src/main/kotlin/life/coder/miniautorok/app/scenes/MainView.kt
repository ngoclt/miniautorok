package life.coder.miniautorok.app.scenes

import javafx.collections.FXCollections
import life.coder.miniautorok.app.viewmodels.AppSettingsModel
import tornadofx.*

class MainView : View("Mini Auto: RoK") {

    val resourceTypes = FXCollections.observableArrayList("Food",
            "Wood", "Stone", "Gold")
    val resourceLevels = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6)

    private val model = AppSettingsModel()

    override val root = vbox {
        addClass(Styles.container)

        imageview("images/rok_header.png") {
            addClass(Styles.heading)
        }

        label("Mini Auto v0.1") {
            addClass(Styles.title)
        }

        form {
            fieldset("Settings") {
                field("Android SDK:") {
                    label(model.androidSdk)

                    button("Select") {
                        action {
                            openSettings()
                        }
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