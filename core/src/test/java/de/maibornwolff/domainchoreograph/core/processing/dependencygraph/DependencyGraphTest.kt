package de.maibornwolff.domainchoreograph.core.processing.dependencygraph

import com.squareup.kotlinpoet.asClassName
import de.maibornwolff.domainchoreograph.core.processing.reflection.asReflectionType
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.*
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreography.NestedChoreography
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreography.NestedDomainResult
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleChoreography
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject2
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject3
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DependencyGraphTest {
    @Test
    fun `create with simple choreography`() {
        val choreography = SimpleChoreography::class.asReflectionType()
        val domainMethod = choreography.enclosedMethods[0]
        val graph = DependencyGraph.create(
            domainMethod.returnType,
            domainMethod.parameters
        )

        val d1Node = DependencyNode.VariableNode(
            type = SimpleDomainObject1::class.asClassName(),
            domainType = SimpleDomainObject1::class.asClassName(),
            name = "d1"
        )
        val d2Node = DependencyNode.VariableNode(
            type = SimpleDomainObject2::class.asClassName(),
            domainType = SimpleDomainObject2::class.asClassName(),
            name = "d2"
        )
        val d3Node = DependencyNode.FunctionNode(
            type = SimpleDomainObject3::class.asClassName(),
            domainType = SimpleDomainObject3::class.asClassName(),
            caller = SimpleDomainObject3::class.asClassName(),
            name = "create",
            parameters = listOf(d1Node, d2Node)
        )

        assertThat(graph.nodes).containsExactly(
            d1Node,
            d2Node,
            d3Node
        )
        assertThat(graph.target).isEqualTo(d3Node)
    }

    @Test
    fun `create with deep choreography`() {
        val choreography = DeepChoreography::class.asReflectionType()
        val domainMethod = choreography.enclosedMethods[0]
        val graph = DependencyGraph.create(
            domainMethod.returnType,
            domainMethod.parameters
        )

        val d1Node = DependencyNode.VariableNode(
            type = DeepDomainObject1::class.asClassName(),
            domainType = DeepDomainObject1::class.asClassName(),
            name = "d1"
        )
        val d2Node = DependencyNode.VariableNode(
            type = DeepDomainObject2::class.asClassName(),
            domainType = DeepDomainObject2::class.asClassName(),
            name = "d2"
        )
        val d3Node = DependencyNode.FunctionNode(
            type = DeepDomainObject3::class.asClassName(),
            domainType = DeepDomainObject3::class.asClassName(),
            caller = DeepDomainObject3::class.asClassName(),
            name = "create",
            parameters = listOf(d1Node, d2Node)
        )
        val d4Node = DependencyNode.FunctionNode(
            type = DeepDomainObject4::class.asClassName(),
            domainType = DeepDomainObject4::class.asClassName(),
            caller = DeepDomainObject4::class.asClassName(),
            name = "create",
            parameters = listOf(d3Node)
        )
        val d5Node = DependencyNode.FunctionNode(
            type = DeepDomainObject5::class.asClassName(),
            domainType = DeepDomainObject5::class.asClassName(),
            caller = DeepDomainObject5::class.asClassName(),
            name = "create",
            parameters = listOf(d3Node, d4Node)
        )

        assertThat(graph.nodes).containsExactly(
            d1Node,
            d2Node,
            d3Node,
            d4Node,
            d5Node
        )
        assertThat(graph.target).isEqualTo(d5Node)
    }

    @Test
    fun `create with nested choreography`() {
        val choreography = NestedChoreography::class.asReflectionType()
        val domainMethod = choreography.enclosedMethods[0]
        val graph = DependencyGraph.create(
            domainMethod.returnType,
            domainMethod.parameters
        )

        val d1Node = DependencyNode.VariableNode(
            type = DeepDomainObject1::class.asClassName(),
            domainType = DeepDomainObject1::class.asClassName(),
            name = "d1"
        )
        val d2Node = DependencyNode.VariableNode(
            type = DeepDomainObject2::class.asClassName(),
            domainType = DeepDomainObject2::class.asClassName(),
            name = "d2"
        )
        val deepChoreographyNode = DependencyNode.ChoreographyNode(
            type = DeepChoreography::class.asClassName(),
            domainType = DeepChoreography::class.asClassName(),
            caller = NestedDomainResult::class.asClassName()
        )
        val resultNode = DependencyNode.FunctionNode(
            type = NestedDomainResult::class.asClassName(),
            domainType = NestedDomainResult::class.asClassName(),
            caller = NestedDomainResult::class.asClassName(),
            name = "create",
            parameters = listOf(d1Node, d2Node, deepChoreographyNode)
        )

        assertThat(graph.nodes).containsExactly(
            d1Node,
            d2Node,
            deepChoreographyNode,
            resultNode
        )
        assertThat(graph.target).isEqualTo(resultNode)
    }
}
