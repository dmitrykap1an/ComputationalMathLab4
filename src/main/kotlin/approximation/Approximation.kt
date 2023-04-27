package approximation

import Data
import kotlin.math.pow
import kotlin.math.sqrt

sealed class Approximation {

    fun getKramer2x2(sx: Double, sxx: Double, sy: Double, sxy: Double, n: Int): Pair<Double, Double>{
        val d = sxx * n - sx * sx
        val d1 = sxy * n - sx * sy
        val d2 = sxx * sy - sx * sxy
        return d1/d to d2/d
    }


    fun getKramer3x3(matrix: ArrayList<ArrayList<Double>>): Triple<Double, Double, Double> {
        val d = getDet(removeColumn(3, matrix))
        val d1 = getDet(replaceColumn(0, matrix))
        val d2 = getDet(replaceColumn(1, matrix))
        val d3 = getDet(replaceColumn(2, matrix))
        return Triple(d1/d, d2/d, d3/d)
    }

    private fun removeColumn(n: Int, matrix: ArrayList<ArrayList<Double>>): ArrayList<ArrayList<Double>> {
        val mx = clone(matrix)
        for (m in mx) {
            m.removeAt(n)
        }
        return mx
    }
    private fun replaceColumn(n: Int, matrix: ArrayList<ArrayList<Double>>): ArrayList<ArrayList<Double>> {
        val mx = clone(matrix)
        for (i in mx.indices) {
            mx[i][n] = mx[i].last()
            mx[i].removeAt(3)
        }
        return mx
    }



    private fun getDet(m: ArrayList<ArrayList<Double>>): Double{
        val a  = m[0][0] * m[1][1] * m[2][2] + m[1][0] * m[2][1] * m[0][2] + m[0][1] * m[1][2] * m[2][0]
        val b  = - m[0][2] * m[1][1] * m[2][0] - m[0][0] * m[1][2] * m[2][1] - m[0][1] * m[1][0] * m[2][2]
        return a + b
    }
    fun deviationMeasure(f: (Double) -> Double, x: ArrayList<Double>, y: ArrayList<Double>): Double {
        val fx = ArrayList(x.map{ f(it) })
        return squareDeviation(fx, y)
    }

    fun getE(phyX: ArrayList<Double>, y: ArrayList<Double>): ArrayList<Double> {
        val e = ArrayList<Double>()
        for(i in phyX.indices){
            e.add(phyX[i] - y[i])
        }
        return e
    }

    private fun squareDeviation(x: ArrayList<Double>, y: ArrayList<Double>): Double{
        var sum = 0.0
        for(i in x.indices){
            sum += (x[i] - y[i]).pow(2)
        }
        return sum
    }
    fun squareDeviation(x: ArrayList<Double>, y: Double): Double{
        var sum = 0.0
        for(i in x.indices){
            sum += (x[i] - y).pow(2)
        }
        return sum
    }

    fun meanSquareDeviation(f: (x: Double) -> Double, x: ArrayList<Double>, y: ArrayList<Double>): Double{
        val data = getPhiX(f, x)
        return sqrt(squareDeviation(data, y)/x.size)
    }

    private fun clone(matrix: ArrayList<ArrayList<Double>>): ArrayList<ArrayList<Double>> {
        val m = ArrayList<ArrayList<Double>>()
        for(i in matrix){
            val clone = i.clone() as ArrayList<Double>
            m.add(clone)
        }
        return m
    }

    fun getPhiX(f: (Double) -> Double, x: ArrayList<Double>): ArrayList<Double> {
        return ArrayList(x.map(f))
    }
    abstract fun solve(xAndY: Pair<ArrayList<Double>, ArrayList<Double>>): Data

}