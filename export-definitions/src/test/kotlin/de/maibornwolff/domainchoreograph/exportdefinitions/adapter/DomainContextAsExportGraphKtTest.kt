package de.maibornwolff.domainchoreograph.exportdefinitions.adapter

import de.maibornwolff.domainchoreograph.core.api.DomainContext
import de.maibornwolff.domainchoreograph.core.api.DomainChoreographyOptions
import de.maibornwolff.domainchoreograph.core.api.DomainEnvironment
import de.maibornwolff.domainchoreograph.exportdefinitions.model.*
import de.maibornwolff.domainchoreograph.scenarios.orderprice.choreographies.OrderPriceCalculator
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


internal class DomainContextAsExportGraphKtTest {
    @Test
    fun `it should map the context to the export graph`() {
        val calls = mutableListOf<DomainContext>()
        val orderPriceCalculator = DomainEnvironment().get(
            OrderPriceCalculator::class.java,
            DomainChoreographyOptions(calls = calls)
        )

        val order = Order(setOf(
            Article("iphone"),
            Article("car")
        ))
        val articlePriceService = ArticlePriceServiceImpl(
            "iphone" to 999f
        )

        var expectedException: ExportException? = null
        try {
            orderPriceCalculator.calculate(
                order = order,
                articlePriceService = articlePriceService
            )
        } catch (err: ArticlePriceNotFoundException) {
            expectedException = ExportException(
                detailMessage = err.message ?: "",
                stackTrace = err.stackTrace.map { it.toString() },
                suppressedExceptions = err.suppressed.map { it.toString() }
            )
        }
        val context: DomainContext = calls[0]
        val actual = context.asExportGraph("id")

        val expected = ExportGraph(
            id = "id",
            nodes = mapOf(
                "order" to ExportNode(
                    id = "order",
                    name = "Order",
                    value = order,
                    hasException = false,
                    scope = "root",
                    type = Order::class.java
                ),
                "articlePriceService" to ExportNode(
                    id = "articlePriceService",
                    name = "ArticlePriceService",
                    value = articlePriceService,
                    hasException = false,
                    scope = "root",
                    type = ArticlePriceService::class.java
                ),
                "orderPrice" to ExportNode(
                    id = "orderPrice",
                    name = "OrderPrice",
                    value = null,
                    hasException = true,
                    exception = expectedException,
                    scope = "root",
                    type = OrderPrice::class.java
                ),
                "orderPrice::0::article" to ExportNode(
                    id = "orderPrice::0::article",
                    name = "Article",
                    value = Article("iphone"),
                    hasException = false,
                    scope = "orderPrice::0",
                    type = Article::class.java
                ),
                "orderPrice::0::articlePrice" to ExportNode(
                    id = "orderPrice::0::articlePrice",
                    name = "ArticlePrice",
                    value = ArticlePrice(999f),
                    hasException = false,
                    scope = "orderPrice::0",
                    type = ArticlePrice::class.java
                ),
                "orderPrice::0::articlePriceService" to ExportNode(
                    id = "orderPrice::0::articlePriceService",
                    name = "ArticlePriceService",
                    value = articlePriceService,
                    hasException = false,
                    scope = "orderPrice::0",
                    type = ArticlePriceService::class.java
                ),
                "orderPrice::1::article" to ExportNode(
                    id = "orderPrice::1::article",
                    name = "Article",
                    value = Article("car"),
                    hasException = false,
                    scope = "orderPrice::1",
                    type = Article::class.java
                ),
                "orderPrice::1::articlePrice" to ExportNode(
                    id = "orderPrice::1::articlePrice",
                    name = "ArticlePrice",
                    value = null,
                    hasException = true,
                    exception = expectedException,
                    scope = "orderPrice::1",
                    type = ArticlePrice::class.java
                ),
                "orderPrice::1::articlePriceService" to ExportNode(
                    id = "orderPrice::1::articlePriceService",
                    name = "ArticlePriceService",
                    value = articlePriceService,
                    hasException = false,
                    scope = "orderPrice::1",
                    type = ArticlePriceService::class.java
                )
            ),
            dependencies = listOf(
                ExportDependency("order", "orderPrice"),
                ExportDependency("articlePriceService", "orderPrice"),
                ExportDependency("orderPrice::0::article", "orderPrice::0::articlePrice"),
                ExportDependency("orderPrice::0::articlePriceService", "orderPrice::0::articlePrice"),
                ExportDependency("orderPrice::1::article", "orderPrice::1::articlePrice"),
                ExportDependency("orderPrice::1::articlePriceService", "orderPrice::1::articlePrice")
            ),
            scopes = mapOf(
                "root" to ExportScope(
                    id = "root",
                    executionContext = "Application",
                    nodes = mutableListOf("order", "articlePriceService", "orderPrice")
                ),
                "orderPrice::0" to ExportScope(
                    id = "orderPrice::0",
                    executionContext = "orderPrice",
                    nodes = mutableListOf("orderPrice::0::article", "orderPrice::0::articlePriceService", "orderPrice::0::articlePrice")
                ),
                "orderPrice::1" to ExportScope(
                    id = "orderPrice::1",
                    executionContext = "orderPrice",
                    nodes = mutableListOf("orderPrice::1::article", "orderPrice::1::articlePriceService", "orderPrice::1::articlePrice")
                )
            ),
            executionContexts = mapOf(
                "Application" to ExportExecutionContext(
                    id = "Application",
                    scopes = mutableListOf("root")
                ),
                "orderPrice" to ExportExecutionContext(
                    id = "orderPrice",
                    scopes = mutableListOf("orderPrice::0", "orderPrice::1")
                )
            )
        )

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected)
    }
}
