package data.datasource

import java.io.File

class SesacOrderDataSource() {
    fun getOrder(): String {
        try {
            val file = File("pg_db")

            if (!file.exists()) {
                file.createNewFile()
            }

            with(file.reader()) {
                val res = readText()
                close()

                return res
            }
        } catch (e: Exception) {
            return ""
        }
    }

    fun saveOrder(json: String): Boolean {
        try {
            val file = File("pg_db")

            if (!file.exists()) {
                file.createNewFile()
            }

            with(file.writer()) {
                val res = write(json)
                close()

                return true
            }
        } catch (e: Exception) {
            return false
        }
    }
}