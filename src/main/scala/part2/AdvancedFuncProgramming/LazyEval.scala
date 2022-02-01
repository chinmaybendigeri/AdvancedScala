package part2.AdvancedFuncProgramming

object LazyEval extends App {

  def incByOne(x: => Int) = {
    lazy val t = x
    t + t + t + 1
  }

  def magicValue ={
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(incByOne(magicValue))
  println

   val x = {
     Thread.sleep(1000)
     println("Im here")
      500
  }

  println(x) // lazy will only be evaluated for the first time
  println(x) // second time it will just return the already evaluated value for 'x' in this case
  println

  val mylist = List(1,30,25,33,23)

   def isGreaterThan20(x : Int) : Boolean = {
    println(s"$x is greater than 20?")
    x > 20
  }

  def isLessThan30(x : Int) : Boolean = {
    println(s"$x is less than 30?")
    x < 30
  }

  val ig20 = mylist.withFilter(isGreaterThan20)
  val il30 = ig20.withFilter(isLessThan30)  // withFilter does the lazy evalution
  il30.foreach(println(_))

  //  for comprehensions with if quard are lazily evaluated
 val myFilteredList = for {
   x <- mylist if x % 2 == 0
 } yield x + 1

  println(myFilteredList)

  // the above is equivalent to
  val myFilteredList_v2 = mylist.withFilter(_ % 2 == 0).map(_ + 1)
  println(myFilteredList_v2)
}
