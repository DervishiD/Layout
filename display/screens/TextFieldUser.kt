package display.screens

import display.TextField

interface TextFieldUser {

    var currentTextField: TextField?

    infix fun focusTextField(toFocus : TextField){
        currentTextField = toFocus
        currentTextField!!.focus()
    }

    fun unfocusTextField(){
        currentTextField?.unfocus()
        currentTextField = null
    }

}