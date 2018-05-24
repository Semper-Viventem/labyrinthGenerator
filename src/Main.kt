/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */


fun main(args: Array<String>) {
    val root = ConsoleSmartBlock(0,0, 0, 10, 10)
    root.isRight = true
    root.generate()
    root.draw()
}