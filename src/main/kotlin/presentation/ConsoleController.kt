package presentation

import di.SesacServiceLocator

class ConsoleController(
) {

    companion object {
        @JvmStatic
        var currentScreen: BaseScreen = SesacServiceLocator.get<HomeScreen>()
    }

    fun start() {
        while (true) {
            currentScreen.display()
            currentScreen.handleInput()
        }
    }
}
