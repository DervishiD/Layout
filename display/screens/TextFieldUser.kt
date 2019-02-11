package display.screens

import display.TextField

interface TextFieldUser {

    var currentTextField: TextField?

    infix fun focusTextField(toFocus : TextField){
        currentTextField = toFocus
    }

    fun unfocusTextField(){
        currentTextField = null
    }

}