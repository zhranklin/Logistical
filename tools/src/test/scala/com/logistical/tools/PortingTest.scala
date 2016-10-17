package com.logistical.tools

import java.io.{BufferedReader, StringReader, StringWriter}
import java.util.Date

import com.logistical.model.Order
import org.scalatest._

import scala.collection.JavaConverters._

/**
  * Created by Zhranklin on 16/7/18.
  */
class PortingTest extends FlatSpec {

  behavior of "Porting"

  val porting = new Porting(println(_: String))

  import com.logistical.model.OrderExamples._

  it should "im/export probably" in {
    val orders = for {
      attr ← attrsL
      fee ← feesL
      staff ← staffL
    } yield new Order(attr, fee, staff, "bar")

    val sw = new StringWriter
    porting.exp(orders.asJava, sw)
    val expString = sw.toString

    println(expString)

    val sr = new BufferedReader(new StringReader(expString))
    val impOrders = porting.imp(sr).asScala
    assert(orders == impOrders)
  }

}
