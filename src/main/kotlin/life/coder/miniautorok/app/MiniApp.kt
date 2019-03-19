package life.coder.miniautorok.app

import javafx.stage.Stage
import tornadofx.App

class MiniApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.height = 600.0
        stage.width = 800.0
        stage.isResizable = false
        super.start(stage)
    }
}