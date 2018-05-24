/**
 * @author Kulikov Konstantin
 * @since 24.05.2018.
 */
class ConsoleSmartBlock(
        id: Int = 0,
        x: Int,
        y: Int,
        maxHeight: Int = HEIGHT,
        maxWeight: Int = WEIGHT,
        generateFactor: Double = DEFAULT_GENERATE_FACTOR,
        rootBlock: AbstractSmartBlock? = null
) : AbstractSmartBlock(id, x, y, maxHeight, maxWeight, generateFactor, rootBlock) {

    companion object {
        private const val BLOCK = "▓"
        private const val SPACE = "░"
        private const val START = "▒"
    }

    override fun draw() {
        if (rootBlock == null) {
            val stringModel = Array(maxHeight * 3, { Array(maxWeight * 3, { "+" }) })
            matrix!!.forEachIndexed { blockYIndex, row ->
                row.forEachIndexed { blockXIndex, smartBlock ->

                    ((smartBlock as? ConsoleSmartBlock)?.getStringModel() ?: fullStringModel()).forEachIndexed { cYIndex, cRow ->
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

    override fun getChildBlock(x: Int, y: Int) =
            ConsoleSmartBlock(
                    id = id + 1,
                    x = x,
                    y = y,
                    maxHeight = maxHeight,
                    maxWeight = maxWeight,
                    generateFactor = generateFactor,
                    rootBlock = rootBlock ?: this
            )
}