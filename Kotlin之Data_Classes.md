## Data Classes 和 Destructuring Declarations
- #### 什么叫data class
简单来说data class就是专门放数据的类,比如我们写的User.java这样的class就是data class<br>
用关键词`data`修饰
```kotlin
data class User(val name: String, val age: Int)
```
编译器会自动从主构造函数中声明的所有属性派生以下成员:<br>
----  `equals()`/`hashCode()` <br>
----  `toString()`: 以`User(name=John, age=42)`的形式<br>
----  componentN() functions按照属性声明的顺序解构(解构举例: val (name, age) = Person)<br>
----  `copy()`函数<br>
[关于什么叫ComponentN() 函数,请看官网的描述](https://kotlinlang.org/docs/reference/multi-declarations.html)
<br>
.<br>
.<br>
.<br>
为了保证一致性和产生的代码有意义,data class需要满足下面的要求:<br>
----  主构造器需要含有至少一个参数<br>
----  所有的主构造器的参数必须标记为val 或者 var<br>
----  ** data class 不能是`abstract open sealed innner`** <br>
---- data class也许只能实现接口(kotlin 1.1之前)<br>
在JVM中,如果被生成的class需要有一个无参的构造器的话,所有属性的默认值都需要被设定好
```kotlin
data class User(val name: String = "", val age: Int = 0)
```

- #### 在类中声明的属性们
需要注意,编译器仅将在`Primary Constructor`中定义的属性用于自动生成的函数。
要从生成的代码中导出属性，需要在类主体中声明它:
```kotlin
data class Person(val name: String) {
    var age: Int = 0
}
// 像上面这样写,只有name这个属性会被用在toString() equals() 
// hashCode() 和 copy()的实现里面, 同时也只有component1()一个函数了
```
下面演示两个age属性不同的Person的实例被当作相同情景
```kotlin
data class Person(val name: String) {
    var age: Int = 0
}
fun main() {
    val person1 = Person("John")
    val person2 = Person("John")
    person1.age = 10
    person2.age = 20
    println("person1 == person2: ${person1 == person2}") // true
    println("person1 with age ${person1.age}: ${person1}")
    println("person2 with age ${person2.age}: ${person2}")
}
// 输出结果
person1 == person2: true
person1 with age 10: Person(name=John)
person2 with age 20: Person(name=John)
```

- ####Copying
通常情况下，我们需要复制一个对象以更改其某些属性，但其余部分保持不变。 
这就是生成copy（）函数的目的。对于上面的User类，其实现如下:
```kotlin
fun copy(name: String = this.name, age: Int = this.age) = User(name, age)

// 这下可以这样写
val jack = User(name = "jack", age = 1)
val olderJack = jack.copy(age = 2)
```

- #### Data Classes 和 Destructuring
```kotlin
val wdy = User("wdy", 22)
val (name, age) = wdy
println("$name, $age years of age")
```

- #### 标准库中的data class
标准库提供了`Pair`和`Triple`








## Destructuring Declarations(解构)
解构(我是这么翻译这个操作的)就是把一个对象变为多个变量
```kotlin
// 常见的解构操作如下
for ((key, value) in map) {
    // ...
}
```
再比如定义一个数据类(data class)
```kotlin
data class Person(val name: String, val age: Int)

// 使用的时候可以这样
var (name, age) = person
println(name)
println(age)

// 上面的代码在编译的时候会变为下面的代码
val name = person.component1()
val age  = person.component2()
```
代码里面的`component1()`可以直接使用声明的第一个属性,componentN()可以
使用声明的第N个属性, data class自动声明`componentN()`,这样解构才能奏效,
注意声明的`componentN()`函数必须用`operator`关键字标记

- #### 函数如何返回多个值
声明一个data class并且函数返回这个data class的实例即可
```kotlin
data class Result(val result: Int, val status: Status)
fun function(...): Result {
    // do something
    return Result(result, status)
}

// 现在可以这样使用
val (result, status) = function(...)
```

- #### map和解构
```kotlin
for ((key, value) in map) {
    // ...
}
```
上面的代码能奏效,是因为:<br>
---- map提供迭代器`iterator()`<br>
---- 通过提供`component1()`和`component2()`函数使每个元素成对出现<br>
标准库的实现是这样的:
```kotlin
// 注意componentN()必须加operator关键字
operator fun <K, V> Map<K, V>.iterator(): Iterator<Map.Entry<K, V>> = entrySet().iterator()
operator fun <K, V> Map.Entry<K, V>.component1() = getKey()
operator fun <K, V> Map.Entry<K, V>.component2() = getValue()
```

- #### 不需要的变量使用下划线
```kotlin
val (_, status) = getResult() // 这样component1()函数就不会被调用了
```

- #### 解构在lambda表达式中的使用(重要)
```kotlin
{ a -> ...} // 一个参数
{ a, b -> ...} // 两个参数
{ (a, b) -> ...} // 被解构出来的一对(a destructured pair)
{ (a, b), c -> ...} // a destructured pair 和 另一个参数 

```









