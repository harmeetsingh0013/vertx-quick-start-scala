package com.harmeetsingh13.blog.third

import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.StaticHandler

import scala.concurrent.Future

class MyFirstVerticleV2 extends ScalaVerticle {

  override def startFuture(): Future[_] = {
    val router = Router.router(vertx = vertx)

    router.route("/").handler(context => {
      context.response()
        .putHeader("content-type", "text/html")
        .end("<h1>Hello from my first Vert.x 3 Application</h1>")
    })

    router.route("/assets/*").handler(StaticHandler.create("assets"))

    vertx.createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(config.getInteger("http.port", 8080))
  }
}

object MyFirstVerticleV2 extends App {
  Vertx.vertx.deployVerticle(ScalaVerticle.nameForVerticle[MyFirstVerticleV2])
}