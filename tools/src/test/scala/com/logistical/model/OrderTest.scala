package com.logistical.model

import java.util.Date

import com.google.gson.Gson
import org.scalatest.FlatSpec

import scala.collection.JavaConverters._
import scala.util.Try

/**
  * Created by Zhranklin on 16/7/18.
  */
class OrderTest extends FlatSpec {

  behavior of "Order"

  import OrderExamples._

  val gson = new Gson

  it should "properly gets attrs" in {
    assert(order.getAttribute("发站") == "成都")
    assert(order.getAttribute("客户单号") == "")
    assert(Try(order.getAttribute("test")).isFailure)
    assert(order.getFee("代收款") == 5)
    assert(order.getFee("保价费") == 0)
    assert(Try(order.getFee("test")).isFailure)
  }

  it should "properly gets fees" in {
    assert(order.getFee("代收款") == 5)
    assert(order.getFee("保价费") == 0)
    assert(Try(order.getFee("test")).isFailure)
  }

  it should "properly equals after gsoned" in {
    val str = gson.toJson(order)
    println(str)
    val orderBack = gson.fromJson(str, classOf[Order])
    assert(order == orderBack)
  }

}

object OrderExamples {

  implicit class JavaBox(i: Int) {
    def jbox = i.asInstanceOf[Integer]
  }

  val attrsL = List(
    Map("发站" → "成都",
      "到站" → "西昌",
      "发货人电话" → "13712345678").asJava,
    Map("发站" → "西昌",
      "到站" → "成都",
      "发货人电话" → "17712345678").asJava)
  val attrs = attrsL.head

  val feesL = List(
    Map("代收款" → 5.jbox,
      "返款费" → 3.jbox).asJava,
    Map("代收款" → 6.jbox,
      "返款费" → 9.jbox).asJava)
  val fees = feesL.head

  val staffL = List(
    List(
      Staff("电子产品", "电脑", 3000, 10),
      Staff("电子产品", "手机", 2000, 5)).asJava,
    List(
      Staff("电子产品", "电脑", 5000, 12),
      Staff("电子产品", "手机", 4000, 6)
    ).asJava
  )
  val staff = staffL.head

  val order = new Order(attrs, fees, staff, "bar")
}
