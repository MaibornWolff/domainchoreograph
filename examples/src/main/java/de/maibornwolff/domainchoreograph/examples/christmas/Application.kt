package de.maibornwolff.domainchoreograph.examples.christmas

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.core.api.DomainDefinition
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.core.api.DomainFunction
import de.maibornwolff.domainchoreograph.domainanalytics.DomainAnalytics

data class Delivery(
    val productName: String,
    val elfName: String,
    val deliveryTimeInHours: Double
)

data class DeliveryPlan(
    val deliveries: List<Delivery>
)

data class Product(
    val name: String,
    val price: Double
)

data class Products(
    val values: List<Product>
)

data class Elf(
    val name: String,
    val costsPerHour: Double
)

data class Elfs(
    val values: List<Elf>
)


class ProductCosts(
    val value: Double
) {
    companion object {
        fun calculate(deliveryPlan: DeliveryPlan, products: Products) = ProductCosts(
            value = deliveryPlan.deliveries.sumByDouble { delivery ->
                val product = products.values.find { it.name == delivery.productName }!!
                return@sumByDouble product.price
            }
        )
    }

    override fun toString() = "$$value"
}

class ElfLaborCosts(
    val value: Double
) {
    companion object {
        fun calculate(deliveryPlan: DeliveryPlan, elfs: Elfs) = ElfLaborCosts(
            value = deliveryPlan.deliveries.sumByDouble { delivery ->
                val elf = elfs.values.find { it.name == delivery.elfName }!!
                return@sumByDouble elf.costsPerHour * delivery.deliveryTimeInHours
            }
        )
    }

    override fun toString() = "$$value"
}

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

    // ???
}
