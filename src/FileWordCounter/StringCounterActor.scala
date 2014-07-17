package FileWordCounter

import akka.actor.Actor

class StringCounterActor extends Actor{
  def receive = {
    case ProcessStringMessage(string) =>
      val wordsInLine = string.split("").length
      sender ! StringProcessedMessage(wordsInLine)
    case _ => println("Error: message not recognized")
  }
}

case class ProcessStringMessage(msg : String)
case class StringProcessedMessage(words : Integer)
