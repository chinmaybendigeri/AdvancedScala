package part1.AdvancedPM

object CustomPM extends App {
  class Person(val name: String, val age: Int) {
  }

  object Person {
    def unapply(p: Person): Option[(String, Int)] = {
      Some((p.name, p.age))
    }
  }

  val vivek = new Person("Vivek", 28)
  val chinmay = new Person("Chinmay", 26)

  val patternMatch = vivek match {
    case Person(n, a) => println(s"Hi, I am $n and $a years old.")
  }


  // the below code can also be optimized further
  object isEven {
    def unapply(x: Int): Option[Boolean] = {
      if (x % 2 == 0) Some(true)
      else None
    }
  }

  val x = 25
  val conditionStatus = x match {
    case isEven(_) => println(s"${x} is Even")
    case _ => println("Is Not Even")
  }

  object isEven_v2 {
    def unapply(x: Int): Boolean = x % 2 == 0
  }

  val listOfIntegers = List(1, 2, 30, 200, 300)
  val conditionStatus_v2 = for (x <- listOfIntegers) x match {
    case isEven_v2() if x < 100 => println("Even")
    case _ => println("Is Not Even")
  }


  // We can define our option custom Wrapper class as the return Type as foolows
  abstract class Wrapper[T, X] {
    def isEmpty: Boolean

    def get: (T, X)
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String, Int] = new Wrapper[String, Int] {
      def isEmpty = false

      def get = (person.name, person.age)
    }
  }

  println(chinmay match {
    case PersonWrapper(n) => s"This person's name is $n"
    case PersonWrapper(n, a) => s"This person's name is $n and the age is $a"
    case _ => "An alien"
  })

}
