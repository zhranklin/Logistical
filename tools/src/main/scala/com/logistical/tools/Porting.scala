package com.logistical.tools

import java.io.{BufferedReader, Reader, Writer}
import java.util.{List ⇒ JList}

import com.logistical.model._

import scala.collection.JavaConverters._
import scala.util._

/**
 * Created by Zhranklin on 16/7/18.
 * 用于导入导出订单的类
 * 构造器接受一个[[ErrorHandler]]作为参数, 所有的出错信息由它处理
 */
class Porting(errorHandler: ErrorHandler) {
  private def header = List(
    List("序列号", "总费", "总数量", "商品类型"),
    Order.ATTRIBUTE_NAMES.asScala,
    Order.FEE_NAMES.asScala
  ).flatten.mkString("\t") + "\n"

  private def orderLine(o: Order) = List(
    List(o.bar, o.getTotalFee, o.getTotalNumber, o.staff.get(0).`type`),
    Order.ATTRIBUTE_NAMES.asScala.map(o.getAttribute),
    Order.FEE_NAMES.asScala.map(o.getFee).map(_.toString)
  ).flatten.mkString("\t") + "\n"

  /**
   * 将一个List导出到一个Writer
   *
   * @param from 现有的Order的List
   * @param to   要将所有的Order导出到哪里
   */
  def exp(from: java.util.List[Order], to: Writer): Unit =
  from.asScala.map(orderLine).foreach(to.append)

  /**
   * 输出开头行
   *
   * @param to 接受输出的writer
   * @return
   */
  def expHeader(to: Writer): Unit = to.append(header)

  def fetchOrder(from: Reader): JList[Order] = {
    val r = new BufferedReader(from)
    Stream.continually(r.readLine).takeWhile(_ != null).flatMap(l ⇒ Try(Order.fromJson(l)) match {
      case Success(order) ⇒ Some(order)
      case Failure(_) ⇒ errorHandler.onError(s"导入json$l 时失败"); None
    }).toList.asJava
  }


  def saveOrder(from: java.util.List[Order], to: Writer): Unit =
    from.asScala.flatMap(o ⇒ Try(o.toJson) match {
      case Success(j) ⇒ Some(j)
      case Failure(_) ⇒ errorHandler.onError(s"导出订单${o.bar}时失败"); None
    }).foreach(to.append)

}
