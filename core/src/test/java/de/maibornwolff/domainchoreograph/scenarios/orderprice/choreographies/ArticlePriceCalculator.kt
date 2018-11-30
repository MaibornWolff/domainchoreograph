package de.maibornwolff.domainchoreograph.scenarios.orderprice.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.Article
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.ArticlePrice
import de.maibornwolff.domainchoreograph.scenarios.orderprice.domaintypes.ArticlePriceService

@DomainChoreography
interface ArticlePriceCalculator {
    fun calculate(article: Article, articlePriceService: ArticlePriceService): ArticlePrice
}
