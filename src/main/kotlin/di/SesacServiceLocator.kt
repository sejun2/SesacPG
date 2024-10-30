package di

import java.lang.reflect.Type
import java.rmi.NoSuchObjectException

object SesacServiceLocator {
    const val DEFAULT_NAME = "\$\$DEFAULT"


    val diCache = HashMap<Type, HashMap<String, Any>>()

    inline fun <reified T : Any> registerSingleton(instance: T) {
        diCache.put(T::class.java, hashMapOf<String, Any>(DEFAULT_NAME to instance))
    }

    inline fun <reified T : Any> registerSingleton(instance: T, instanceName: String) {
        diCache.put(T::class.java, hashMapOf<String, Any>(instanceName to instance))
    }

    inline fun <reified T : Any> get(): T {
        val res = diCache.get(T::class.java)?.get(DEFAULT_NAME)
            ?: throw NoSuchObjectException("${T::class.java} is not registered")
        return res as T
    }

    inline fun <reified T : Any> get(instanceName: String): T {
        val res = diCache.get(T::class.java)?.get(instanceName)
            ?: throw NoSuchObjectException("${T::class.java} with $instanceName is not registered")
        return res as T
    }

    fun clearDi() {
        diCache.clear()
    }
}