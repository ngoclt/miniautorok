package life.coder.miniautorok.app

import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App

class MiniApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.width = 250.0
        stage.height = 400.0
        stage.isResizable = false
        stage.icons += Image("icon.jpg")
        super.start(stage)
    }
}