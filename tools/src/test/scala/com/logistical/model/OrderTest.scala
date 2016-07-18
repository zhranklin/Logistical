package com.logistical.model

import java.util

import org.scalatest.FlatSpec

import scala.collection.JavaConverters._
import com.google.gson.Gson
import scala.util.Try

/**
  * Created by Zhranklin on 16/7/18.
  */
class OrderTest extends FlatSpec {

  behavior of "Order"
  val attrs = Map(
    "发站" → "成都",
    "到站" → "西昌",
    "发货人电话" → "13712345678"
  ).asJava

  val fees = Map(
    "代收款" -> 5.jbox,
    "返款费" -> 3.jbox
  ).asJava

  implicit class JavaBox(i: Int) {
    def jbox = i.asInstanceOf[Integer]
  }

  val staff = List.empty[Staff].asJava
  val order = new Order(attrs, fees, staff)
  val gson = new Gson

  it should "properly gets attrs" in {
    assert(order.getAttribute("发站") == "成都")
    assert(order.getAttribute("客户单号") == "")
    assert(Try(order.getAttribute("test")).isFailure)
    assert(order.getFee("代收款") == 5)
    assert(order.getFee("保s价费") == 0)
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
