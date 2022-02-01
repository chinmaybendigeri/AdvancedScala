package part4.Implicits

import scala.language.implicitConversions

object ImplicitBasics extends App {

  implicit val sortingBy: Ordering[Person] = Ordering.fromLessThan((a,b) => a.name.compareTo(b.name) < 0)

  case class Person(name: String, age: Int){
    def greet() : String = s"HI, My name is $name a nd my age is $age"
  }

  val persons = List(
    Person("Chinmay",26),
    Person("Vivek",28),
    Person("Jeevs",25)
  )

  println(persons.sorted)


  implicit def fromStringtoPerson(name: String): Person = Person(name,35)
  println("Mary".greet)


}
