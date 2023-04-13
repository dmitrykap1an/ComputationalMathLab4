import java.io.*
import java.time.LocalDateTime

object CLI {

    private lateinit var input: () -> String
    private val br = BufferedReader(FileReader("src/files/tasks/task1.txt"))
    private var visible = true


    fun approximation(){
        askInputOption()
        val data = getDataTable();
    }

    private fun getDataTable(): MutableList<Double>{
        val numberOfDots = askNumberOfDots()
        val dataTable = mutableListOf<Double>()
        ask("Введите таблицу данных:\n")
        repeat(numberOfDots){
            while(true){
                try{
                    ask("x${it + 1}: ")
                    dataTable.add(input().toDouble())
                    break;
                }
                catch (e: NumberFormatException){
                    ask("Точка должна быть представлена числом!\n")
                }
            }

        }
        return dataTable
    }



    private fun askInputOption() {
        print("Прочитать данные из файла? Д/н ")
        val str = readln()
        when (str.lowercase()) {
            "д", "\n", "l" -> {
                visible = false
                input ={ br.readLine() }
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
                ask("Введите количество точек для таблицы [8;12]: ")
                val numberOfDots = input().toInt()
                if(numberOfDots in 8..12) return numberOfDots
                else ask("Количество точек таблицы должно быть представлено числом из диапозона [8;12]!\n")
            } catch (e: NumberFormatException) {
                ask("Количество точек таблицы должно быть представлено числом из диапозона [8;12]!\n")
            }
        }

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
