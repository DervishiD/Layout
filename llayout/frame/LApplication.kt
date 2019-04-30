package llayout.frame

import llayout.Action

open class LApplication(protected val onRun : Action = {}) : Runnable {
    override fun run() = onRun()
}