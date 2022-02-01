package part2.AdvancedFuncProgramming

object LazyEval2 extends App {

  def incOne(x : => Int) = {
    println("inside inc")
    x + 1
  }

  def incOneByValue(x : Int) = {
    println("inside inc")
    x + 1
  }
  val myList = List(1,2,4,5)
  myList.map(x => incOne{
    println("outside inc");x }).foreach(println(_))
  println
  myList.map(x => incOneByValue{
    println("outside inc"); x}).foreach(println(_))

  def something() =
  {
    println("calling something")
    1 // return value
  }

  // Defined function
  def callByName(x: => Int) =
  {
    println("x1=" + x)
    println("x2=" + x)
  }

  // Calling function
  callByName(something())
}
