package de.maibornwolff.domainchoreograph.core.api

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.squareup.kotlinpoet.asClassName
import de.maibornwolff.domainchoreograph.core.processing.dependencygraph.DependencyNode
import de.maibornwolff.domainchoreograph.scenarios.choreographywithexception.ExceptionDomainObject
import de.maibornwolff.domainchoreograph.scenarios.choreographywithexception.TestException
import de.maibornwolff.domainchoreograph.scenarios.deepchoreography.*
import de.maibornwolff.domainchoreograph.scenarios.nestedchoreography.NestedDomainResult
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject1
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject2
import de.maibornwolff.domainchoreograph.scenarios.simplechoreography.SimpleDomainObject3
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ResolveDomainDefinitionTest {
    @Test
    fun `resolve a simple domain definition during runtime`() {
        val result: SimpleDomainObject3 = resolveDomainDefinition(
            SimpleDomainObject1(1),
            SimpleDomainObject2(2)
        )

        assertThat(result.sum).isEqualTo(3)
    }

    @Test
    fun `resolve a nested domain definition during runtime`() {
        val result: NestedDomainResult = resolveDomainDefinition(
            DeepDomainObject1(1),
            DeepDomainObject2(2)
        )

        assertThat(result.value).isEqualTo(3)
    }
}

class ResolveDomainDefinitionWithOptionsTest {
    @Test
    fun `resolve a simple domain definition during runtime with options`() {
        val logger: DomainLogger = mock()

        val object1 = SimpleDomainObject1(1)
        val object2 = SimpleDomainObject2(2)
        val result: SimpleDomainObject3 = resolveDomainDefinitionWithOptions(
            DomainChoreographyOptions(logger = setOf(logger)),
            object1,
            object2
        )

        val node1 = DependencyNode.VariableNode(
            type = SimpleDomainObject1::class.asClassName(),
            domainType = SimpleDomainObject1::class.asClassName(),
            name = "Parameter 0"
        )
        val node2 = DependencyNode.VariableNode(
            type = SimpleDomainObject2::class.asClassName(),
            domainType = SimpleDomainObject2::class.asClassName(),
            name = "Parameter 1"
        )
        val node3 = DependencyNode.FunctionNode(
            type = SimpleDomainObject3::class.asClassName(),
            domainType = SimpleDomainObject3::class.asClassName(),
            caller = SimpleDomainObject3::class.asClassName(),
            name = "create",
            parameters = listOf(
                node1,
                node2
            )
        )

        assertThat(result.sum).isEqualTo(3)
        val expected = DomainContext(
            schema = DomainChoreographySchema(
                rootNode = node3,
                nodeOrder = listOf(node1, node2, node3)
            ),
            nodes = mapOf(
                SimpleDomainObject1::class.java to DomainContextNode(object1),
                SimpleDomainObject2::class.java to DomainContextNode(object2),
                SimpleDomainObject3::class.java to DomainContextNode(result)
            ),
            choreographyInterface = null,
            exception = null
        )
        argumentCaptor<DomainContext> {
            verify(logger).onComplete(capture())
            val actual = firstValue
            assertThat(actual).isEqualTo(expected)
        }
    }

    @Test
    fun `resolve a nested domain definition during runtime with options`() {
        val logger: DomainLogger = mock()

        val object1 = DeepDomainObject1(2)
        val object2 = DeepDomainObject2(3)
        val object3 = DeepDomainObject3(5)
        val object4 = DeepDomainObject4(10.0)
        val object5 = DeepDomainObject5(5, 10.0)
        val result: NestedDomainResult = resolveDomainDefinitionWithOptions(
            DomainChoreographyOptions(logger = setOf(logger)),
            object1,
            object2
        )

        val node1 = DependencyNode.VariableNode(
            type = DeepDomainObject1::class.asClassName(),
            domainType = DeepDomainObject1::class.asClassName(),
            name = "Parameter 0"
        )
        val node2 = DependencyNode.VariableNode(
            type = DeepDomainObject2::class.asClassName(),
            domainType = DeepDomainObject2::class.asClassName(),
            name = "Parameter 1"
        )
        val node3 = DependencyNode.FunctionNode(
            type = DeepDomainObject3::class.asClassName(),
            domainType = DeepDomainObject3::class.asClassName(),
            caller = DeepDomainObject3::class.asClassName(),
            name = "create",
            parameters = listOf(
                node1,
                node2
            )
        )
        val node4 = DependencyNode.FunctionNode(
            type = DeepDomainObject4::class.asClassName(),
            domainType = DeepDomainObject4::class.asClassName(),
            caller = DeepDomainObject4::class.asClassName(),
            name = "create",
            parameters = listOf(node3)
        )
        val node5 = DependencyNode.FunctionNode(
            type = DeepDomainObject5::class.asClassName(),
            domainType = DeepDomainObject5::class.asClassName(),
            caller = DeepDomainObject5::class.asClassName(),
            name = "create",
            parameters = listOf(node3, node4)
        )
        val deepChoreographyNode = DependencyNode.ChoreographyNode(
            type = DeepChoreography::class.asClassName(),
            domainType = DeepChoreography::class.asClassName(),
            caller = NestedDomainResult::class.asClassName()
        )
        val nestedDomainResultNode = DependencyNode.FunctionNode(
            type = NestedDomainResult::class.java.asClassName(),
            domainType = NestedDomainResult::class.java.asClassName(),
            caller = NestedDomainResult::class.java.asClassName(),
            name = "create",
            parameters = listOf(
                node1,
                node2,
                deepChoreographyNode
            )
        )

        assertThat(result.value).isEqualTo(10)
        val expected = DomainContext(
            schema = DomainChoreographySchema(
                rootNode = nestedDomainResultNode,
                nodeOrder = listOf(node1, node2, deepChoreographyNode, nestedDomainResultNode)
            ),
            nodes = mapOf(
                DeepDomainObject1::class.java to DomainContextNode(object1),
                DeepDomainObject2::class.java to DomainContextNode(object2),
                DeepChoreography::class.java to DomainContextNode(mock()),
                NestedDomainResult::class.java to DomainContextNode<NestedDomainResult>(
                    value = result,
                    subContextMapping = mapOf(
                        DeepChoreography::class.java to listOf(
                            DomainContext(
                                choreographyInterface = DeepChoreography::class.java,
                                schema = DomainChoreographySchema(
                                    rootNode = node5,
                                    nodeOrder = listOf(
                                        node1,
                                        node2,
                                        node3,
                                        node4,
                                        node5
                                    )
                                ),
                                nodes = mapOf(
                                    DeepDomainObject1::class.java to DomainContextNode(object1),
                                    DeepDomainObject2::class.java to DomainContextNode(object2),
                                    DeepDomainObject3::class.java to DomainContextNode(object3),
                                    DeepDomainObject4::class.java to DomainContextNode(object4),
                                    DeepDomainObject5::class.java to DomainContextNode(object5)
                                )
                            )
                        )
                    )
                )
            ),
            choreographyInterface = null,
            exception = null
        )
        argumentCaptor<DomainContext> {
            verify(logger).onComplete(capture())
            val actual = firstValue
            assertThat(actual.schema).isEqualTo(expected.schema)
            assertThat(actual.nodes.size).isEqualTo(4)
            assertThat(actual.nodes.keys).isEqualTo(expected.nodes.keys)
            assertThat(actual.nodes[DeepDomainObject1::class.java]).isEqualTo(expected.nodes[DeepDomainObject1::class.java])
            assertThat(actual.nodes[DeepDomainObject2::class.java]).isEqualTo(expected.nodes[DeepDomainObject2::class.java])
            assertThat(actual.nodes[NestedDomainResult::class.java]).isEqualTo(expected.nodes[NestedDomainResult::class.java])
        }
    }

    @Test
    fun `resolve a domain definition with exception during runtime with options`() {
        val logger: DomainLogger = mock()

        val object1 = SimpleDomainObject1(1)
        val object2 = SimpleDomainObject2(2)

        val node1 = DependencyNode.VariableNode(
            type = SimpleDomainObject1::class.asClassName(),
            domainType = SimpleDomainObject1::class.asClassName(),
            name = "Parameter 0"
        )
        val node2 = DependencyNode.VariableNode(
            type = SimpleDomainObject2::class.asClassName(),
            domainType = SimpleDomainObject2::class.asClassName(),
            name = "Parameter 1"
        )
        val node3 = DependencyNode.FunctionNode(
            type = ExceptionDomainObject::class.asClassName(),
            domainType = ExceptionDomainObject::class.asClassName(),
            caller = ExceptionDomainObject::class.asClassName(),
            name = "create",
            parameters = listOf(
                node1,
                node2
            )
        )
        val expectedException = TestException()
        val expected = DomainContext(
            schema = DomainChoreographySchema(
                rootNode = node3,
                nodeOrder = listOf(node1, node2, node3)
            ),
            nodes = mapOf(
                SimpleDomainObject1::class.java to DomainContextNode(object1),
                SimpleDomainObject2::class.java to DomainContextNode(object2),
                ExceptionDomainObject::class.java to DomainContextNode(
                    value = null,
                    exception = expectedException
                )
            ),
            choreographyInterface = null,
            exception = expectedException
        )

        var exception: TestException? = null
        try {
            resolveDomainDefinitionWithOptions<ExceptionDomainObject>(
                DomainChoreographyOptions(logger = setOf(logger)),
                object1,
                object2
            )
        } catch (e: TestException) {
            exception = e
        }

        assertThat(exception).isEqualTo(expectedException)
        argumentCaptor<DomainContext> {
            verify(logger).onComplete(capture())
            val actual = firstValue
            assertThat(actual).isEqualTo(expected)
        }
    }
}
