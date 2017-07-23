package com.harmeetsingh13.blog.third

import java.util.concurrent.atomic.AtomicInteger

case class Whisky (
  id: Int,
  name: String,
  origin: String
) {
  def this(name: String, origin: String) {
    this(Whisky.COUNTER.getAndIncrement(), name, origin)
  }

}

object Whisky {
  val COUNTER = new AtomicInteger()
  private var data: Map[Int, Whisky] = _

  def createSomeData = {
    val bowmore = new Whisky(name = "Bowmore 15 Years Laimrig", origin = "Scotland, Islay")
    val talisker = new Whisky(name = "Talisker 57° North", origin = "Scotland, Island")

    data = Map(bowmore.id -> bowmore, talisker.id -> talisker)
  }

  def getWhiskies = data.values.toVector
}
