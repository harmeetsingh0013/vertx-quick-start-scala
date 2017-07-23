package com.harmeetsingh13.blog.third

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.vertx.core.json
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.lang.scala.json.Json
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.StaticHandler

import scala.concurrent.Future

class WhiskyVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {
    Whisky.createSomeData

    val router = Router.router(vertx = vertx)
    val mapper = json.Json.mapper.registerModule(DefaultScalaModule)

    router.get("/api/whiskies").handler(context => {
      println(Whisky.getWhiskies)
      context.response()
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(mapper.writeValueAsString(Whisky.getWhiskies))
    })

    router.route("/assets/*").handler(StaticHandler.create("assets"))

    vertx.createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(config.getInteger("http.port", 8080))
  }
}

object WhiskyVerticle extends App {
  Vertx.vertx.deployVerticle(ScalaVerticle.nameForVerticle[WhiskyVerticle])
}
