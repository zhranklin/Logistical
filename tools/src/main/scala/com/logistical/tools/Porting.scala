package com.logistical.tools

import java.io.{BufferedReader, Writer}
import java.util

import com.google.gson.Gson
import com.logistical.model._

import scala.collection.JavaConverters._
import scala.util.Try

/**
  * Created by Zhranklin on 16/7/18.
  * 用于导入导出订单的类
  * 构造器接受一个[[ErrorHandler]]作为参数, 所有的出错信息由它处理
  */
class Porting(errorHandler: ErrorHandler) {
  private val gson = new Gson

  private def JsonToOrder(json: String) = Try(gson.fromJson(json, classOf[Order]))
    .toOption.toRight(s"订单导入失败: $json")

  private def OrderToJson(order: Order) = Try(gson.toJson(order, classOf[Order]))
    .toOption.toRight(s"导出订单失败: $order")

  /**
    * 将一个List导出到一个Writer, 以后可以构造一个重载方法, 接受File对象, 或者文件名之类的
    * @param from 现有的Order的List
    * @param to 要将所有的Order导出到哪里
    */
  def exp(from: java.util.List[Order], to: Writer) ={
    from.asScala.foreach {
      OrderToJson(_) match {
        case Right(json) =>
          to.append(json)
          to.append('\n')
        case Left(err) => errorHandler.onError(err)
      }
    }
    to.close()
  }

  /**
    * 从BufferedReader导入Order, 以后会重载接受File对象等
    * @param from 包含json的BufferedReader
    * @return 一个Order的List
    */
  def imp(from: BufferedReader): util.List[Order] = {
    def stream: Stream[String] = from.readLine #:: stream
    stream.takeWhile(_ != null).flatMap{
      JsonToOrder(_) match {
        case Right(order) ⇒
          Some(order)
        case Left(err) ⇒
          errorHandler.onError(err)
          None
      }
    }.asJava
  }
}
