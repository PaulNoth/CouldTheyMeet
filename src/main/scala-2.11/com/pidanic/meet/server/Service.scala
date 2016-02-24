package com.pidanic.meet.server

import akka.actor.Actor
import spray.http.MediaTypes
import spray.routing.HttpService
import MediaTypes._
import java.util.Date

private[server] trait Service extends HttpService {
  val route = path("/") {
    get {
      respondWithMediaType(`text/plain`) {
        complete {
          "hello " + new Date
        }
      }
    }
  }
}

private[server] object ServiceActor extends Actor with Service {

  def actorRefFactory = context

  def receive = runRoute(route)
}
