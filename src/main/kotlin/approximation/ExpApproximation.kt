package approximation

import Data
import kotlin.math.exp
import kotlin.math.ln

object ExpApproximation: Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y) = xAndY
        val expY = ArrayList(y.map{ln(it)})
        val linearApproximation = LinearApproximation.solve(x to expY)
        val (A, B) = linearApproximation.coefficients
        val a = exp(B)
        val b = A
        val f = {x: Double -> a * exp(b * x)}
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
            "Экспоненциальная аппроксимация"
        )
    }
}