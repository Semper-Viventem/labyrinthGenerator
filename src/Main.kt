/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */


fun main(args: Array<String>) {
    val root = SmartBlock(0,0, 0, 30, 30)
    root.isRight = true
    root.generate()
    root.draw()
}