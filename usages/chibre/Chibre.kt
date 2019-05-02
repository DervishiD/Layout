package usages.chibre

import llayout.displayers.Label
import llayout.displayers.TextField
import llayout.frame.LApplication
import llayout.frame.LFrame
import llayout.frame.LFrameBuilder
import llayout.frame.LScene
import llayout.utilities.LProperty
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

val chibreApplication : LApplication = LApplication { frame.run() }

val screen : LScene = object : LScene(){

    var team1score : LProperty<Int> = LProperty(0)
    var team2score : LProperty<Int> = LProperty(0)

    val team1label : Label = Label(0.33, 0.33, "Team 1 : ")
    val team2label : Label = Label(0.33, 0.66, "Team 2 : ")

    val team1scorelabel : Label = Label(0, 0.33, "0").alignLeftToRight(team1label) as Label
    val team2scorelabel : Label = Label(0, 0.66, "0").alignLeftToRight(team2label) as Label

    init{
        team1score.addListener{team1scorelabel.setDisplayedText(team1score.value.toString())}
        team2score.addListener{team2scorelabel.setDisplayedText(team2score.value.toString())}
    }

    val field1 : TextField = TextField(0.66, 0.33).digitsOnly()
    val field2 : TextField = TextField(0.66, 0.66).digitsOnly()

    var focusedField : TextField? = null

    override fun keyTyped(e: KeyEvent?) {
        if(e!!.keyChar == '\n'){
            if(field1.typedText() != "") team1score.value += field1.typedText().toInt()
            if(field2.typedText() != "") team2score.value += field2.typedText().toInt()
            field1.clear()
            field2.clear()
            focusedField?.unfocus()
            focusedField = null
        }
        focusedField?.type(e)
    }

    override fun mouseClicked(e: MouseEvent?) {
        when(val c = getComponentAt(e!!.x, e.y)){
            is TextField -> focusedField = c
            else -> {
                focusedField?.unfocus()
                focusedField = null
            }
        }
        super.mouseClicked(e)
    }

    override fun load() {
        add(team1scorelabel)
        add(team2scorelabel)
        add(team1label)
        add(team2label)
        add(field1)
        add(field2)
    }

    override fun save() {
        remove(team1scorelabel)
        remove(team2scorelabel)
        remove(team1label)
        remove(team2label)
        remove(field1)
        remove(field2)
    }

}

val frame : LFrame = LFrameBuilder(screen).setDecorated(false).setFullScreen(true).exitOnClose().build()