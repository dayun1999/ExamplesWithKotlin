package gettingStarted

val PI = 3.14 //Top-Level variable

fun main() {
    /*
    这是注释
     */

    //变量声明
    val a: Int = 1 //显示声明
    val b = 2 //编译器自动推测b类型为Int

    //基本打印语句
    println("hello world!!!")

    //函数调用
    printSum(2, 2)

    //字符串模板
    var a1 = 1
    val s1 = "a is $a1"
    a1 = 2
    val s2 = "${s1.replace("is","was")}, but " +
            "now is $a1"
    println(s2)

    //for loop 循环
    val items = listOf("apple", "banana", "mango")
    for (item in items) {
        println(item)
    }
    /*
    or
     */
    val items1 = listOf("apple", "banana", "mango")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }

    //while loop while循环
    val items2 = listOf("apple", "banana", "mango")
    var index = 0
    while (index < items2.size) {
        println("item at $index is ${items2[index]}")
        index++
    }

    //when表达式
    fun describe(obj: Any): String =
        when (obj) {
            1           -> "One"
            "Hello"     -> "Greeting"
            is Long     -> "Long"
            !is String  -> "Not a String"
            else        -> "Unknown"
        }

    //Ranges--检查一个数是否在一个范围里面 in关键字
    val x = 10
    val y = 9
    if (x in 1..y+1) {
        println("fits in range")
    }
    //Ranges--检查一个数是否不在一个范围里面
    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range, too")
    }
    //Ranges--遍历
    for (x in 1..5) {
        print(x)
    }
    println()
    for (x in 1..10 step 2) {
        print(x)
    }
    println()

    //Collections 集合
    //遍历
//    for(item in items) {
//        println(item)
//    }
    //检查某个元素是否属于集合
//    when {
//        "orange" in items -> println("juicy")
//        "apple"  in items -> println("apple is fine too")
//    }
    //使用lambda表达式过滤和map集合
    val fruits = listOf("banana", "avocado", "apple", "kiwi")
    fruits
            .filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println(it) }

    //创建类的实例
//    val rectangle = Rectangle(5.0, 2.0)












}


//类型检查和自动转换
fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        //下面obj就会自动被转换为String类型的了
        return obj.length
    }
    //在类型检查之外的代码, obj任然是`Any`类型
    return null
}
/*
or
 */
fun getStringlength1(obj: Any): Int? {
    if (obj !is String) return null

    //现在`obj`被自动转换到String类型了
    return obj.length
}
/*
or even
 */
fun getStringLength2(obj: Any): Int? {
    //在&&的右边，obj被自动转换为String类型了
    if (obj is String && obj.length > 0) {
        return obj.length
    }
    return null
}

//空检查-用问号显式表示可以返回空值
fun parseInt(str: String): Int? {
    return null //return null if str does
}
//空检查-不加问号表示直接返回空值
fun printProduct(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    //使用 `x * y` 可能会产生错误，因为他们可能含有null
    if (x != null && y != null) {
        // x 和 y 在经过空值检查之后会自动被转换为非空
    }
    else {
        println("'$arg1' or '$arg2' is not a number")
    }
}

//条件语句
fun max0f(a: Int, b: Int): Int {
    return if (a > b) {
        a
    } else {
        b
    }
} //等价于下面的语句
fun maxOf(a: Int, b: Int) = if (a >b) a else b


fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum0(a: Int, b: Int) = a + b

fun printSum(a:Int, b: Int) {
    println("Sum of $a and $b is ${a + b}")
}