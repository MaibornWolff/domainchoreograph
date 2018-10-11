package de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces

import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.choreographies.Cashier
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.Article
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.Basket
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.domaindefinitions.BasketPrice
import de.maibornwolff.domainchoreograph.scenarios.domaindefinitionswithinterfaces.serviceimplementations.PriceServiceImplementation
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DomainDefinitionsWithInterfacesTests {

    @Test
    fun `it should work with interfaces`() {

        // given
        val basket = Basket(listOf(
            Article("apple"),
            Article("banana")
        ))

        val priceService = PriceServiceImplementation()

        val cashier = DomainEnvironment().get(Cashier::class.java)

        // when
        val price = cashier.checkout(
            basket,
            priceService
        )

        // then
        assertThat(price).isEqualTo(BasketPrice(1.5))
    }
}
