package di

import java.lang.reflect.Type
import java.rmi.NoSuchObjectException

object SesacServiceLocator {

    val diCache = HashMap<Type, Any>()

    inline fun <reified T : Any> registerSingleton(instance: T) {
        diCache.put(T::class.java, instance)
    }

    inline fun <reified T : Any> get(): T {
        val res = diCache.get(T::class.java) ?: throw NoSuchObjectException("${T::class.java} is not registered")
        return res as T
    }

    fun clearDi() {
        diCache.clear()
    }
}