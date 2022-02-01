package part4.Implicits

object ImplicitOrdering extends App {

  // Implicts are ordered based on following
  //1. Local Scope (Normal Scope)
  //2. Import Scope
  //3. Companion Objects  - of any supertype or its own type

  /*
  we need to order the purchases based on the following ordering
  1. Total Cost - 50%
  2. Num of Units - 25%
  3. Unit Price - 25%
  */

  case class Purchases(nUnits: Int, unitPrice: Double)

  object Purchases{
    implicit def numOfUnitsOrdering : Ordering[Purchases]  = Ordering.fromLessThan((a,b) => a.nUnits < b.nUnits )
  }

  implicit def totalCostOrdering : Ordering[Purchases]  = Ordering.fromLessThan((a,b) => (a.nUnits * a.unitPrice) < (b.nUnits * b.unitPrice))


  val purchases = List(Purchases(20,10),Purchases(10,10),Purchases(5,50))
  println(purchases.sorted)
}
