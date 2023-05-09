package approximation

import Data
import kotlin.math.sqrt

object LinearApproximation : Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y ) = xAndY
        val sx = x.sumOf { it }
        val sxx = x.sumOf { it * it }
        val sy = y.sumOf { it }
        var sxy = 0.0
        for(i in x.indices){
            sxy += x[i] * y[i]
        }

        val (a, b) = getKramer2x2(sx, sxx, sy, sxy, x.size)
        val f = { x: Double -> a * x + b}
        val coefficients = arrayListOf(a, b)
        val meanSquareDeviation = meanSquareDeviation(f, x, y)
        val phyX = getPhiX(f, x)
        val e = getE(phyX, y)
        val pearsonCoefficient = pearsonCoefficient(x, y, sx, sy, x.size)

        return Data(f, coefficients, meanSquareDeviation, x, y, phyX, e,"Линейная аппроксимация", pearsonCoefficient)

    }

    private fun pearsonCoefficient(x: ArrayList<Double>, y: ArrayList<Double>, sx: Double, sy: Double, n: Int): Double{
        val meanX = sx/n
        val meanY = sy/n
        var s = 0.0

        for(i in x.indices){
            s+= (x[i] - meanX) * (y[i] - meanY)
        }

        val r = s/(sqrt(squareDeviation(x, meanX) * squareDeviation(y, meanY)))
        return r
    }


}