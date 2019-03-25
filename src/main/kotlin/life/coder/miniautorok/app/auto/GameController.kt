package life.coder.miniautorok.app.auto

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Debug
import org.sikuli.script.ImagePath

class GameController {

    private var screen: ADBScreen? = null
    private lateinit var actionBarController: ActionBarController

    companion object {
        val instance = GameController()
    }

    init {
        setImagePath()
    }

    fun setScreen(screen: ADBScreen) {
        this.screen = screen
        actionBarController = ActionBarController(screen)
    }

    private fun setImagePath() {
        ImagePath.setBundlePath(this.javaClass.getResource("/game").path)
        Debug.info("Load images from: " + ImagePath.getBundlePath())
    }

    fun switchToMap() {
        actionBarController.openMap()
    }

    fun switchToCity() {
        actionBarController.openCity()
    }
}

