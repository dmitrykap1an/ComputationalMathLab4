import approximation.*
import org.jetbrains.letsPlot.geom.geomArea
import org.jetbrains.letsPlot.letsPlot
import java.io.*
import java.time.LocalDateTime

object CLI {

    private lateinit var input: () -> String
    private lateinit var bw : BufferedWriter
    private val br = BufferedReader(FileReader("src/files/tasks/task3.txt"))
    private var visible = true


    fun approximation(){
        askInputOption()
        val data = getDataTable();
        val quadraticApproximation = QuadraticApproximation.solve(data)
        val linearApproximation = LinearApproximation.solve(data)
        val powerApproximation = PowerApproximation.solve(data)
        val expApproximation = ExpApproximation.solve(data)
        val logApproximation = LogApproximation.solve(data)
        val arr = arrayListOf(
            quadraticApproximation,
            linearApproximation,
            powerApproximation,
            expApproximation,
            logApproximation
        )
        askOutputOption()
        val bestApproximation = arr.minBy { it.meanSquareDeviation }
        printResult(powerApproximation)
        //printResult(bestApproximation)

    }

    private fun getDataTable(): Pair<ArrayList<Double>, ArrayList<Double>>{
        val numberOfDots = askNumberOfDots()
        ask("Введите таблицу данных:\n")
        return getTable(numberOfDots, 'x') to getTable(numberOfDots, 'y')
    }

    private fun askOutputOption(){
        print("Записать результат в файл? Д/н ")
        val str = readln()
        bw = when (str.lowercase()) {
            "д", "\n", "l" -> {
                createFileAndWriteResult()

            }

            else -> {
                BufferedWriter(OutputStreamWriter(System.out))
            }
        }
    }

    private fun printResult(data: Data){
        bw.write(data.nameOfApproximation.uppercase())
        bw.newLine()
        bw.write("Коэффициенты аппроксимирующих функций:\n")
        for(i in data.coefficients.indices){
            bw.write("${'a' + i}: ${data.coefficients[i]}, ")
        }
        bw.newLine()
        bw.write("Среднеквадратичное отклонение: ${data.meanSquareDeviation}\n")
        bw.write("Массив x: ${data.x}\n")
        bw.write("Массив y: ${data.y}\n")
        bw.write("Массив phy(x): ${data.phyX}\n")
        bw.write("Массив e: ${data.e}\n")
        if(data.pearsonCoefficient != null) bw.write("Коэффициент корреляции Пирсона: ${data.pearsonCoefficient}\n")
        bw.write("----------------------------------------------------")
        bw.newLine()
        bw.flush()
        //bw.close()
        //showPlot(data)
    }

    private fun getTable(numberOfDots: Int, char: Char): ArrayList<Double> {
            while(true){
                try{
                    ask("Введите $numberOfDots чисел соответсвующих $char:\n ")
                   val data = ArrayList(input().split(" ").map { it.toDouble() })
                    if(data.size == numberOfDots) return data
                    else ask("Введите $numberOfDots чисел!")
                }
                catch (e: NumberFormatException){
                    ask("Точки должны быть представлена числом!\n")
                }
            }

    }




    private fun askInputOption() {
        print("Прочитать данные из файла? Д/н ")
        val str = readln()
        when (str.lowercase()) {
            "д", "\n", "l" -> {
                visible = false
                input = { br.readLine() }
            }

            else -> {
                visible = true
                input = { readln() }
            }
        }
    }

    private fun askNumberOfDots(): Int{
        while(true){
            try {
                ask("Введите количество точек для таблицы [7;12]: ")
                val numberOfDots = input().toInt()
                if(numberOfDots in 7..12) return numberOfDots
                else ask("Количество точек таблицы должно быть представлено числом из диапозона [8;12]!\n")
            } catch (e: NumberFormatException) {
                ask("Количество точек таблицы должно быть представлено числом из диапозона [8;12]!\n")
            }
        }

    }

    private fun showPlot(data: Data){
        val (listX, listY)  = generateSequence(data.f, data.x.first() - 3, data.x.last() + 3)
        val d = mapOf(
            data.x + listX to data.y + listY
        )

        val plot = letsPlot(d) + geomArea(fill = "white"){x = listX; y = listY}
        plot.show()
    }

    private fun generateSequence(f: (Double) -> Double, start: Double, end: Double): Pair<ArrayList<Double>, ArrayList<Double>> {
        val x = ArrayList<Double>()
        val y = ArrayList<Double>()
        var s = start
        val step = (end - start) / 100
        while(s <= end){
            x.add(s)
            y.add(f(s))
            s += step
        }
        return x to y
    }


    private fun ask(text: String) {
        if (visible) print(text)
    }

    fun createFileAndWriteResult(): BufferedWriter {
        val date = LocalDateTime.now()
        val file = File(
            "/home/newton/IdeaProjects/Math/comp_math/lab4/src/files/results/" +
                    "${date.dayOfMonth}_${date.month}_${date.hour}:${date.minute}.${date.second}"
        )

        return BufferedWriter(FileWriter(file))
    }



}

private operator fun String.times(i: Int): String {
    val builder = StringBuilder()
    repeat(i){
        builder.append(this)
    }
    return builder.toString()
}