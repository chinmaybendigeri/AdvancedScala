package part2.AdvancedFuncProgramming

object PartialCurriedFunctions extends App{

  val simpleAdderFunction = (x: Int,y : Int) => x + y
  def simpleAdderMethod(x: Int,y : Int):Int = x + y
  def curriedAdderMethod(x: Int) (y: Int) = x + y

  val newSimpleAdderFunction = simpleAdderFunction(7,_ : Int)
  val newSimpleAdderMethod = simpleAdderMethod(7,_: Int)
  val newCurriedAdderMethod = curriedAdderMethod(7) _

  val partialAdderFunction : PartialFunction[Int,Int] = { 7 +  _ : Int}

  println(newSimpleAdderFunction(10))
  println(newSimpleAdderMethod(10))
  println(newCurriedAdderMethod(10))
  println(partialAdderFunction(10))

}
