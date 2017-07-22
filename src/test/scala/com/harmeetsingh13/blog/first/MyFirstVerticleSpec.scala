package com.harmeetsingh13.blog.first

import com.harmeetsingh13.blog.VerticleTesting
import io.vertx.core.json.JsonObject
import io.vertx.scala.core.DeploymentOptions
import org.scalatest.Matchers

import scala.concurrent.Promise

class MyFirstVerticleSpec extends VerticleTesting[MyFirstVerticle](DeploymentOptions().setConfig(new JsonObject("{ \"http.port\": 8082}")))
  with Matchers {

  "MyFirstVerticle" should
    "return 202 response" in {
      val promise = Promise[String]

      vertx.createHttpClient
        .getNow(8082, "localhost", "/",
          r => {
            r.bodyHandler(b => promise.success(b.toString))
          }
        )
      promise.future.map(res => res should equal("<h1>Hello from my first Vert.x 3 Application</h1>"))
    }
}
