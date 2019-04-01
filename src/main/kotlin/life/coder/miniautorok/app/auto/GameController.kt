package life.coder.miniautorok.app.auto

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Debug
import org.sikuli.script.ImagePath
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class GameController {

    private var screen: ADBScreen? = null

    private var maximumNumberOfArmies = 2


    private var currentNumberOfArmies = 0
    private var isOutOfTroop = false
    private var troopFullAtNumberOfArmy = -1

    private lateinit var actionController: ActionController

    companion object {
        val instance = GameController()
    }

    init {
        setImagePath()
    }

    fun setScreen(screen: ADBScreen) {
        this.screen = screen
        actionController = ActionController(screen)
    }

    fun setMaximumNumberOfArmies(number: Int) {
        maximumNumberOfArmies = number
    }

    private fun setImagePath() {
        ImagePath.setBundlePath(this.javaClass.getResource("/game").path)
        Debug.info("Load images from: " + ImagePath.getBundlePath())
    }

    fun reset() {
        currentNumberOfArmies = 0
        isOutOfTroop = false
        troopFullAtNumberOfArmy = -1
    }

    fun switchToMap() {
        actionController.openMap()
    }

    fun switchToCity() {
        actionController.openCity()
    }

    private fun doGathering() {
        switchToMap()
        currentNumberOfArmies = actionController.countNumberOfArmies()
        if (currentNumberOfArmies >= maximumNumberOfArmies
                || (isOutOfTroop && troopFullAtNumberOfArmy == currentNumberOfArmies)) {
            return
        }

        val sentFarmerSuccessfully = actionController.gather(1)

        if (!sentFarmerSuccessfully) {
            isOutOfTroop = actionController.isOutOfTroop
            troopFullAtNumberOfArmy = currentNumberOfArmies
        }
    }

    // Game helper
    private var autoGatherService: ScheduledExecutorService? = null

    fun autoGather() {
        autoGatherService = Executors.newSingleThreadScheduledExecutor()
        autoGatherService?.scheduleAtFixedRate({ doGathering() }, 0, 30, TimeUnit.SECONDS)
    }

    fun stopAutoGather() {
        autoGatherService?.shutdown()
        autoGatherService = null
    }

    fun numberOfArmiesOnMap(): Int {
        Debug.info("Checking number of armies on map")
        currentNumberOfArmies = actionController.countNumberOfArmies()
        Debug.info("Number of armies on map: $currentNumberOfArmies")
        return currentNumberOfArmies
    }
}

