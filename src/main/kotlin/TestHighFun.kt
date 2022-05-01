import sun.invoke.empty.Empty

fun main() {
    max(1f, -1f, 2.5f, 5f, 244f, -42f)

    val numbers = mutableListOf("one", "two", "three")
    numbers.also { println("在列表添加新元素: $it") }
        .add("four")
}

fun max1(vararg nums: Int): Int {
    var maxNum = nums[0]
    for (num in nums) {
        println("maxNum=$maxNum, num=$num")
        maxNum = Math.max(maxNum, num)
    }
    return maxNum
}

fun <T : Comparable<T>> max(vararg nums: T): T {
    if(nums is Empty) throw RuntimeException("Vararg cannot be Empty.")
    var maxNum: T = nums[0]
    for (num in nums) {
        if (maxNum < num) {
            maxNum = num
        }
    }
    println("maxNum is $maxNum")
    return maxNum
}