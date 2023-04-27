package approximation

import Data
import kotlin.math.pow

object QuadraticApproximation: Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y) = xAndY
        val sx = x.sumOf{ it }
        val sxx = x.sumOf { it.pow(2)}
        val sx3 = x.sumOf{ it.pow(3)}
        val sx4 = x.sumOf{ it.pow(4)}
        val sy = y.sumOf{ it }

        var sxy = 0.0
        var sxxy = 0.0
        for(i in x.indices){
            sxy += x[i] * y[i]
            sxxy += x[i] * x[i] * y[i]
        }

        val matrix = ArrayList(
            listOf(
            arrayListOf(x.size.toDouble(), sx, sxx, sy),
            arrayListOf(sx, sxx, sx3, sxy),
            arrayListOf(sxx, sx3, sx4, sxxy)
            )
        )
        val (a2, a1, a0) = getKramer3x3(matrix)
        val f = {x: Double -> a0 * x * x + a1 * x + a2}
        val coefficients = arrayListOf(a0, a1, a2)
        val meanSquareDeviation = meanSquareDeviation(f, x, y)
        val phyX = getPhiX(f, x)
        val e = getE(phyX, y)
        return Data(f, coefficients, meanSquareDeviation, x, y, phyX, e, "Квадратичная аппроксимация")
    }


}