package life.coder.miniautorok.app

import javafx.scene.image.Image
import javafx.stage.Stage
import life.coder.miniautorok.app.scenes.MainView
import life.coder.miniautorok.app.scenes.Styles
import org.sikuli.basics.Settings
import tornadofx.App

class MiniApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.width = 450.0
        stage.height = 500.0
        stage.isResizable = false
        stage.icons += Image("icon.jpg")

        super.start(stage)
    }
}