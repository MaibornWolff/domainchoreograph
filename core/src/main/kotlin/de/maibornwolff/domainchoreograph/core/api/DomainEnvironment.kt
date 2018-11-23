package de.maibornwolff.domainchoreograph.core.api

import de.maibornwolff.domainchoreograph.core.processing.context.MutableDomainChoreographyStaticContext
import de.maibornwolff.domainchoreograph.core.processing.utils.NameUtils

class DomainEnvironment(
    logger: Set<DomainLogger>? = null
) {
    companion object {
        @JvmStatic
        fun builder() = DomainEnvironmentBuilder()
    }

    val options = logger?.let { DomainChoreographyOptions(logger = it) }

    fun <T : Any> get(domainChoreographyType: Class<T>, context: MutableDomainChoreographyStaticContext): T {
        return loadImplementation(domainChoreographyType)
            .getConstructor(MutableDomainChoreographyStaticContext::class.java)
            .newInstance(context)
    }

    fun <T : Any> get(domainChoreographyType: Class<T>, options: DomainChoreographyOptions): T {
        return loadImplementation(domainChoreographyType)
            .getConstructor(DomainChoreographyOptions::class.java)
            .newInstance(options)
    }

    fun <T : Any> get(domainChoreographyType: Class<T>): T {
        if (this.options != null) {
            return get(domainChoreographyType, options)
        }

        return loadImplementation(domainChoreographyType)
            .getConstructor()
            .newInstance()
    }

    inline fun <reified T : Any> get(): T {
        return get(T::class.java)
    }

    internal fun getMeta(domainChoreographyType: Class<*>): DomainChoreographyMeta {
        val classLoader = javaClass.classLoader
        val clazz = classLoader.loadClass(
            NameUtils.getChoreographyMetaObjectName(domainChoreographyType.name)
        )
        return clazz.getField("INSTANCE").get(DomainChoreographyMeta::class) as DomainChoreographyMeta
    }

    private fun <T : Any> loadImplementation(domainChoreographyType: Class<T>): Class<T> {
        val classLoader = javaClass.classLoader
        return classLoader.loadClass(
            NameUtils.getChoreographyImplementationName(domainChoreographyType.name)
        ) as Class<T>
    }
}

class DomainEnvironmentBuilder {
    private val logger = mutableSetOf<DomainLogger>()

    fun addLogger(logger: DomainLogger): DomainEnvironmentBuilder {
        this.logger.add(logger)
        return this
    }

    fun build() = DomainEnvironment(logger = logger)
}
