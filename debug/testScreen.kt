package debug

import llayout3.displayers.*
import llayout3.frame.*
import llayout3.utilities.StringDisplay
import llayout3.utilities.Text
import java.awt.Color.RED

private object TestScreen : LScene(){

    private val b : TextButton = TextButton("Button"){}.setX(0.5).setY(0.6) as TextButton
    private val l : Label = Label("Label").alignUpToDown(b).alignRightToRight(b) as Label
    private val l2 = Label("Label 2").alignLeftTo(0).alignUpTo(0)
    private val f : TextField = TextField(0.5).setY(0.5).alignLeftTo(0) as TextField
    private val tsp : TextScrollPane = TextScrollPane(0.4, 0.4)
            .setX(0.8).setY(0.5) as TextScrollPane
    private val csp : ConsoleScrollPane = ConsoleScrollPane(0.4, 0.4)
    private val hds : HorizontalDoubleSlider =
            HorizontalDoubleSlider(0.3, 30)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    .setX(0.7)
                    .setY(0.8)
                    as HorizontalDoubleSlider
    private val vds : VerticalDoubleSlider =
            VerticalDoubleSlider(30, 250)
                    .setMinimum(0)
                    .setMaximum(5)
                    .setPrecision(0.5)
                    .setX(0.5)
                    .setY(0.25)
                    as VerticalDoubleSlider
    private val s : Switch = Switch()
    private val dc : DoubleCursor = DoubleCursor(0.2, 0.3)
                    .setMinimalXValue(-2)
                    .setMaximalXValue(2)
                    .setMinimalYValue(0)
                    .setMaximalYValue(5)
                    .setXYPrecision(0.25)
                    .alignUpTo(0)
                    .alignLeftTo(0.1) as DoubleCursor
    private val sd : Switch = Switch().alignDownTo(1.0).alignRightTo(1.0) as Switch
    private val ld : Label = Label(StringDisplay("This one's a label adapter", RED))
            .alignRightToLeft(sd)
            .alignUpToUp(sd) as Label
    private val tbd : TextButton = TextButton("That one's a TextButton adapter"){println("Yup, it is")}
            .setMaxLineLength(110).setX(340).setY(270) as TextButton
    private val tasa : TextArrowSelector<Int> = TextArrowSelector(Text("200") to 1, Text("10000000") to 2)
    private val tfa : TextField = TextField()
            .setX(0.7).setY(0.2) as TextField
    private val grid : RegularGrid = RegularGrid(2, 2, 200, 200).alignRightTo(1.0).alignUpTo(0) as RegularGrid
    private val gdc : DoubleCursor = DoubleCursor(100, 100)

    init{
        b.setOnMouseReleasedAction { b.moveAlong(-5, 5) }
        add(l)
        add(b)
        add(l2)
        add(f)
        tsp.write(25)
        tsp.writeln(122)
        tsp.writeln(0.5)
        tsp.write(" apples")
        tsp.writeln(StringDisplay("Hello there", RED))
        tsp.writeln("Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
                "totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, " +
                "explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur " +
                "magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est, qui dolorem ipsum, quia " +
                "dolor sit amet consectetur adipisci[ng] velit, sed quia non-numquam [do] eius modi tempora inci[di]dunt, ut" +
                " labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum[d] exercitationem" +
                " ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure " +
                "reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, vel illum, qui dolorem " +
                "eum fugiat, quo voluptas nulla pariatur?\n" +
                "\n" +
                "[33] At vero eos et accusamus et iusto odio dignissimos ducimus, qui blanditiis praesentium voluptatum deleniti " +
                "atque corrupti, quos dolores et quas molestias excepturi sint, obcaecati cupiditate non-provident, similique " +
                "sunt in culpa, qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum " +
                "facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio, cumque nihil impedit," +
                " quo minus id, quod maxime placeat, facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. " +
                "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet, ut et voluptates " +
                "repudiandae sint et molestiae non-recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut " +
                "reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat… ")
        add(tsp)
        csp.alignLeftTo(0)
        csp.alignDownTo(1.0)
        csp.write(1)
        csp.writeln("Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
                "totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, " +
                "explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur " +
                "magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est, qui dolorem ipsum, quia " +
                "dolor sit amet consectetur adipisci[ng] velit, sed quia non-numquam [do] eius modi tempora inci[di]dunt, ut" +
                " labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum[d] exercitationem" +
                " ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure " +
                "reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, vel illum, qui dolorem " +
                "eum fugiat, quo voluptas nulla pariatur?\n" +
                "\n" +
                "[33] At vero eos et accusamus et iusto odio dignissimos ducimus, qui blanditiis praesentium voluptatum deleniti " +
                "atque corrupti, quos dolores et quas molestias excepturi sint, obcaecati cupiditate non-provident, similique " +
                "sunt in culpa, qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum " +
                "facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio, cumque nihil impedit," +
                " quo minus id, quod maxime placeat, facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. " +
                "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet, ut et voluptates " +
                "repudiandae sint et molestiae non-recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut " +
                "reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat… ")
        add(csp)
        add(hds)
        add(vds)
        add(dc)
        add(ld)
        add(sd)
        add(tbd)
        tasa.alignRightToLeft(ld).alignDownToUp(ld)
        add(tasa)
        add(tfa)
        grid[0, 0] = s
        grid[1, 1] = gdc
        add(grid)
    }

}

private val frame : LFrame = LFrame(TestScreen)

val testApplication : LApplication = LApplication{ frame.run() }
