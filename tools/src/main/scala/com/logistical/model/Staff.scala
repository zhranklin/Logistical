package com.logistical.model

import scala.beans.BeanProperty

/**
 * type 大类, subType 小类, number 数量, price 单价
 * 在构造器中需要直接提供这四个属性(String String int int)
 */
case class Staff(@BeanProperty var `type`: String, @BeanProperty var subType: String, @BeanProperty var number: Int, @BeanProperty var price: Int) {
  def this() = this("", "", 0, 0)
}