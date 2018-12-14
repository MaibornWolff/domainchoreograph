package de.maibornwolff.domainchoreograph.examples.christmas.solution

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics

@DomainDefinition
data class Delivery(
    val productName: String,
    val elfName: String,
    val deliveryTimeInHours: Double
)

@DomainDefinition
data class DeliveryPlan(
    val deliveries: List<Delivery>
)

@DomainDefinition
data class Product(
    val name: String,
    val price: Double
)

@DomainDefinition
data class Products(
    val values: List<Product>
)

@DomainDefinition
data class Elf(
    val name: String,
    val costsPerHour: Double
)

@DomainDefinition
data class Elfs(
    val values: List<Elf>
)


@DomainDefinition
class ProductCosts(
    val value: Double
) {
    companion object {
        @DomainFunction
        fun calculate(deliveryPlan: DeliveryPlan, products: Products) = ProductCosts(
            value = deliveryPlan.deliveries.sumByDouble { delivery ->
                val product = products.values.find { it.name == delivery.productName }!!
                return@sumByDouble product.price
            }
        )
    }

    override fun toString() = "$$value"
}

@DomainDefinition
class ElfLaborCosts(
    val value: Double
) {
    companion object {
        @DomainFunction
        fun calculate(deliveryPlan: DeliveryPlan, elfs: Elfs) = ElfLaborCosts(
            value = deliveryPlan.deliveries.sumByDouble { delivery ->
                val elf = elfs.values.find { it.name == delivery.elfName }!!
                return@sumByDouble elf.costsPerHour * delivery.deliveryTimeInHours
            }
        )
    }

    override fun toString() = "$$value"
}

@DomainDefinition
class DeliveryCosts(
    val value: Double
) {
    companion object {
        @DomainFunction
        fun calculate(productCosts: ProductCosts, elfLaborCosts: ElfLaborCosts) = DeliveryCosts(
            value = productCosts.value + elfLaborCosts.value
        )
    }

    override fun toString() = "$$value"
}

@DomainChoreography
interface SantaClausFactory {
    fun planDeliveryCosts(plan: DeliveryPlan, elfs: Elfs, products: Products): DeliveryCosts
}

fun main(args: Array<String>) {
    val analytics = DomainAnalytics()
    analytics.server.start()
    val environment = DomainEnvironment(setOf(
        analytics.logger
    ))

    val factory = environment.get<SantaClausFactory>()

    val deliveryPlan = DeliveryPlan(
        deliveries = listOf(
            Delivery(productName = "Pony", elfName = "Snowball", deliveryTimeInHours = 10.0),
            Delivery(productName = "Lego", elfName = "Fir", deliveryTimeInHours = 2.0)
        )
    )
    val elfs = Elfs(values = listOf(
        Elf(name = "Snowball", costsPerHour = 8.0),
        Elf(name = "Fir", costsPerHour = 10.0)
    ))
    val products = Products(values = listOf(
        Product(name = "Pony", price = 3000.00),
        Product(name = "Lego", price = 500.00)
    ))

    println(factory.planDeliveryCosts(
        plan = deliveryPlan,
        elfs = elfs,
        products = products
    ))
}
