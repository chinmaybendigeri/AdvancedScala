package part2.AdvancedFuncProgramming

object LazyEvalMyStreams extends App {

  abstract class MyStream[+A]{
    def isEmpty : Boolean
    def head: A
    def tail : MyStream[A]

    def #::[B >: A](element : B) : MyStream[B]
    def ++[B>: A](otherStream: => MyStream[B]) : MyStream[B]

    def map[B](f: A => B) : MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean) : MyStream[A]
    def foreach(f: A => Unit) : Unit
    def take(n: Int) : MyStream[A]

    def takeAsList(n: Int) : List[A]

    def toList[B >: A](acc: List[B] = Nil) : List[B] = {
      if(isEmpty) acc
      else
        tail.toList(head :: acc)
    }

  }

  object EmptyStream extends MyStream[Nothing]{
    def isEmpty: Boolean = true

    def head: Nothing = throw new NoSuchElementException

    def tail: MyStream[Nothing] = throw new NoSuchElementException

    def #::[B >: Nothing](element: B): MyStream[B] = new NonEmptyStream(element,this)

    def ++[B >: Nothing](otherStream: => MyStream[B]): MyStream[B] = otherStream

    def map[B](f: Nothing => B): MyStream[B] = this

    def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

    def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

    def foreach(f: Nothing => Unit) : Unit = ()

    def take(n: Int): MyStream[Nothing] = this

    def takeAsList(n: Int): List[Nothing] = Nil

  }

  class NonEmptyStream[+A](h : A,t : => MyStream[A]) extends MyStream[A] {
    def isEmpty: Boolean = false

    override val head: A = h

    lazy override val tail: MyStream[A] = t

    //(1,2,3)

    def #::[B >: A](element: B): MyStream[B] = new NonEmptyStream[B](element, this)

    def ++[B >: A](otherStream: => MyStream[B]): MyStream[B] = new NonEmptyStream[B](head, tail ++ otherStream)

    def map[B](f: A => B): MyStream[B] = new NonEmptyStream[B](f(head), tail.map(f))

    def flatMap[B](f: A => MyStream[B]): MyStream[B] = {
      f(head) ++ tail.flatMap(f)
    }
    //

    def filter(predicate: A => Boolean): MyStream[A] = {
      if(predicate(head)) new NonEmptyStream[A](head,tail.filter(predicate))
      else
        tail.filter(predicate)
    }


    def foreach(f: A => Unit) : Unit = {
      f(head)
      t.foreach(f)
    }

    def take(n: Int): MyStream[A] = {
      if(n <= 0) EmptyStream
      else if(n == 1) new NonEmptyStream[A](head,EmptyStream)
      else
        new NonEmptyStream[A](head,tail.take(n -1))
    }

    def takeAsList(n: Int): List[A] = take(n).toList()

  }


  object MyStream {
    def from[A](start: A) (generator: A => A) : MyStream[A] = {
      new NonEmptyStream(start, MyStream.from(generator(start)) (generator))
    }

    def fibnocciGen(first: Int, second: Int) : MyStream[Int]  = {
      new NonEmptyStream[Int](first,MyStream.fibnocciGen(second,second + first))
    }

    def eratosthenes(numbers: MyStream[Int]) : MyStream[Int] = {
      if(numbers.isEmpty) numbers
      else
        new NonEmptyStream(numbers.head, eratosthenes(numbers.tail.filter(_ % numbers.head != 0)))
    }

  }
  //1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144


  val myStream = MyStream.from(2) (_ + 1)
  MyStream.eratosthenes(myStream).take(100).foreach(println(_))

//  val myFibStream = MyStream.fibnocciGen(1,1)
//  myFibStream.take(10).foreach(println(_))
//
//  myFibStream.map(_ * 10).take(20).foreach(println(_))

}
