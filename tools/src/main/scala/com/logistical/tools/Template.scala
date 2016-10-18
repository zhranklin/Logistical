package com.logistical.tools

import java.io.OutputStream

import com.logistical.model.Order

import scala.util.Try

/**
 * Created by Zhranklin on 16/10/3.
 */
class OrderTemplate(tpl: String) {

  implicit class RichOrder(o: Order) {
    def uniAttr(name: String): String = name match {
      case "总价" ⇒ o.getTotalFee.toString
      case "总件数" ⇒ o.getTotalNumber.toString
      case _ ⇒ Try(o.getFee(name).toString).getOrElse(o.getAttribute(name))
    }
  }

  val CMD = "<(.+?)>(.*)".r
  val ATTR = "{(.+?)}(.*)".r
  val OTHER = "([^<{]+)(.*)".r

  def render(order: Order, o: OutputStream) = {
    implicit val builder = PrintWork.builder()
    def rend(tpl: String): Unit = tpl match {
      case CMD(command, rest) ⇒
        PrintWork.handleCmd(command)
        rend(rest)
      case ATTR(name, rest) ⇒
        rend(order.uniAttr(name))
        rend(rest)
      case OTHER(text, rest) ⇒
        rend(text)
        rend(rest)
      case _ ⇒ builder.text(tpl)
    }
    rend(tpl)
    builder.build(o).run()
  }
}
