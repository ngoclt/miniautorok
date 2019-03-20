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
    }

    init {
        container {
            alignment = Pos.TOP_CENTER
            backgroundColor += Color.WHITE
            backgroundImage += URI("/background.jpg")
            opacity = 0.7
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