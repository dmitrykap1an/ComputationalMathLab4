package approximation

import Data
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

object LogApproximation: Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y) = xAndY
        val newX = ArrayList(x.map{ln(it)})
        val linearApproximation = LinearApproximation.solve(newX to y)
        val (A, B) = linearApproximation.coefficients
        val a =  A
        val b = B
        val f = {x: Double -> a * ln(x) + b}
        val coefficients = arrayListOf(a, b)
        val meanSquareDeviation = meanSquareDeviation(f, x, y)
        val phyX = getPhiX(f, x)
        val e = getE(phyX, y)
        return Data(
            f,
            coefficients,
            meanSquareDeviation,
            x,
            y,
            phyX,
            e,
            "Логарифмическая аппроксимация"
        )
    }
}