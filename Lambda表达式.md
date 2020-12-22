## Lambda表达式
- #### 更高阶的函数和Lambda表达式
kotlin是一个函数为王的编程语言, 这意味着函数可以被存储在变量和数据结构中
,也可以被当作更高阶(higher-order functions)的函数的参数和返回值, 简单说咱可以把函数当
作一般变量来使用<br>
kotlin作为一种静态类型的语言, 使用了像`lambda`表达式这种便利的函数类型家族

- #### 高阶函数(Higher-Order)
就是把一个函数作为参数或者返回值是一个函数的函数
```kotlin
// 举个集合中fold函数的例子
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
) : R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}
```
上面的例子中, `combine`有一个函数类型`(R, T) -> R`,表示接受两个参数,类型分别为
R 和 T, 返回一个R类型的值<br>
为了调用这个`fold`, 需要将函数类型的实例作为参数传递进去, 而`lambda`表达式就是
最常用的
```kotlin
fun main() {
    val items = listOf(1, 2, 3, 4, 5)
    
    // Lambda 就是{}里面的代码块
    items.fold(0, {
        acc: Int, i: Int -> //这个就是Lambda的参数
        print("acc = $acc, i = $i")
        val result = acc + i  // 求和
        println("result = $result")
        //下面的就是lambda的返回值
        result
    })

    // Lambda表达式的参数类型可以省略,前提是能被推断出来
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

    val product = items.fold(1, Int::times)

    println("joinedToString = $joinedToString")
    println("product = $product")

}

// 输出结果
acc = 0, i = 1, result = 1
acc = 1, i = 2, result = 3
acc = 3, i = 3, result = 6
acc = 6, i = 4, result = 10
acc = 10, i = 5, result = 15
joinedToString = Elements: 1 2 3 4 5
product = 120
```

- #### 函数类型(Function types)
1. 所有的函数类型都有一个`()`包裹起来的参数列表和一个返回值: <br>
`(A, B) -> C` 表示一个接收两个参数`A` 和 `B` 并且返回一个C类型的值的函数类型,
参数类型可以为空, 比如`() -> A`, 如果你见到`val onClick: () -> Unit`这样的
, 直接忽略`Unit`即可,就像java里面的`void` <br>
2. 函数类型可以选择性的加上接收者类型, `A.(B) -> C`, 这个函数类型就代表可以在A的接收器对象
上使用参数B调用并返回C值的函数 <br>
3. Suspending 函数也属于函数类型中的特殊的一种， 它有一个`suspend`标识符，
比如`suspend () -> Unit` 或者 `suspend A.(B) -> C` <br>
也可以给一个给函数类型取个别名
```kotlin
typealias ClickHandler = (Button, ClickEvent) -> Unit
```

- #### 初始化函数类型
可以有几种方式获取一个函数类型的实例
```kotlin
1. 用下面几种形式中的一种
-- lambda表达式 { a, b -> a + b }

-- 匿名函数: 
fun(s: String): Int {
    return s.toIntOrNull() ?: 0
}


2. 使用对现有声明函数的可调用的引用
-- 一个top-level函数、本地函数、成员函数或者拓展函数，比如::isOdd, String::toInt

-- 一个top-level属性、成员属性或者拓展属性,比如List<Int>::size

-- 一个构造器 ::Regex


3. 使用自定义类的实例,这个自定义类像实现接口一样的实现这个函数类型
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}
val intFunction: (Int) -> Int = IntTransformer()
```
编译器也能推断出某些函数类型的类型，所以有时候不需要写这些类型
```kotlin
// 下面没有写a的函数类型具体是什么,由=后面可以推断出为(Int) -> Int
val a = { i: Int -> i + 1 } // (Int) -> Int
```
对于非字面量的函数类型的值,有接收者和没有接收者都是可以相互转换的,所以接收者可以作为
第一个参数,反过来也一样, 比如`(A, B) -> C`可以转换为 `A.(B) -> C`
```kotlin
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun // ok

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}

val result = runTransformation(repeatFun) // ok
```

- #### 调用一个函数类型的实例
函数类型的值可以通过`invoke`来调用, `f.invoke(x) 也等同于 f(x)` <br>
有接收者的函数类型在调用的时候可以使用拓展函数的形式调用, 例如`1.foo(2)`
```kotlin
val stringPlus: (String, String) -> String = String::plus
val intPlus: Int.(Int) -> Int = Int::plus

println(stringPlus.invoke("<-", "->"))
println(stringPlus("hello", "world"))

println(intPlus.invoke(1, 1))
println(intPlus(1, 2))
println(2.intPlus(3)) // 这步有点像拓展函数的调用
```

- #### Lambda表达式和匿名函数
两者都是函数字面量(比如没有被声明但是被当作表达式直接传递使用的函数)
```kotlin
max(strings, { a, b -> a.length < b.length })
```
`max`就是一个高阶函数, 第二个参数是一个表达式同时它自己也是个函数, 第二个参数就是
函数字面量，和下面的有名字的函数等同
```kotlin
fun compare(a: String, b: String): Boolean = a.length < b.length
```

- ##### Lambda表达式语法
完整的lambda表达式的语法就像下面这样:
```kotlin
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
```
Lambda表达式总是被花括号`{}`包围着, `->`前面的部分叫作形参,  `->`后面的就是lambda的body啦, 如果返回类型不是
`Unit`(相当于void， 也就是没有返回值),那么body里面的最后一个表达式就被当作返回值
```kotlin
// sum的类型可以写可以不写(如果能被推断出来的话)
val sum = { x: Int, y: Int -> x + y }
```

- ##### 在结尾传递lambda
kotlin中, 有这样的编码习惯: 如果函数的最后一个参数是一个函数的话, 那么lambda
表达式作为相应的参数被传递的时候可以放在`()`外面, 举例
```kotlin
val product = items.fold(1) { acc, e -> acc * e }
```
** 这样的语法叫作尾调用lambda(trailing lambda) ** <br>
如果函数lambda表达式就是那个函数的唯一的参数, 那么连`()`都可以省略
```kotlin
run { println("...")}
```

- ##### it: 单个参数的隐式的名字
it这个名字应该见得不少, 在lambda表达式中只有一个参数的
情况不少见, 如果编译器自己能够弄明白函数签名, 那么允许不用声明唯一
的参数, 并且可以省略`->`, 这时候隐式的参数会以`it`的名字来声明
```kotlin
ints.filter { it > 0 } // (it: Int) -> Boolean
```

- ##### 从lambda表达式中返回一个值
可以显示地使用`qualified return`语法返回一个值<br>
隐式的话,最后一个表达式就是返回值
```kotlin
// 下面两个是等同的
ints.filter {
    val shouldFilter = it > 0
    shouldFilter
}

ints.filter {
    val shouldFilter = it > 0 
    return@filter shouldFilter
}
```
上面这个编码习惯和`在()外传递lambda表达式`一起构成了`LINQ-style`编码
```kotlin
strings.filter { it.length == 5}.sortedBy { it }.map( it.toUpperCase() )
```

- ##### 匿名函数
上面呈现的lambda表达式语法缺少的一件事是可以指定函数的返回类型。 
在大多数情况下，这是不必要的，因为可以自动推断返回类型。 但是，如果确实需要明确指定它，则可以使用另一种语法：匿名函数。
```kotlin
// 匿名函数是莫得函数名字的
fun(x: Int, y: Int): Int = x + y
```
同样匿名函数的参数类型和返回类型也可以省略
```kotlin
ints.filter(fun(item) = item > 0)
```
注意匿名函数和lambda表达式的区别

- ##### 闭包
Lambda表达式或匿名函数（以​​及局部函数和对象表达式）可以访问其闭包，即在外部范围中声明的变量。 
闭包中捕获的变量可以在lambda中进行修改：
```kotlin
var sum = 0
ints.filter { it > 0 }.forEach {
    sum += it
}
print(sum)
```

- ##### 带有接收者的函数字面量
(直接翻译官方的话) 带有接收者的函数类型，例如`A.(B)-> C`，可以使用特殊形式的函数常量来实例化————带有接收者的函数常量<br>
如上所述，Kotlin提供了在接收方提供接收方对象的情况下调用函数类型实例的功能。
在函数字面量的主体内部，传递给调用的接收方对象将变为隐式`this`，以便您可以在
没有任何其他限定符的情况下访问该接收方对象的成员，或者使用this表达式访问接收方对象。
此行为类似于扩展功能，它也使您可以访问函数主体内的接收器对象的成员。<br>
下面来个例子: 函数字面量带有它的接收者和接收者的类型
```kotlin
// 这个plus就是被接收者所调用的
val sum: Int.(Int) -> Int = { other -> plus(other) }
```
匿名函数的语法使我们可以明确接收者的类型, 如果你想在你的代码的后面使用一个
函数类型的变量,这一点还是挺有用的
```kotlin
val sum: Int.(other: Int) -> Int = this + other // 这个this指的就是接收者对象
```










