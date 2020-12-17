package gettingStarted

import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.IllegalArgumentException

val list = listOf(-3, -2, -1, 0, 1, 2, 3)
val nameList = listOf("wdy", "WDY")


fun main() {
    val name = "王小虎"

    //检查某个元素是否在集合里面
    if ("wdy" in nameList) {
        println("wdy is in the name list")
    }
    if ("wdy" !in nameList) {
        println("wdy is not in the name list")
    }


    //String Interpolation
    println("Name $name")


    //实例检查
//    when (x) {
//        is Foo -> //...
//        if Bar -> //...
//        else   -> //...
//    }


    //遍历map 或者 成对列表(list of pairs)
//    for ((k,v) in map) {
//        println("$k -> $v")
//    }


    //Ranges的使用
    for (i in 1..100) {
    } //包含100
    for (i in 1 until 100) {
    } //不包含100
    for (i in 10 downTo 1) {
    } //倒序
    //if (x in 1..10) { }


    //只读list 和 只读map
    val list = listOf("a", "b", "c")
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    val map1 = mutableMapOf("a" to 1, "b" to 2, "c" to 3)
    //访问map
    println(map["a"])
    map1["a"] = 10 //前提是map可以被修改


    //Lazy property ???
//    val p: String by lazy {
//        // cmpute the string
//    }


    //拓展函数
    fun String.spaceToCamelCase() {
        //...
    }
    "Convert this to camelcase".spaceToCamelCase()


    //创建单例
//    object Resource {
//        val name0 = "Name"
//    }


    //if not null 的简写
    val files = File("Test").listFiles()
    println(files?.size)
    //if not null and else 的简写
    val files1 = File("Test").listFiles()
    println(files?.size ?: "empty")


    //执行语句if null
    val values = mapOf("wdy" to 1, "email" to 10)
    val email = values["email"] ?: throw IllegalStateException("Email is missing")


    //从一个可能为空的集合中获取第一个元素
    val emails = listOf("2664683329@qq.com")
    val mainEmail = emails.firstOrNull() ?: ""


    //Execute if not null
//    val value0 = ...
//    value0?.let {
//        ... // 执行这个语句如果不为空的话
//    }


    //val value1 = listOf("")
    // defaultValue is returned if the value or the transformValue result is null
    //val mapped = value1?.let { transformValue(it) } ?: defaultValue


    //Return on when statement
    fun transform(color: String): Int {
        return when (color) {
            "Res" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")

        }
    }


    // 'try/catch' expression
//    fun test() {
//        val result = try {
//            count()
//        } catch (e: ArithmeticException) {
//            throw IllegalStateException(e)
//        }
    //working with result
//    }


    // if 表达式
    fun foo(param: Int) {
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "three"
        }
    }


    //Builder-style usage of methods that return Uint
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }


    //***** Single-expression functions ******重要
    fun theAnswer() = 42

    //等价于
    fun theAnswer1(): Int {
        return 42
    }

    //可以和 when 搭配使用，使代码量更少
    fun transform1(color: String): Int = when (color) {
        "Red" -> 0
        else -> throw IllegalArgumentException("Invalid color param value")
    }


    //在一个对象实例上调用多个方法(with)
    class Turtle {
        fun penDown() {}
        fun penUp() {}
        fun turn(degrees: Double) {}
        fun forward(pixels: Double) {}
    }

    val myTurtle = Turtle()
    with(myTurtle) {
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }


    //使用apply配置一个对象的属性
//    val myRectangle = Rectangle().apply {
//        length = 4
//        breadth = 5
//        color = 0xFAFAFA
//    }
//    //这个在配置某对象的constructor里面没有的属性时非常有用


    //Java 7's try with resources
    val stream = Files.newInputStream(Paths.get("/some/file.txt"))
    stream.buffered().reader().use { reader ->
        println(reader.readText())
    }


    //Consuming a nullable Boolean
    val b1: Boolean? = null
    if (b1 == true) {
        //...
    } else {
        // b是false或者null
    }


    //***************两个变量的交换***************
    var a = 1
    var b = 2
    a = b.also { b = a }


    //TODO(): Making code as incomplete
    //kotlin的标准库有TODO()这个函数，并且这个函数总是会抛出`NotImplementedError`
    //这个在界面的TODO窗口里面可以看到
    fun calcTaxes(): BigDecimal = TODO("Waiting for feedback from accounting")
    println("hello")
}

//函数参数拥有默认值
fun foo(a: Int = 0, b: String = "") {
    //do something
}

//按需求筛选列表元素
val positives = list.filter { x -> x > 0 }
val positives1 = list.filter { it > 0 }






