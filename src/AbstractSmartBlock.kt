import java.util.*

/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */
abstract class AbstractSmartBlock(
        val id: Int = 0,
        val x: Int,
        val y: Int,
        protected val maxHeight: Int = HEIGHT,
        protected val maxWeight: Int = WEIGHT,
        protected var generateFactor: Double = DEFAULT_GENERATE_FACTOR,
        protected val rootBlock: AbstractSmartBlock? = null
) {

    companion object {
        const val HEIGHT = 10
        const val WEIGHT = 10
        const val DEFAULT_GENERATE_FACTOR: Double = 1.0
    }

    var isLeft: Boolean = false
    var isRight: Boolean = false
    var isTop: Boolean = false
    var isBottom: Boolean = false

    protected var left: AbstractSmartBlock? = null
    protected var right: AbstractSmartBlock? = null
    protected var top: AbstractSmartBlock? = null
    protected var bottom: AbstractSmartBlock? = null

    protected val matrix = if (rootBlock == null) {
        Array<Array<AbstractSmartBlock?>>(maxHeight, { Array(maxWeight, { null }) })
    } else {
        null
    }

    private val derectionsGenerators = listOf(
            { factor: Double ->
                if (x > 0 && Math.random() < factor && itemExist(x - 1, y).not()) {
                    createLeft()
                    true
                } else {
                    false
                }
            },
            { factor: Double ->
                if (x < maxWeight - 1 && Math.random() < factor && itemExist(x + 1, y).not()) {
                    createRight()
                    true
                } else {
                    false
                }
            },
            { factor: Double ->
                if (y < maxHeight - 1 && Math.random() < factor && itemExist(x, y + 1).not()) {
                    createBottom()
                    true
                } else {
                    false
                }
            },
            { factor: Double ->
                if (y > 0 && Math.random() < factor && itemExist(x, y - 1).not()) {
                    createTop()
                    true
                } else {
                    false
                }
            }
    )

    init {
        (rootBlock ?: this).let { it.matrix!![y][x] = this }
        Collections.shuffle(derectionsGenerators)
    }

    fun generate() {
        (derectionsGenerators[0].invoke(generateFactor))
        (derectionsGenerators[1].invoke(generateFactor))
        (derectionsGenerators[2].invoke(generateFactor))
        (derectionsGenerators[3].invoke(generateFactor))
    }

    abstract fun draw()

    abstract fun getChildBlock(x: Int, y: Int): AbstractSmartBlock

    private fun itemExist(x: Int, y: Int): Boolean {
        if (rootBlock == null) return false
        return rootBlock.matrix!![y][x] != null
    }

    private fun createLeft() {
        left = getChildBlock(x - 1, y)
        left!!.isLeft = true
        left!!.generate()
    }

    private fun createRight() {
        right = getChildBlock(x + 1, y)
        right!!.isRight = true
        right!!.generate()
    }

    private fun createTop() {
        top = getChildBlock(x, y - 1)
        top!!.isTop = true
        top!!.generate()
    }

    private fun createBottom() {
        bottom = getChildBlock(x, y + 1)
        bottom!!.isBottom = true
        bottom!!.generate()
    }
}