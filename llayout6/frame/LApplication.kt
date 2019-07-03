package llayout6.frame

import llayout6.utilities.Action

/**
 * An object that represents a runnable application.
 * @since LLayout 1
 */
open class LApplication(protected val onRun : Action = {}) : Runnable {
    override fun run() = onRun()
}