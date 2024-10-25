package data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class SesacOrderDataSource() {
    suspend fun getOrder(): String {
        return withContext(Dispatchers.IO) {
            try {
                val file = File("pg_db")

                if (!file.exists()) {
                    file.createNewFile()
                }

                with(file.reader()) {
                    val res = readText()
                    close()

                    return@withContext res
                }
            } catch (e: Exception) {
                return@withContext ""
            }
        }
    }

    suspend fun saveOrder(json: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val file = File("pg_db")

                if (!file.exists()) {
                    file.createNewFile()
                }

                with(file.writer()) {
                    val res = write(json)
                    close()

                    return@withContext true
                }
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }
}