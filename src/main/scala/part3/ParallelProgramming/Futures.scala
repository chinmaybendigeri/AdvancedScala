package part3.ParallelProgramming

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures extends App{

  def printNtimes(n: Int): Unit = {
    val name = "Chinmay"
    if(n > 0) {
      println(s"My name is $name")
      printNtimes(n - 1)
    }
    else
     println("")
  }


  val afuture = Future {
    printNtimes(0)
  }

  val test = afuture.onComplete(t => t match {
    case Success(_) => println("Hurraaay")
    case Failure(exception) => println(s"throw new $exception")
  })

  println(afuture.value)

}
