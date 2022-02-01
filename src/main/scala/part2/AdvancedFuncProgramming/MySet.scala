package part2.AdvancedFuncProgramming

//object CustomSet extends App{
//
//  val aSet = Set(1,2,3,4,5)
//
//  println(aSet.head)
//  println(aSet.tail)
//
//  println(aSet.++(Set(7,8,9)))
//
//  println(aSet.contains(7))
//  println(aSet.+(10))
//}

trait MySet[A] extends (A => Boolean){

  def contains(x: A) : Boolean
  def +(x: A): MySet[A]
  def ++(otherSet : MySet[A]): MySet[A]
  def unary_! : MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]) : MySet[B]
  def filter(f: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def apply(v1: A): Boolean = {
    contains(v1)
  }

  def remove(x : A) : MySet[A]
  def intersection(other : MySet[A]) : MySet[A]
  def difference(other: MySet[A] ) : MySet[A]

}

class NonEmptySet[A](val h: A,val t : MySet[A]) extends MySet[A]{

  def contains(x: A): Boolean = {
      (h == x) || t.contains(x)
  }

  def +(x: A): MySet[A] = {
    if(t.contains(x)) this
    else
      new NonEmptySet(x,this)
  }

  //(1,2,3) ++ (4,5)
  //(2,3) ++ (4,5) + 1
  //(3) ++ (4,5) + 1 + 2
  //() ++ (4,5) + 1 + 2 + 3
  //(4,5) + 1 + 2 + 3
  def ++(otherSet: MySet[A]): MySet[A] = {
    t.++(otherSet)+h
  }

  //(1,2,3).map(_ * 2)
  //(2,3).map(_ * 2) + f(1)
  //(3).map(_ * 2) + f(1) + f(2)
  //().map(_ * 2) + f(1) + f(2) + f(3)
  //EmptySet + f(1) + f(2) + f(3)
  //(6) + f(1) + f(2)
  //(6,4) + f(1)
  //(6,4,2)
  def map[B](f: A => B): MySet[B] = (t map f) + f(h)

  //(1,2,3).flatMap(x => MySet[Int](x * 10,EmptySet))
  // (2,3).flatMap(x => MySet[Int](x * 10,EmptySet)) ++ (10)
  //(3).flatMap(x => MySet[Int](x * 10,EmptySet)) ++ (10) ++ (20)
  //().flatMap(x => MySet[Int](x * 10,EmptySet)) ++ (10) ++ (20) ++ (30)
  //EmptySet ++ (10) ++ (20) ++ (30)
  //(30) ++ (10) ++ (20)
  //(20) ++ (10) + 30
  //(10) + 30 + 20
  //30 + 20 + 10
  //(30,20) + 10
  //(30,20,10)
  def flatMap[B](f: A => MySet[B]): MySet[B] = (t flatMap f) ++ f(h)

  //(1,2,3,4).filter(_ % 2 == 0)
  //(2,3,4).filter(_ % 2 == 0)
  //(3,4).filter(_ % 2 == 0)
  //(4).filter(_ % 2 == 0)
  //().filter(_ % 2 == 0)
  // EmptySet
  // EmptySet + 4
  // (4)
  // (4) + 2
  // (4,2)
  // (4,2)
  def filter(f: A => Boolean): MySet[A] = {
    val filteredTail = t.filter(f)
    if(f(h)) filteredTail + h
    else filteredTail
  }

  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }
  3
  //(1,2,3).remove(2)
  //(2,3).remove(2) + 1
  //(3) + 1
//  def contains(x: A): Boolean = {
//    (h == x) || t.contains(x)
//  }

  def remove(x: A): MySet[A] = {
    if(h == x) t
    else
       t.remove(x) + h
  }
  (1,2,3,4)
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))
// def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))
  def intersection(other: MySet[A]): MySet[A] = filter(x => other(x))
  //(1,2,3) - (2,3,4) = (1)
  def difference(other: MySet[A]): MySet[A] = filter(!other) // x => !other.contains(x)
}

class EmptySet[A] extends MySet[A]{

  def contains(x: A): Boolean = false

  def +(x: A): MySet[A] = new NonEmptySet(x,this)

  def ++(otherSet: MySet[A]): MySet[A] = otherSet

  def map[B](transformer: A => B): MySet[B] = new EmptySet[B]

  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

  def filter(f: A => Boolean): MySet[A] = this

  def foreach(f: A => Unit): Unit = ()

  def remove(x: A): MySet[A] = this

  def intersection(otherSet: MySet[A]): MySet[A] = this

  def difference(otherSet : MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A]{
   def contains(elem : A): Boolean = property(elem)

   def +(elem: A): MySet[A] = {
     new PropertyBasedSet[A](x => property(x) || x == elem )
   }

   def ++(otherSet: MySet[A]): MySet[A] = {
     new PropertyBasedSet[A](x => property(x) || otherSet.apply(x))
   }

   def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

   def map[B](f: A => B): MySet[B] = politelyFail

   def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

   def filter(f: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && f(x))

   def foreach(f: A => Unit): Unit = politelyFail

   def remove(elem: A): MySet[A] = filter(x => x != elem)

   def intersection(other: MySet[A]): MySet[A] = filter(other)

   def difference(other: MySet[A]): MySet[A] = filter(!other)

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit Hole!")
}

object MySet{
  def apply[A](values: A*): MySet[A] = {
    //MySet(1,2,3,4) => Seq(1,2,3,4)
    // EmptySet
    //(2,3,4),EmptySet + 1
    //(3,4),EmptySet + 1 + 2
    //(4),EmptySet + 1 + 2 + 3
    //(),EmptySet + 1 + 2 + 3 + 4
    //EmptySet + 4 + 3+ 2 + 1 = MySet(1,2,3,4)
      def accumulator[A](valSeq: Seq[A],acc: MySet[A]): MySet[A] = {
        if(valSeq.isEmpty) acc
        else
          accumulator(valSeq.tail,acc + valSeq.head)
      }
     accumulator(values.toSeq,new EmptySet[A])
  }
}

object MySetTestRunner extends App{


  //val mySet : MySet[Int] = new NonEmptySet(1,new NonEmptySet(2,new NonEmptySet(3,new NonEmptySet(4,new EmptySet))))
 // mySet.+(20).foreach(println(_))

  //mySet.++(new NonEmptySet(4,new NonEmptySet(5,new EmptySet))).flatMap(x => new NonEmptySet[Int](x * 10,new EmptySet[Int])).foreach(println(_))

 // mySet.filter(_ % 2 == 0).foreach(println(_))

  val newSet = MySet(1,2,3,4)
//  newSet.remove(35).foreach(println(_))
//
//  newSet.difference(MySet(678,11,88))
  val negatedSet = !newSet

  //val propertySet : PropertyBasedSet[Int] = new PropertyBasedSet(x => x % 2 != 0)

  println(negatedSet.contains(5))
}