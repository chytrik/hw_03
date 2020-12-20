class BinaryNode<Element>(
    val value: Element,
    var leftChild: BinaryNode<Element>? = null,
    var rightChild: BinaryNode<Element>? = null
) {
    fun preorderTraversal(){
        performAction()
        leftChild?.preorderTraversal()
        rightChild?.preorderTraversal()
    }
    fun postorderTraversal(){
        leftChild?.postorderTraversal()
        rightChild?.postorderTraversal()
        performAction()
    }
    private fun performAction(){
        println("Navštíven uzel s hodnotou: $value")
    }
}

fun manTree(){
    val zero = BinaryNode(0)
    val one = BinaryNode(1)
    val two = BinaryNode(2)
    val four = BinaryNode(4)
    val five = BinaryNode(5)
    val tree = BinaryNode(3)

    tree.leftChild = four
    tree.rightChild = five
    four.leftChild = zero
    four.rightChild = one
    five.leftChild = two
    println("preorderTraversal")
    println("Prochází se stromem nejprve kořen, poté se rekurzivně prochází podstromy jeho potomků.")
    tree.preorderTraversal()
    println()
    println("preorderTraversal")
    println("Prochází se stromem nejprve podstromy, poté se rekurzivně prochází jejich kořeny.")
    tree.postorderTraversal()
}