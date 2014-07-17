package FileWordCounter

import akka.actor.{Actor, ActorRef, Props}

import scala.io.Source._

class WordCounterActor(fileName: String) extends Actor{

  private var result = 0
  private var linesProcessed = 0
  private var totalLines = 0
  private var fileSender: Option[ActorRef] = None
  private var running = false

  def receive = {
    case StartProcessFileMessage() =>
      if(running) {
        println("Warning: duplicate start message received")
      }
      else {
        running = true
        fileSender = Some(sender)
        fromFile(fileName).getLines().foreach { line =>
          context.actorOf(Props[StringCounterActor]) ! ProcessStringMessage(line)
          totalLines += 1
        }
      }
    case StringProcessedMessage(words) =>
      result += words
      linesProcessed += 1
      if(linesProcessed == totalLines){
        fileSender.map(_ ! result)
      }

    case _ => println("message not recognized!")
  }

}

case class StartProcessFileMessage()