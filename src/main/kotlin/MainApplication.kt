import di.DIProvider
import presentation.*

class MainApplication {
    fun run() {
        DIProvider.provide()
        ConsoleController().start()
    }
}

fun main(args: Array<String>) {
    MainApplication().run()
}
//TODO: runCatching, coroutines mig