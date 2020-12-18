# ExamplesWithKotlin
- [类和继承 Class and Inheritance](https://github.com/code4EE/ExamplesWithKotlin/blob/main/%E7%B1%BB%E5%92%8C%E7%BB%A7%E6%89%BF.md)

## Kotlin 的基本语法
- #### var 和 val<br>
`var` 和 `val`都是kotlin中声明变量的方式，区别是var声明为可变变量，
而val声明的是不可变变量，相当于java中的final<br>
在kotlin中, 还有个`const`
```kotlin
1. const必须修饰val
2. const只允许在top-level级别和object中声明
```
##### `const val` 和 `val` 的区别如下:
- const val 可见性为public final static, 可以直接访问
- val 可见性为private final static, 并且val会生成方法getNormalObject(),
通过方法调用访问
当定义常量的时候，推荐使用const val方式


- #### String? 与可空性<br>
`String?`后面的问号(?)是怎么回事？<br>
Kotlin里面通过在类型后面放置`?`来指定相关的变量可以为空(`null`)<br>

- #### 基本数据类型<br>
类型----大小(位)<br>
Byte 8 <br>
Short 16 <br>
Int 32 <br>
Long   64<br>
Float  32  有效位 24 指数位 8   小数点位数 6-7<br>
Double 64  有效位 53 指数位 11  小数点位数 15-16<br>
```kotlin
val a = 1.1 //小数默认为Double类型
val b = 1.1F //显示说明为float类型
val c = 0x0F //16进制
val d = 0b00001011 //2进制
val e = 123.5e10 //支持科学计数法(默认为Double类型)

//可以使用下划线来提升可读性
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010

```
**注意!!!** kotlin不支持8进制<br>


- #### 类型转化<br>
kotlin中,更小的类型不是更大类型的子类,因此小类型不能隐式的转换为大类型<br>
```kotlin
//例子1
val a: Int? = 1  // a boxed Int
val b: Long? = a // 隐式转换,编译失败
print(b == a) //false

//例子2
val b: Byte = 1 // OK
val i: Int = b // 错误

//例子3--正确的做法
val b: Byte = 1
val i: Int = b.toInt()
println(i) // 1
```
每个数字类型支持以下的转换<br>
```kotlin
toByte(): Byte
toShort(): Short
toInt(): Int
toLong(): Long
toFloat(): Float
toDouble(): Double
toChar(): Char
```
隐式转换的缺乏很少引起注意,因为类型是从上下文推断出来的,
并且算术运算对于适当的转换是重载的,比如:
```kotlin
val l = 1L + 3 // Long + Int => Long
```

- #### 基本运算
```kotlin
// ** 整数除法 Division of integers
val x = 5 / 2
println(x == 2) // true
println(x == 2.5) //false

val x = 5L / 2
println(x == 2L) //true

//为了返回一个浮点数,显式将一个参数转化为浮点类型即可
val x = 5 / 2.toDouble()
println(x == 2.5) // true





// ** 位操作 Bitwise operations
/** 对于kotlin的位操作运算,只有有名字的而且可以以infix形式调用的函数*/
val x = (1 shl 2) and 0x000FF000

shl(bits)  // 左移<<运算 signed shift left
shr(bits)  // 右移>>运算 signed shift right
ushr(bits) // 无符号右移 unsigned shift right
and(bits)  // 与(&)
or(bits)   // 或(|)
xor(bits)  // 异或(^)
inv(bits)  // 取反(!)




// ** 浮点数的比较
// 1. NaN 和自身相等
// 2. NaN 比任何数都大,包括POSITIVE_INFINITY
// 3. -0.0 比0.0小
```


- #### Characters<br>
kotlin中`Char`被用来表示字符,而且并不能直接被当做数字<br>
```kotlin
fun check(c: Char) {
    if (c==1) { //ERROR: incompatible types
        // ...
    }
}
```
正常表示一个字符为`1`, 特殊字符` \n \t \b \r \\ \" \' \$ `, 支持Unicode
字符: ` '\uFF00' `,注意单引号<br>
```kotlin
// 将Char转为Int类型数字
fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.toInt() - '0'.toInt() //显式转换
}
```



- #### Booleans
`Boolean`类型只有`true` 和 `false`两个值<br>
如果需要可为空的引用，则将布尔值装箱(boxed)<br>
三个运算: `||  &&   !`  



- #### Arrays
kotlin中的数组(Arrays)用`Array`这个类来表示的
```kotlin
class Array<T> private constructor() {
    val size: Int
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T): UInt
    
    operator fun iterator(): Interator<T>
    // ...
}
```
##### 如何创建一个数组？<br>
1.使用标准库中的`arrayOf()`函数就可以了<br>
`arrayOf(1, 2, 3)`代表创建了数组`[1, 2, 3]`, 另外`arrayOfNulls()`函数可以
创建一个给定大小的元素为空的数组<br>
2.使用`Array`构造函数
```kotlin
// 创建数组["0", "1", "4", "9", "16"]
val asc = Array(5) { i -> (i * i).toString() } // 第一个i是索引
asc.forEach { println(it) } // it 是kotlin的编码习惯里面所推荐使用的lambda表达式的参数
```

##### 基本类型数组(Primitive type arrays)
```kotlin
val x: IntArray = intArrayOf(1, 2, 3) // [1, 2, 3]

val arr = IntArray(5) // [0, 0, 0, 0, 0]

val arr = IntArray(5) { 42 } // [42, 42, 42, 42, 42]

val arr = IntArray(5) { it * 1} // [0, 1, 2, 3, 4]
```



#### 无符号整型 Unsigned integers
无符号整型只有kotlin 1.3以及之后的版本支持
`kotlin.UByte`   0- 255<br>
`kotlin.UShort`  0- 65535<br>
`kotlin.UInt`    0- 2^32-1<br>
`kotlin.ULong`   0- 2^64-1<br>
关于Unsigned integers [请看](https://kotlinlang.org/docs/reference/basic-types.html) <br>
和原始类型一样, 每个无符号类型都有相应的数组类型,并且对`Array`class有相似的API而且没有装箱开销
```kotlin
kotlin.UByteArray()
kotlin.UIntArray()
kotlin.UShortArray()
kotlin.ULongArray()
```
什么是无符号整型的字面量(Literals)？
```kotlin
// 在数字后面加上后缀 u 或者 U
val b: UByte = 1u // UByte
val s: UShort = 1u // UShort
val l: ULong = 1u  // ULong

val a1 = 43u   // UInt, no expected type provided, constant fits in UInt
val a2 = 0xFFFF_FFFF_FFFFu // ULong, no expected type provided, constant doesn't fit in UInt

// 在数字后面加上后缀 uL 或者 UL
val a = 1UL  // ULong
```

- #### Strings
字符串类型`String`, 字符串是不可更改的(immutable), 一个String的元素是由多个字符(character)组成的
,所以可以通过`s[i]`这样的语法访问单个字符
```kotlin
// 通过for循环遍历一个字符串
for (c in str) {
    println(c)
}

// 字符串拼接
val s = "abc" + 1
println(s + "def")
``` 
##### 字符串的字面量
kotlin有两种字符串的字面量<br>
1. 含有转义字符的转义字符串(escaped strings)<br>
2. 原始字符串
```kotlin
// 转义字符串
val s = "Hello, world!\n"

// 原始字符串
val text = """
    for (c in "foo")
        print(c)
"""

// 可以使用trimMargin()函数删除前面的空格(leading whitespace)
val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
""".trimMargin()
// 注意 | 符号是默认的margin前缀,也可以选择其他的字符然后将其作为trimMargin的参数
// 比如 trimMargin(">")
```

##### 字符串模板(Strings templates)
一个模板表达式总是以一个美元符号`$`开头
```kotlin
val i = 10
println("i = $i") // 打印的结果为 i = 10

val s = "abc"
println("$s.length is ${s.length}") // 打印的结果为 abc.length is 3
```
原始字符和转义字符都支持字符串模板
```kotlin
// 在原始字符串里面表示美元符号 $, 不能使用斜杠 \$ 
val price = """
    ${'$'}9.99
"""
```

- #### 控制流(if, when, for, while)
##### if的使用
```kotlin
// 1.if作为语句(statement)
var max: Int
if (a < b) {
    max = b
} else {
    max = a
}

// 2.if作为表示式(expression)
val max = if (a > b) a else b

// if 的分支如果是块(block),那么最后一个表达式就是这个块的值
val max = if (a > b) {
    print("Choose a")
    a
} else {
    print("Choose b")
    b
}
```

##### when的使用
其实`when`就是其他语言里面的`switch`
```kotlin
when (x) {
    1 -> print("x == 1")     // 可以看见不需要加break
    2 -> print("x == 2")
    else -> {
        print("x 不是1 也不是2")
    }   
}

// switch / when 的穿透
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("其他")
}

// 箭头左边的也不一定就是常数0或者1这种
when (s) {
    parseInt(s) -> print("s encodes x")
    else -> print("s does not encode x")
}

// 也可以在箭头左边查看值是否在某个集合或者某个范围里面
when (x) {
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    !in 10..20 -> print("x is outside the range")
    else -> print("none of the above")
}

// 另一种用法: 用来检查一个值是否是某种特定类型, 这其中会有个smart casts机制
fun hasPrefix(x: Any) = when(x) {
    is String -> x.startsWith("prefix") // smart casts机制, x是String类型的时候可以直接用String的方法
    else -> false
}

// 既然是相当于switch,就可以替代if-else-if语句链
// 注意下面演示的when后面没有参数
when {
    x.isOdd() -> print("x is odd")
    y.isEven() -> print("y is even")
    else -> print("x+y is odd")
}

// 自从kotlin 1.3开始, 使用下面的语法就可以得到when中的变量
fun Request.getBody() =
    when (val response = executeRequest()) {
        is Success -> response.body
        is HttpError -> throw HttpException(response.status)
    }
```

##### for的使用
```kotlin
for (item in collection) print(item)

for (item: Int in ints) {
    // ...
}

// 遍历一组正序数字
for (i in 1..3) {
    print(i)
}
// 遍历一组逆序数字,间隔为2
for (i in 6 downTo 0 step 2) {
    print(i)
}
// 通过索引方式遍历数组
for (index in array.indices) {
    print(array[index])
}
// 按照索引方式还有另一种方式
for ((index, value) in array.withIndex()) {
    println("the element at $index is $value")
}
```

##### while的使用
两种: `while` 和 `do..while`
```kotlin
while (x > 0) {
    x--
}

do {
    val y = retriveData()
} while (y != null)

```

- #### (返回和跳转) Returns and Jumps
kotlin有三种结构化跳转语句: return 、 break 和 continue
```kotlin
val s = person.name ?: return // 如果person.name没有提供, 那么s的值就会是一个Nothing类型 
```
[Nothing type](https://kotlinlang.org/docs/reference/exceptions.html#the-nothing-type)

##### Break and Continue Labels
用过`goto`语句的应该知道label,kotlin中的label的形式是`abc@`这样结尾跟着一个
@的
```kotlin
// break 与 label的使用
loop@ for (i in 1..100) {
    for (j in 1..10) {
        if (/**/) break@loop
    }
}


// return 与 label的使用--0 // 一般return的使用
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return  //直接退出整个foo()函数
        print(it)
    }
    println("this point is unreachable")
}
// 输出结果: 12

// return 与 label的使用--1
fun foo() {
    listOf(1,2,3,4,5).forEach lit@{ 
        if (it == 3) return@lit
        print(it)
    }
    print(" done with explicit label")
}
// 输出结果: 1245 done with explicit label

// return 与 label的使用--2 使用隐式label
fun foo() {
    listOf(1,2,3,4,5).forEach { 
        if (it == 3) return@forEach // 使用隐式label, 和函数同名,这样只会退出调用其的lambda函数
        print(it)
    }
    print(" done with explicit label")
}

// return 与 label的使用--3 使用匿名函数代替lambda
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
        if (value == 3) return //只会退出当前这个匿名函数
        print(value)   
    })
    print("done with anonymous function")
}
// 输出结果: 1245 done with anonymous function

// return 与 label的使用--4 return用出break的效果
fun foo() {
    run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop
            print(it)
        }
    }   
    print(" done with nested loop")
}

// 最后一个
return@a 1 // means return 1 at label @a
```




- #### data关键字 与数据类<br>
举例，通常在User.java这样的类中我们存放的都是数据，
```kotlin
class User(var firstName: String?, var LastName: String?)
```