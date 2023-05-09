package approximation

import Data
import kotlin.math.pow

object CubeApproximation: Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y) = xAndY
        val n = x.size.toDouble()
        val sx = x.sumOf { it }
        val sx2 = x.sumOf { it.pow(2) }
        val sx3 = x.sumOf { it.pow(3) }
        val sx4 = x.sumOf { it.pow(4) }
        val sx5 = x.sumOf { it.pow(5) }
        val sx6 = x.sumOf { it.pow(6) }
        val sy = y.sumOf{ it }
        var sxy = 0.0;
        var sx2y = 0.0
        var sx3y = 0.0
        for(i in x.indices){
            sxy += x[i] * y[i]
            sx2y += x[i].pow(2) * y[i]
            sx3y += x[i].pow(3) * y[i]
        }

        val matrix = arrayListOf(
            arrayListOf(sx6, sx5, sx4, sx3, sx3y),
            arrayListOf(sx5, sx4, sx3,sx2, sx2y ),
            arrayListOf(sx4, sx3,sx2, sx, sxy ),
            arrayListOf(sx3,sx2, sx, n, sy ),
        )
        val (a, b, c, d) = getKramer4x4(matrix)
        val f = {x: Double -> a * x.pow(3) + b *  x.pow(2) + c * x + d}
        val coefficients = arrayListOf(a, b, c, d)
        val meanSquareDeviation = meanSquareDeviation(f, x, y)
        val phyX = getPhiX(f, x)
        val e = getE(phyX, y)
        return Data(f, coefficients, meanSquareDeviation, x, y, phyX, e, "Кубическая аппроксимация")
    }
}