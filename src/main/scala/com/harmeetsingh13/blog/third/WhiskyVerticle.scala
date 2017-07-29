package com.harmeetsingh13.blog.third

import com.harmeetsingh13.blog.util.JsonUtil
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.handler.{BodyHandler, StaticHandler}
import io.vertx.scala.ext.web.{Router, RoutingContext}

import scala.concurrent.Future

class WhiskyVerticle extends ScalaVerticle {

  override def startFuture(): Future[_] = {
    Whisky.createSomeData

    val router = Router.router(vertx = vertx)

    router.route("/assets/*").handler(StaticHandler.create("assets"))
    router.route("/api/whiskies*").handler(BodyHandler.create())

    router.get("/api/whiskies").handler(getAll(_))
    router.post("/api/whiskies").handler(addOne(_))
    router.delete("/api/whiskies/:id").handler(deleteOne(_))
    router.get("/api/whiskies/:id").handler(getOne(_))
    router.put("/api/whiskies/:id").handler(updateOne(_))

    vertx.createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(config.getInteger("http.port", 8080))
  }

  private def getAll(context: RoutingContext) = {
    context.response()
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(JsonUtil.toJson(Whisky.getWhiskies))
  }

  private def addOne(context: RoutingContext) = {
    val whisky = context.getBodyAsString() match {
      case Some(json: String) => JsonUtil.fromJson[Whisky](json)
      case None => throw new RuntimeException
    }
    Whisky.addWhisky(whisky)

    context.response()
      .setStatusCode(201)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(JsonUtil.toJson(whisky))
  }

  private def deleteOne(context: RoutingContext) = {
    context.request().getParam("id") match {
      case Some(id) => Whisky.remove(id.toInt)
        context.response().setStatusCode(204).end()
      case None => context.response().setStatusCode(400).end()
    }
  }

  private def getOne(context: RoutingContext) = {
    context.request().getParam("id") match {
      case Some(id) => Whisky.findOne(id.toInt) match {
        case Some(whisky) =>
          context.response()
            .setStatusCode(201)
            .putHeader("content-type", "application/json; charset=utf-8")
            .end(JsonUtil.toJson(whisky))
        case None => context.response().setStatusCode(404).end()
      }
      case None => context.response().setStatusCode(404).end()
    }
  }

  private def updateOne(context: RoutingContext) = {
    context.request().getParam("id") match {
      case Some(id) => context.getBodyAsString() match {
        case Some(json) =>
          Whisky.updateWhisky(id.toInt, JsonUtil.fromJson[Whisky](json))
          context.response()
            .setStatusCode(201)
            .putHeader("content-type", "application/json; charset=utf-8")
            .end()
        case None => context.response().setStatusCode(404).end()
      }
      case None => context.response().setStatusCode(404).end()
    }
  }
}

object WhiskyVerticle extends App {
  Vertx.vertx.deployVerticle(ScalaVerticle.nameForVerticle[WhiskyVerticle])
}
