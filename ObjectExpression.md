## Object Expressions and Declarations
有时我们需要创建一个对某些类稍加修改的对象，而无需为其显式声明一个新的子类。
Kotlin通过object表达式`object expression`和对象声明`object declarations`来处理这种情况。
- #### object 表达式
创建一个继承自某种类型的匿名类的对象,可以像下面这样写:
```kotlin
window.addMouseListener(object : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { /*...*/ }

    override fun mouseEntered(e: MouseEvent) { /*...*/ }
})
```
如果`object`后面的那个父类(supertype)有构造函数的话,构造函数的参数必须被传入,
如果有多个父类(不一定是父类,也可能是实现的接口),用逗号分隔开来
```kotlin
open class A(x: Int) {
    public open val y: Int = x
}

interface B { /*...*/ }

val ab: A = object : A(1), B {
    override val y = 15
}
```
注意的是: 匿名的object只有在本地和private的声明才能被当作类型使用; 如果你将匿名的
object作为public函数的返回类型 或者 作为public属性的类型,那么该函数或者属性的实际类型
将会是这个匿名object的父类型 或者 `Any`( 前提是如果你没有为这个匿名的object定义任何父类)
任何在匿名object内部定义的成员是不可达的(访问不到的)
```kotlin
class C {
    // 这是private函数,按照上面说的,这个函数的返回类型就是匿名object的类型
    private fun foo() = object {
        val x: String = "x"
    }
    
    // 这是public函数,按照上面说的,这个函数的返回类型是Any
    fun publicFoo() = object {
        val x: String = "x"
    }
     
    fun bar() {
        val x1 = foo().x  // 正确
        val x2 = publicFoo().x  // 错误,Unresolved reference 'x'
    }
}
```
`object 表达式`中的代码能访问其闭包中的变量
```kotlin
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0
    
    window.assMouselistener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
}
```

- #### Object declarations
下面要介绍的是object declarations和单例的联系
```kotlin
// 下面这样式儿的就叫object declaration, 在object后面总是有一个名字
object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }
    
    val allDataProviders: Collection<DataProvider>
        get() = // ...
}
```
object declaration不是一个表达式,并且它的初始化是线程安全的,在第一次被访问的时候就完成初始化<br>
可以直接通过使用名字来获得这个object
```kotlin
DataProviderManager.registerDataProvider(...)
```
同样的,这样声明的object也可以拥有supertype:
```kotlin
object DefaultListener : MouseAdapter() {
     override fun mouseClicked(e: MouseEvent) { ... }

     override fun mouseEntered(e: MouseEvent) { ... }
}
```
object declaration不可以是本地的(比如直接嵌套在一个函数里面),但是它们可以
被嵌套进其他的object declaration或者非内部类

- #### Companion Objects
在一个class里面的object declaration可以用`companion`关键字标记
```kotlin
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}
```
companion object里面定义的成员可以通过类名来调用
```kotlin
val instance = MyClass.create()
```
companion object的名字可以省略,这样的话`Companion`名字就会被使用
```kotlin
class MyClass {
    companion object {  }
}

val x = MyClass.Companion
```
类名本身可以被用来作为该类的companion object的一个引用
```kotlin
class MyClass1 {
    companion object Named { }
}

val x = MyClass1

class MyClass2 {
    companion object { }
}

val y = MyClass2
```
请注意，即使companion object的成员看起来像其他语言中的静态成员，
在运行时它们仍然是真实对象的实例成员，例如可以实现接口：
```kotlin
interface Factory<T> {
    fun create(): T
}

class MyClass {
    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}

val f: Factory<MyClass> = MyClass
```
在JVM中,你可以把companion object中的成员当作真的静态方法或者字段来对待,前提是
你使用`@JvmStatic`注解<br>
[更多的细节自己看看官方文档](https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html#static-fields)


- #### object expression 和 declaration的语义的不同
---- object expression都是当被用到的时候立刻执行的<br>
---- object declaration是懒初始化的,只有被第一次访问的时候才初始化<br>
---- companion object的初始化都是在对应的类被加载(或者解析)的时候,和java的静态初始化相匹配

