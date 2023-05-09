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
        val d = getDet(removeColumn(3,  matrix))
        val d1 = getDet(replaceColumn(0,3,  matrix))
        val d2 = getDet(replaceColumn(1, 3, matrix))
        val d3 = getDet(replaceColumn(2, 3, matrix))
        return Triple(d1/d, d2/d, d3/d)
    }

    fun getKramer4x4(matrix: ArrayList<ArrayList<Double>>): ArrayList<Double>{
        val d = getDet4x4(removeColumn(4, matrix))
        val d1 = getDet4x4(replaceColumn(0, 4,matrix))
        val d2 = getDet4x4(replaceColumn(1, 4, matrix))
        val d3 = getDet4x4(replaceColumn(2, 4, matrix))
        val d4 = getDet4x4(replaceColumn(3, 4 ,matrix))
        return arrayListOf(d1/d, d2/d, d3/d, d4/d)
    }

    private fun removeColumn(n: Int, matrix: ArrayList<ArrayList<Double>>): ArrayList<ArrayList<Double>> {
        val mx = clone(matrix)
        for (m in mx) {
            m.removeAt(n)
        }
        return mx
    }
    private fun replaceColumn(n: Int,replaceableColumn: Int, matrix: ArrayList<ArrayList<Double>>): ArrayList<ArrayList<Double>> {
        val mx = clone(matrix)
        for (i in mx.indices) {
            mx[i][n] = mx[i].last()
            mx[i].removeAt(replaceableColumn)
        }
        return mx
    }

    private fun getDet4x4(m: ArrayList<ArrayList<Double>>): Double{
        val firstMatrix = arrayListOf(
            arrayListOf(m[1][1], m[1][2], m[1][3]),
            arrayListOf(m[2][1], m[2][2], m[2][3]),
            arrayListOf(m[3][1], m[3][2], m[3][3]),
        )
        val secondMatrix = arrayListOf(
            arrayListOf(m[1][0], m[1][2], m[1][3]),
            arrayListOf(m[2][0], m[2][2], m[2][3]),
            arrayListOf(m[3][0], m[3][2], m[3][3]),
        )
        val thirdMatrix = arrayListOf(
            arrayListOf(m[1][0], m[1][1], m[1][3]),
            arrayListOf(m[2][0], m[2][1], m[2][3]),
            arrayListOf(m[3][0], m[3][1], m[3][3]),
        )
        val fourthMatrix = arrayListOf(
            arrayListOf(m[1][0], m[1][1], m[1][2]),
            arrayListOf(m[2][0], m[2][1], m[2][2]),
            arrayListOf(m[3][0], m[3][1], m[3][2]),
        )

        return m[0][0] * getDet(firstMatrix) - m[0][1] * getDet(secondMatrix) +
                m[0][2] * getDet(thirdMatrix) - m[0][3] * getDet(fourthMatrix)
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