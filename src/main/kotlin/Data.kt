class Data(
    val f: (Double) -> Double,
    val coefficients: ArrayList<Double>,
    val meanSquareDeviation: Double,
    val x: ArrayList<Double>,
    val y: ArrayList<Double>,
    val phyX: ArrayList<Double>,
    val e: ArrayList<Double>,
    val nameOfApproximation: String,
    val pearsonCoefficient: Double? = null,
)