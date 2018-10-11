package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies

import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.kotlinpoet.asClassName
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.core.api.*
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies.ArticlePriceCalculator
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies.OrderPriceCalculator
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies.OrderPriceCalculatorWithImplementation
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.*
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.services.ArticlePriceServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class NestedChoreographiesScenarioTests {

    @Test
    fun `should work with nested Choreographies`() {
        val orderPriceCalculator = DomainEnvironment().get(OrderPriceCalculator::class.java)

        val price = orderPriceCalculator.calculate(
            order = Order(setOf(
                Article("iphone"),
                Article("car")
            )),
            articlePriceService = ArticlePriceServiceImpl(
                "iphone" to 999f,
                "car" to 10_000f
            )
        )

        assertEquals(OrderPrice(10_999f), price)
    }

    @Test
    fun `should work with nested Choreographies and mocked Service`() {
        val orderPriceCalculator = DomainEnvironment().get(OrderPriceCalculator::class.java)

        val articlePriceService: ArticlePriceService = mock()
        whenever(articlePriceService.getPrice("iphone")).thenReturn(999f)
        whenever(articlePriceService.getPrice("car")).thenReturn(10_000f)

        val price = orderPriceCalculator.calculate(
            order = Order(setOf(
                Article("iphone"),
                Article("car")
            )),
            articlePriceService = articlePriceService
        )

        assertEquals(OrderPrice(10_999f), price)
    }

    @Test
    fun `should work with logger`() {
        // given
        val logger: DomainLogger = mock()
        val environment = DomainEnvironment(
            logger = setOf(logger)
        )
        val orderPriceCalculator = environment.get(
            OrderPriceCalculatorWithImplementation::class.java
        )

        val order = Order(setOf(
            Article("iphone"),
            Article("car")
        ))
        val articlePriceService = ArticlePriceServiceImpl(
            "iphone" to 999f,
            "car" to 10_000f
        )
        orderPriceCalculator.calculate(
            order = order,
            articlePriceService = articlePriceService
        )
        val expected = DomainContext(
            nodes = mapOf(
                Order::class.java to DomainContextNode(order),
                ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                OrderPrice::class.java to DomainContextNode<OrderPrice>(
                    OrderPrice(10_999f),
                    mapOf(
                        ArticlePriceCalculator::class.java to listOf(
                            DomainContext(
                                nodes = mapOf(
                                    Article::class.java to DomainContextNode(Article("iphone")),
                                    ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                                    ArticlePrice::class.java to DomainContextNode(ArticlePrice(999f))
                                ),
                                schema = environment.getMeta(ArticlePriceCalculator::class.java).schemas["calculate"]!!,
                                choreographyInterface = ArticlePriceCalculator::class.java
                            ),
                            DomainContext(
                                nodes = mapOf(
                                    Article::class.java to DomainContextNode(Article("car")),
                                    ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                                    ArticlePrice::class.java to DomainContextNode(ArticlePrice(10_000f))
                                ),
                                schema = environment.getMeta(ArticlePriceCalculator::class.java).schemas["calculate"]!!,
                                choreographyInterface = ArticlePriceCalculator::class.java
                            )
                        )
                    )
                )
            ),
            schema = environment.getMeta(OrderPriceCalculatorWithImplementation::class.java).schemas["calculate"]!!,
            choreographyInterface = OrderPriceCalculatorWithImplementation::class.java
        )

        argumentCaptor<DomainContext> {
            verify(logger).onComplete(capture())
            assertThat(firstValue).isEqualToComparingFieldByFieldRecursively(expected)
        }
    }

    @Test
    fun `should work with static plugins and exceptions`() {
        // given
        val logger: DomainLogger = mock()
        val environment = DomainEnvironment(
            logger = setOf(logger)
        )

        val orderPriceCalculator: OrderPriceCalculatorWithImplementation = environment.get()

        val order = Order(setOf(
            Article("iphone"),
            Article("car")
        ))
        val articlePriceService = ArticlePriceServiceImpl(
            "iphone" to 999f
        )
        val expectedException: Throwable = ArticlePriceNotFoundException()
        var actualException: Throwable? = null

        try {
            orderPriceCalculator.calculate(
                order = order,
                articlePriceService = articlePriceService
            )
        } catch (e: Exception) {
            actualException = e
        }

        assertEquals(expectedException, actualException)
        val expected = DomainContext(
            nodes = mapOf(
                Order::class.java to DomainContextNode(order),
                ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                OrderPrice::class.java to DomainContextNode<OrderPrice>(
                    null,
                    mapOf(
                        ArticlePriceCalculator::class.java to listOf(
                            DomainContext(
                                nodes = mapOf(
                                    Article::class.java to DomainContextNode(Article("iphone")),
                                    ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                                    ArticlePrice::class.java to DomainContextNode(ArticlePrice(999f))
                                ),
                                schema = environment.getMeta(ArticlePriceCalculator::class.java).schemas["calculate"]!!,
                                choreographyInterface = ArticlePriceCalculator::class.java
                            ),
                            DomainContext(
                                nodes = mapOf(
                                    Article::class.java to DomainContextNode(Article("car")),
                                    ArticlePriceService::class.java to DomainContextNode(articlePriceService),
                                    ArticlePrice::class.java to DomainContextNode(
                                        value = null,
                                        exception = expectedException
                                    )
                                ),
                                exception = expectedException,
                                schema = environment.getMeta(ArticlePriceCalculator::class.java).schemas["calculate"]!!,
                                choreographyInterface = ArticlePriceCalculator::class.java
                            )
                        )
                    ),
                    exception = expectedException
                )
            ),
            exception = expectedException,
            schema = environment.getMeta(OrderPriceCalculatorWithImplementation::class.java).schemas["calculate"]!!,
            choreographyInterface = OrderPriceCalculatorWithImplementation::class.java
        )

        argumentCaptor<DomainContext> {
            verify(logger).onComplete(capture())
            val actual = firstValue
            assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected)
        }
    }

    @Test
    fun `should generate meta data`() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val articlePriceCalculatorMeta: DomainChoreographyMeta = DomainEnvironment().getMeta(ArticlePriceCalculator::class.java)
        val articleNode = DependencyNode.VariableNode(
            type = Article::class.java.asClassName(),
            domainType = Article::class.java.asClassName(),
            name = "article"
        )
        val articlePriceServiceNode = DependencyNode.VariableNode(
            type = ArticlePriceService::class.java.asClassName(),
            domainType = ArticlePriceService::class.java.asClassName(),
            name = "articlePriceService"
        )
        val articlePriceCalculatorNode = DependencyNode.FunctionNode(
            type = ArticlePrice::class.java.asClassName(),
            domainType = ArticlePrice::class.java.asClassName(),
            caller = ArticlePrice::class.java.asClassName(),
            name = "calculate",
            parameters = listOf(articleNode, articlePriceServiceNode)
        )
        val expectedSchema = DomainChoreographySchema(
            articlePriceCalculatorNode,
            listOf(
                articleNode,
                articlePriceServiceNode,
                articlePriceCalculatorNode
            )
        )

        assertEquals(articlePriceCalculatorMeta.schemas.size, 1)
        assertEquals(
            gson.toJson(mapOf(
                "calculate" to expectedSchema
            )),
            gson.toJson(articlePriceCalculatorMeta.schemas)
        )
    }
}
