package llayout7.frame

import llayout7.utilities.Action

/**
 * An object that represents a runnable application.
 * @since LLayout 1
 */
open class LApplication(protected val onRun : Action = {}) : Runnable {
    override fun run() = onRun()
}