package part1.AdvancedPM

object PMSeqCustom extends App {

  val list = List(1, 2)

  list match {
    case List(1, 2, _*) => println("List starting with 1,2")
    case _ => println("Something Else")
  }

  //infix patterns
  class Or[A, B, C](val a: A, val b: B, val c: C)

  object -->: {
    def unapply[A, B, C](x: Or[A, B, C]): Option[(A, B, C)] = Some((x.a, x.b, x.c))
  }

  val either = new Or(0, "zero", false)
  val description = either match {
    case -->:(number, string, boolean) => println(s"either $number or $string or $boolean")
    case _ => println("Nothing")
  }

  abstract class MyList[+A] {
    def head: A = ???

    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list == Empty) Some(Seq.empty)
      else {
        unapplySeq(list.tail).map(x => list.head +: x)
      }
    }
  }

  val myNewList = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))
  myNewList match {
    case MyList(1, _*) => println(s"List starting with 1")
    case _ => println("Something Else")
  }

}
