package life.coder.miniautorok.app.scenes

import javafx.collections.FXCollections
import tornadofx.*

class MainView : View("Mini Auto: RoK") {

    val resourceTypes = FXCollections.observableArrayList("Food",
            "Wood", "Stone", "Gold")
    val resourceLevels = FXCollections.observableArrayList(1,
            2, 3, 4, 5, 6)

    override val root = vbox {
        addClass(Styles.container)

        imageview("images/rok_header.png") {
            addClass(Styles.heading)
        }

        label("Mini Auto v0.1") {
            addClass(Styles.title)
        }

        form {
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
}