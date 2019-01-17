package main

import java.awt.event.KeyEvent.VK_ESCAPE

enum class Key(vararg codes : Int){

    ESCAPE(VK_ESCAPE);

    private val codes : IntArray = codes

    public fun correspondsTo(code : Int) : Boolean{
        for(i : Int in codes){
            if(i == code){
                return true
            }
        }
        return false
    }

}