import java.util.*

/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */
class SmartBlock(
        val id: Int = 0,
        val x: Int,
        val y: Int,
        private val maxHeight: Int = HEIGHT,
        private val maxWeight: Int = WEIGHT,
        private var generateFactor: Double = DEFAULT_GENERATE_FACTOR,
        private val rootBlock: SmartBlock? = null
) {

    companion object {
        private const val HEIGHT = 7
        private const val WEIGHT = 20
        private const val DEFAULT_GENERATE_FACTOR: Double = 1.0
        private const val BLOCK = "▓"
        private const val SPACE = "░"
        private const val START = "▒"
    }

    var isLeft: Boolean = false
    var isRight: Boolean = false
    var isTop: Boolean = false
    var isBottom: Boolean = false

    private var left: SmartBlock? = null
    private var right: SmartBlock? = null
    private var top: SmartBlock? = null
    private var bottom: SmartBlock? = null

    private val matrix = if (rootBlock == null) {
        Array<Array<SmartBlock?>>(maxHeight, { Array(maxWeight, { null }) })
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
        (rootBlock ?: this).let { it.matrix!![y][x] = this@SmartBlock }
        Collections.shuffle(derectionsGenerators)
    }

    fun generate() {
        (derectionsGenerators[0].invoke(generateFactor))
        (derectionsGenerators[1].invoke(generateFactor))
        (derectionsGenerators[2].invoke(generateFactor))
        (derectionsGenerators[3].invoke(generateFactor))
    }

    fun draw() {
        println(matrix?.toString() ?: "MATRIX IS NULL")
        if (rootBlock == null) {
            val stringModel = Array(maxHeight * 3, { Array(maxWeight * 3, { "+" }) })
            matrix!!.forEachIndexed { blockYIndex, row ->
                row.forEachIndexed { blockXIndex, smartBlock ->

                    (smartBlock?.getStringModel() ?: fullStringModel()).forEachIndexed { cYIndex, cRow ->
                        cRow.forEachIndexed { cXIndex, s ->
                            stringModel[cYIndex + (blockYIndex * 3)][cXIndex + (blockXIndex * 3)] = s
                        }
                    }
                }
            }

            stringModel.forEach { row ->
                val line = StringBuffer().apply { row.forEach { append(it) } }
                println(line)
            }
        } else {
            getStringModel().forEach { row ->
                val line = StringBuffer().apply { row.forEach { append(it) } }
                println(line)
            }
        }
    }

    private fun getStringModel() = arrayOf(
            arrayOf(BLOCK, if (top != null || isBottom) SPACE else BLOCK, BLOCK),
            arrayOf(if (left != null || isRight) SPACE else BLOCK, if (rootBlock == null) START else SPACE, if (right != null || isLeft) SPACE else BLOCK),
            arrayOf(BLOCK, if (bottom != null || isTop) SPACE else BLOCK, BLOCK)
    )

    private fun fullStringModel() = arrayOf(
            arrayOf(BLOCK, BLOCK, BLOCK),
            arrayOf(BLOCK, BLOCK, BLOCK),
            arrayOf(BLOCK, BLOCK, BLOCK)
    )

    private fun getChildBlock(x: Int, y: Int) =
            SmartBlock(
                    id = id + 1,
                    x = x,
                    y = y,
                    maxHeight = maxHeight,
                    maxWeight = maxWeight,
                    generateFactor = generateFactor,
                    rootBlock = rootBlock ?: this
            )

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