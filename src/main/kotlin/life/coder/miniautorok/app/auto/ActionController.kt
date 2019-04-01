package life.coder.miniautorok.app.auto

import org.sikuli.android.ADBScreen
import org.sikuli.basics.Debug
import org.sikuli.script.Location

class ActionController(private val screen: ADBScreen) {

    var isOutOfTroop = false

    private fun resetScreenIfNeeded() {
        if (screen.has("button_close_dialog.png")) {
            screen.click("button_close_dialog.png")
        }

        if (!screen.has("map.png") && !screen.has("city.png")) {
            screen.click()
            try {
                screen.wait(3000)
            } catch (e: Exception) {
                Debug.error(e.localizedMessage)
            }

        }
    }

    fun openMap(): Boolean {
        try {
            resetScreenIfNeeded()

            if (screen.has("map.png")) {
                Debug.info("Go to map.")
                screen.click("map.png")
                return true
            } else if (screen.has("city.png")) {
                Debug.info("Already in the map.")
                return true
            }
        } catch (e: Exception) {
            Debug.error("Cannot go to map: " + e.localizedMessage)
        }

        return false
    }

    fun openCity(): Boolean {
        try {
            resetScreenIfNeeded()

            if (screen.has("city.png")) {
                Debug.info("Go to city.")
                screen.click("city.png")
                return true
            } else if (screen.has("map.png")) {
                Debug.info("Already in the city.")
                return true
            }

        } catch (e: Exception) {
            Debug.error("Cannot go to city: " + e.localizedMessage)
        }

        return false
    }

    fun clickSearch(): Boolean {
        try {
            screen.click("search.png")
            Debug.info("Open search tooltip.")
            return true
        } catch (e: Exception) {
            Debug.error("Cannot find search button: " + e.localizedMessage)
        }

        return false
    }

    fun searchOnMap(type: Int): Boolean {

        if (!openMap()) {
            return false
        }

        if (!clickSearch()) {
            return false
        }

        var itemImage: String = imageFor(type)

        try {
            screen.wait(itemImage)
            screen.click(itemImage)
            screen.wait("button_search.png")
            screen.click("button_search.png")
            screen.wait("search_arrow.png")
            screen.click(screen.center)

        } catch (e: Exception) {
            Debug.error("Cannot search item: " + e.localizedMessage)
        }

        return false
    }

    private fun imageFor(type: Int): String {
        when (type) {
            1 -> return "search_corn.png"
            2 -> return "search_wood.png"
            3 -> return "search_stone.png"
            else -> {
                return "search_barbarians.png"
            }
        }
    }

    fun attackBarbarian() {
        try {
            screen.wait("button_attack.png")
            screen.click("button_attack.png")

            screen.wait("button_new_troops.png")
            screen.click("button_new_troops.png")

            screen.click("button_switch_commander.png")
            screen.click("button_select_commander_kusunoki.png")
            screen.click("button_marching_army.png")


        } catch (e: Exception) {
            Debug.error("Cannot find search button: " + e.localizedMessage)
        }
    }

    fun gather(type: Int): Boolean {
        try {
            searchOnMap(type)

            screen.wait("button_gather.png")
            screen.click("button_gather.png")

            screen.wait("button_new_troops.png")
            screen.click("button_new_troops.png")

            if (!screen.has("no_troop.png")) {
                screen.wait("button_marching_army.png")
                screen.click("button_marching_army.png")
                isOutOfTroop = false
                return true
            }  else {
                isOutOfTroop = true
            }

            if (screen.has("button_close_dialog.png")) {
                screen.click("button_close_dialog.png")
            }

            return false

        } catch (e: Exception) {
            Debug.error("Cannot gather item: " + type + e.localizedMessage)

        }

        return false
    }

    fun setLevelForSearch(level: Int): Boolean {
        try {
            // try to reset the slider to level 1
            //            Match btnMinus = screen.find("level_minus.png");
            while (!screen.has("sliderbar_empty.png")) {
                screen.click("level_minus.png")
            }

            // Now we set the level for slider
            //            Match btnPlus = screen.find("level_plus.png");
            for (i in 0 until level) {
                screen.click("level_plus.png")
            }
        } catch (e: Exception) {
            Debug.error("Cannot set level: " + e.localizedMessage)
        }

        return true
    }

    fun countNumberOfArmies(): Int {
        try {
            screen.click("button_army_info.png")

            val returnButtons = screen.findAll("button_return_army.png").asSequence().toList()
            val armiesInQueue = returnButtons.count()

            screen.click("button_close_dialog.png")

            return armiesInQueue
        } catch (e: Exception) {
        }
        return 0
    }

    companion object {

        private val MAX_LEVEL_BARBARIAN = 25
        private val MAX_LEVEL_RESOURCE_SLOT = 6
    }
}
