package part2.AdvancedFuncProgramming

object PartialFunctions extends App{

  // Partial Functions able us to restruct the domain type to specific sub type
  // for example: when we define as below, the function can take any Int value and is defined as a Total FUnction

  val aTotalFunction = (x : Int) => x + 1

  // the above function will give increment value of x by 1 for any Int Value
  // but with partial function we can limit the function to certain values of Int as follows.

  //1. Using PM we can restrict the function to certain values but in theory its still not a partial function
  val x = 2
  val aFussyFunction = x match {
    case 1 => 30
    case 2 => 999
    case 5 => 100
  }
  println(aFussyFunction)

  //2. we can create a partial function for the above scenario as above

  val aPartialFunction : PartialFunction[Int,Int] = {
    case 1 => 38
    case 2 => 129
    case 5 => 100
  }

  println(aPartialFunction(2))
  //println(aPartialFunction(999)) this will throw MatchError exception

  println(aPartialFunction.isDefinedAt(10))
  val aLiftedPF = aPartialFunction.lift // A lifted PF returns the OPtion[Int] ==>
  // If x is matched , then Some(x)
  // If no value returns None
  println(aLiftedPF(1))
  println(aLiftedPF(200))

  // we can also chain one PF function with another as follows

  val aChainedPF = aPartialFunction.orElse[Int,Int]{
    case 300 => 101
  }
  println(aChainedPF(2))
  println(aChainedPF(300))

  val newChatBoxFunction : PartialFunction[String,String] = {
      case "Hello" => "Hi there,How can i help?"
      case "Register O3" => "Sure. Please enter Yes to Register O3 for this week"
      case "Yes" => "O3 Registered for this week successfully"
      case "Thanks" => "Do you need any other help?"
      case "No" => "Quitting! Have a great day ahead..."
    }

    scala.io.Source.stdin.getLines().foreach(line => println(s"Amigo said: ${newChatBoxFunction(line)}") )
    println(newChatBoxFunction.isDefinedAt("Random"))
}
