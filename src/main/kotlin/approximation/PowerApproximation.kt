package approximation

import Data
import java.lang.Math.pow
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

object PowerApproximation: Approximation() {
    override fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data {
        val (x, y) = xAndY
        val newX = ArrayList(x.map{ln(it)})
        val newY = ArrayList(y.map{ln(it)})
        val linearApproximation = LinearApproximation.solve(newX to newY)
        val (B, A) = linearApproximation.coefficients
        val a = exp(A)
        val b = B
        val f = { x: Double -> a * x.pow(b)}
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
            "Степенная аппроксимация"
        )
    }
}