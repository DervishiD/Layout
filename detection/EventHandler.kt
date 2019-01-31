package detection

import detection.MouseBehaviour.ENTER
import detection.MouseBehaviour.EXIT
import detection.MouseBehaviour.PRESS
import detection.MouseBehaviour.RELEASE
import display.Button
import java.awt.Component

class EventHandler {
    companion object {

        private val pressedKeys : ArrayList<Int> = ArrayList<Int>()

        private var typingText : Boolean = false

        private var hoveredComponent : Component? = null
        private var pressedComponent : Component? = null

        public fun pressedKeys() : ArrayList<Int> = pressedKeys

        public fun press(key : Int){
            pressedKeys.add(key)
            //TODO -- WITH THE TYPING TEXT BOOLEAN THAT DETECTS IF YOU TYPE
            //TODO -- CONTINUOUSLY AAAAAAAAAAAAAA OR TEXT-LIKE A...AAAAAAAAAAAA
        }

        public fun release(key : Int){
            pressedKeys.remove(key)
            //TODO
        }

        public fun handle(mouseAction : MouseAction){
            val target : Component = mouseAction.target()
            val behaviour : MouseBehaviour = mouseAction.behaviour()
            when(behaviour){
                ENTER -> enter(target)
                EXIT -> exit(target)
                PRESS -> press(target)
                RELEASE -> release(target)
            }
        }

        private fun enter(target : Component){
            hoveredComponent = target
            //TODO
        }

        private fun exit(target : Component){
            hoveredComponent = null
            //TODO
        }

        private fun press(target : Component){
            pressedComponent = target
            //TODO
        }

        private fun release(target : Component){
            pressedComponent = null

            if(target is Button){
                target.click()
            }

            //TODO
        }

    }
}