package FileWordCounter

import akka.actor.{ActorSystem, Props}
import akka.dispatch.ExecutionContexts._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._


object FileWordCounterRunner extends App {
  implicit val ec = global()

  override def main(args: Array[String]) {
    val system = ActorSystem("System")
    val wordCounterActor = system.actorOf(Props( new WordCounterActor(args(0))))
    implicit val timeout = Timeout(60 seconds)
    val future = wordCounterActor ? StartProcessFileMessage()
    future.map{result =>
      println("Total number of words " + result)
      system.shutdown()
    }
  }
}
