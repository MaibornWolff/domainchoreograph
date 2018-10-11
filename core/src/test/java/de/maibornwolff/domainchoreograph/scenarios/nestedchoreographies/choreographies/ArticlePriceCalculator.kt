package de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.choreographies

import de.maibornwolff.domainchoreograph.core.api.DomainChoreography
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.Article
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.ArticlePrice
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreographies.domaintypes.ArticlePriceService

@DomainChoreography
interface ArticlePriceCalculator {
    fun calculate(article: Article, articlePriceService: ArticlePriceService): ArticlePrice
}
