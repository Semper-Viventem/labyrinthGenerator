/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */


fun main(args: Array<String>) {
    val root = ConsoleSmartBlock(0,10, 5, 10, 20, 0.75)
    root.generate()
    root.draw()
}