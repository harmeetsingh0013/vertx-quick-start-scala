package com.harmeetsingh13.blog.first

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

import scala.concurrent.Future

class MyFirstVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {
    vertx.createHttpServer()
      .requestHandler(r => r.response().end("<h1>Hello from my first Vert.x 3 Application</h1>"))
      .listenFuture(8080)
  }
}

object MyFirstVerticle extends App {
  Vertx.vertx.deployVerticle(ScalaVerticle.nameForVerticle[MyFirstVerticle])
}