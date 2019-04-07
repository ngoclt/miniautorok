package life.coder.miniautorok.app.auto

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Debug
import org.sikuli.script.ImagePath
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class GameController {

    private var screen: ADBScreen? = null

    private var maximumNumberOfArmies = 1
    private var gatheringType = 1

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

    private fun rssType(type: String): Int {
        when (type) {
            "Food" -> return 1
            "Wood" -> return 2
            "Stone" -> return 3
            else -> {
                return 1
            }
        }
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

    private fun doGathering(type: Int) {
        switchToMap()
        gatheringType = type
        currentNumberOfArmies = actionController.countNumberOfArmies()
        if (currentNumberOfArmies >= maximumNumberOfArmies) {
            autoGatherService?.shutdown()
            autoGatherService = null

            checkArmyService = Executors.newSingleThreadScheduledExecutor()
            checkArmyService?.scheduleAtFixedRate({ checkArmies() }, 0, 3, TimeUnit.MINUTES)

            return
        }

        val sentFarmerSuccessfully = actionController.gather(type)
        switchToCity()

        if (!sentFarmerSuccessfully) {
            isOutOfTroop = actionController.isOutOfTroop
            troopFullAtNumberOfArmy = currentNumberOfArmies
        }
    }

    private fun checkArmies() {
        currentNumberOfArmies = actionController.countNumberOfArmies()
        if (currentNumberOfArmies < maximumNumberOfArmies) {
            checkArmyService?.shutdown()
            checkArmyService = null

            autoGatherService = Executors.newSingleThreadScheduledExecutor()
            autoGatherService?.scheduleAtFixedRate({ doGathering(gatheringType) }, 0, 60, TimeUnit.SECONDS)

            return
        }
    }

    // Game helper
    private var autoGatherService: ScheduledExecutorService? = null
    private var checkArmyService: ScheduledExecutorService? = null


    fun autoGather(type: String, level: Int, armies: Int, time: Int) {
        maximumNumberOfArmies = armies
        autoGatherService = Executors.newSingleThreadScheduledExecutor()
        autoGatherService?.scheduleAtFixedRate({ doGathering(rssType(type)) }, 0, 60, TimeUnit.SECONDS)
    }

    fun stopAutoGather() {
        autoGatherService?.shutdown()
        autoGatherService = null

        checkArmyService?.shutdown()
        checkArmyService = null
    }

    fun verify() {
        actionController.verifyCaptcha()
    }
}

