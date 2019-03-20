package life.coder.miniautorok.app.scenes

import com.sun.javafx.css.Size
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import java.net.URI

class Styles : Stylesheet() {
    companion object {
        val container by cssclass()
        val heading by cssclass()
        val title by cssclass()
        val content by cssclass()

        val wrapperColor = c("white", 0.5)
    }

    init {
        heading {
            spacing = 10.px
        }

        container {
            alignment = Pos.TOP_CENTER
            backgroundImage += URI("/background.jpg")
            padding = box(10.px)
        }

        content {
            backgroundColor += wrapperColor
            padding = box(10.px)
        }

        imageView and heading {

        }

        label and title {
            fontWeight = FontWeight.BOLD
            fontFamily = "Comic Sans MS"
            fontSize = 24.px
        }


    }
}