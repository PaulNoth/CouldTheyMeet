package com.pidanic.meet.server

import spark.Spark._
import spark.{Request, Response, Route}
import java.util.Date

object SparkServer extends App {

  get("/", new Route {
    override def handle(request: Request, response: Response): AnyRef = {
      "hello" + new Date
    }
  })
}
