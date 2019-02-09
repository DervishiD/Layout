package detection

import display.Button
import java.awt.Component

internal class EventHandler {
    internal companion object {

        private val pressedKeys : ArrayList<Int> = ArrayList<Int>()

        private var typingText : Boolean = false

        private var hoveredComponent : Component? = null
        private var pressedComponent : Component? = null

        internal fun pressedKeys() : ArrayList<Int> = pressedKeys

        internal fun press(key : Int){
            pressedKeys.add(key)
            //TODO -- WITH THE TYPING TEXT BOOLEAN THAT DETECTS IF YOU TYPE
            //TODO -- CONTINUOUSLY AAAAAAAAAAAAAA OR TEXT-LIKE A...AAAAAAAAAAAA
        }

        internal fun release(key : Int){
            pressedKeys.remove(key)
            //TODO
        }

        internal fun enter(target : Component){
            hoveredComponent = target
            //TODO
        }

        internal fun exit(target : Component){
            hoveredComponent = null
            //TODO
        }

        internal fun press(target : Component){
            pressedComponent = target
            //TODO
        }

        internal fun release(target : Component){
            pressedComponent = null

            if(target is Button){
                target.click()
            }

            //TODO
        }

    }
}