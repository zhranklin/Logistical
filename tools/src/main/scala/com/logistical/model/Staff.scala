package com.logistical.model

/**
  * type 大类, subType 小类, number 数量, price 单价
  * 在构造器中需要直接提供这四个属性(String String int int)
  */
case class Staff(`type`: String, subType: String, number: Int, price: Int)
