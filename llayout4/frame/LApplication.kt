package llayout4.frame

import llayout4.Action

/**
 * An object that represents a runnable application.
 * @since LLayout 1
 */
open class LApplication(protected val onRun : Action = {}) : Runnable {
    override fun run() = onRun()
}