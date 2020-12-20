typealias Visitor<Element> = (Element) -> Unit

class BinaryNode<Element>(
    private val value: Element,
    var leftChild: BinaryNode<Element>? = null,
    var rightChild: BinaryNode<Element>? = null
)
{
    fun preorderTraversal(visit: Visitor<Element>) {
        visit(value)
        leftChild?.postorderTraversal(visit)
        rightChild?.postorderTraversal(visit)
    }

    private fun preorderTraversalWithNull(visit: Visitor<Element?>) {
        visit(value)
        leftChild?.preorderTraversalWithNull(visit) ?: visit(null)
        rightChild?.preorderTraversalWithNull(visit) ?: visit(null)
    }

    fun postorderTraversal(visit: Visitor<Element>) {
        leftChild?.preorderTraversal(visit)
        visit(value)
        rightChild?.preorderTraversal(visit)
    }

    // serializace objektu do jiného datového typu
    fun serialize(node: BinaryNode<Element> = this): MutableList<Element?> {
        val list = mutableListOf<Element?>()
        node.preorderTraversalWithNull { list.add(it) }
        return list
    }

    fun deserialize(list: MutableList<Element?>): BinaryNode<Element?>? {
        // 1
        val rootValue = list.removeAt(list.size - 1) ?: return null

        // 2
        val root = BinaryNode<Element?>(rootValue)

        root.leftChild = deserialize(list)
        root.rightChild = deserialize(list)

        return root
    }

    override fun toString() = diagram(this)
    private fun diagram(node: BinaryNode<Element>?,
                        top: String = "",
                        root: String = "",
                        bottom: String = ""): String {
        return node?.let {
            if (node.leftChild == null && node.rightChild == null) {
                "$root${node.value}\n"
            } else {
                diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
                        root + "${node.value}\n" + diagram(node.leftChild, "$bottom│ ", "$bottom└──", "$bottom ")
            }
        } ?: "${root}null\n"
    }

}


fun mainTree(){

    // definice stromu - viz obrázek
    val zero = BinaryNode(0)
    val one = BinaryNode(1)
    val two = BinaryNode(2)
    val four = BinaryNode(4)
    val five = BinaryNode(5)
    val tree = BinaryNode(3) // root
    tree.leftChild = four
    tree.rightChild = five
    four.leftChild = zero
    four.rightChild = one
    five.leftChild = two

    //generování ukázek
    println("preorderTraversal")
    println("Prochází se stromem nejprve kořen, poté se rekurzivně prochází podstromy jeho potomků.")
    tree.preorderTraversal { println(it) }
    println()
    println("preorderTraversal")
    println("Prochází se stromem nejprve podstromy, poté se rekurzivně prochází jejich kořeny.")
    tree.postorderTraversal{ println(it) }
    println()
    println("Diagram")
    println(tree)
    println()
    println("Serializace")
    val array = tree.serialize()
    println(array.forEach{ print("|$it")})
    println("Deserializace")
    println(tree.deserialize(array))

}