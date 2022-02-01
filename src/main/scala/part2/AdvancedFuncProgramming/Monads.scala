package part2.AdvancedFuncProgramming

object Monads extends App{

  //implement the Lazy[T] => computation which will be executed only when its needed
  // List(x).flatmap(f) => f(x)  -> left associativity
  // list.flatmap(x => List(x)) = list  -> right associativity
  // List(x).flatMap(f).flatMap(g) = f(v).flatMap(x => List(x))

  class Lazy[+A](value: => A){
    lazy val use : A = value
    def flatMap[B](f: (=> A) => Lazy[B]) : Lazy[B] = f.apply(value)
  }

  object Lazy {
    def apply[A](value: => A): Lazy[A]= {
      println("value : " + value)
      new Lazy(value)
    }
  }

  val mylazylist = Lazy {
    Thread.sleep(2000)
    println("Im too lazy to work")
    42
  }

  //println(mylazylist.use)
  //println(mylazylist.use)
  println(mylazylist.flatMap(x => Lazy(x * 2)).use)



}
