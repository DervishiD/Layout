package usages.chibre

import llayout2.displayers.Label
import llayout2.displayers.TextField
import llayout2.frame.*
import llayout2.utilities.LObservable

val chibreApplication : LApplication = LApplication { frame.run() }

object Screen : LScene(){

    var team1score : LObservable<Int> = LObservable(0)
    var team2score : LObservable<Int> = LObservable(0)

    val team1label : Label = Label("Team 1 : ").setX(0.33).setY(0.33) as Label
    val team2label : Label = Label("Team 2 : ").setX(0.33).setY(0.66) as Label

    val team1scorelabel : Label = Label("0").setY(0.33).alignLeftToRight(team1label) as Label
    val team2scorelabel : Label = Label("0").setY(0.66).alignLeftToRight(team2label) as Label

    val field1 : TextField = TextField().matchPositiveShort().setX(0.66).setY(0.33) as TextField
    val field2 : TextField = TextField().matchPositiveShort().setX(0.66).setY(0.66) as TextField

    init{
        team1score.addListener{team1scorelabel.setText(team1score.value)}
        team2score.addListener{team2scorelabel.setText(team2score.value)}
        field1.setOnEnterAction {
            team1score.value += field1.text().toInt()
            field1.clear()
        }
        field2.setOnEnterAction {
            team2score.value += field2.text().toInt()
            field2.clear()
        }
        add(team1scorelabel)
        add(team2scorelabel)
        add(team1label)
        add(team2label)
        add(field1)
        add(field2)
    }

}

val frame : LFrame = LFrame(Screen).setFullscreen()
