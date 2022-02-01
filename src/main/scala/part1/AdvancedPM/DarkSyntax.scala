package part1.AdvancedPM

object DarkSyntax extends App {


  //1. Methods with parameters can be called with {} and can return the parameter value at the end

  def doubler(x: Int): Int = x * 2

  val aNewmethodSyntax = doubler {
    // Do some code Implementation
    50
  }

  //2. Map partial functions Implementation
  val aListofIntegers = List(1, 2, 3, 4)
  val newListofIntegers = aListofIntegers.map {
    x => x + 1
  }
  println(newListofIntegers)

  //.3 Syntactic Sugar :: and #:: methods are special in the sense it is right associative if there is a : at the right
  val aFreshList = List(5, 7) :++ aListofIntegers
  println(aFreshList)
  //

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = {
      println(value)
      this
    }
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  println(myStream)

  //4. Class can also have special naming conventions and Generic types can be infix too
  class -->[A, B](x: A, y: B) {
    def towards: Unit = println("Towards created")
  }

  val towardsInstance: Int --> String = new -->(26, "Chinmay")
  towardsInstance.towards

  //5. Multi line methods which are meaningful and are like natural lanaguage

  class MultiLine(name: String) {
    def `and then said`(msg: String): Unit = println(s"$name said $msg")
  }

  new MultiLine("chinmay") `and then said` "Scala is Amazing"


}
