package com.logistical.model

import java.util.{Arrays ⇒ JArrays, List => JList, Map => JMap}
import scala.collection.JavaConverters._

object Order {
  /**
    * 可用的属性名称, 作为getAttribute的参数, 以及构造器参数中attributes的键名
    */
  val ATTRIBUTE_NAMES: JList[String] = JArrays.asList("发站", "到站", "发货人", "发货人电话", "收货人", "收货人电话", "客户单号", "付款方式", "返款方", "返款方式1", "返款方式2")
  /**
    * 可用的费用名称, 作为getFee的参数, 以及构造器中fee的键名
    */
  val FEE_NAMES: JList[String] = JArrays.asList("总运费", "代收款", "返款费", "保价费", "接货费", "送货费")
}

/**
  * Order为订单的模型类, 用来表示一个订单的各项信息, 包括各种属性(字符串)以及各种费用(int), 还有所包含的商品用Staff List表示
  * 注: Order和Staff均实现了自己的equals方法(内容相同则值相等), 且Order没实现hashCode(), 如果要作为map的键, 请考虑IdentityHashMap
  * @param attributes 属性的键值对Map, 见ATTRIBUTE_NAMES
  * @param fees       费用的键值对Map, 见FEE_NAMES
  * @param staff      商品的List
  */
class Order(attributes: JMap[String, String], fees: JMap[String, Integer], var staff: JList[Staff]) extends Serializable {
  /**
    * 获取订单的某个属性的值
    *
    * @param name 表示需要的属性名称
    * @return 属性该属性的值
    * @throws IllegalArgumentException 如果这个属性名称不存在, 就会抛出
    */
  @throws[IllegalArgumentException]
  def getAttribute(name: String): String = {
    if (!Order.ATTRIBUTE_NAMES.contains(name)) throw new IllegalArgumentException("该属性名称不存在: " + name)
    Option(attributes.get(name)).getOrElse("")
  }

  /**
    * 返回订单的某个费用的值, 返回类型为int
    *
    * @param name 费用的名称
    * @return 该费用的数值
    * @throws IllegalArgumentException 当该费用名称不存在时, 就会抛出
    */
  @throws[IllegalArgumentException]
  def getFee(name: String): Int = {
    if (!Order.FEE_NAMES.contains(name)) throw new IllegalArgumentException("该费用名称不存在: " + name)
    if (name == "总运费")
      staff.asScala.map(s ⇒ s.number * s.price).sum
    else
      Option[Int](fees get name) getOrElse(0)
  }

  /**
    * 所有费用总和
    *
    * @return 所有费用加起来的总和, 包括运费和各种杂项费用
    */
  def getTotalFee: Int = Order.FEE_NAMES.asScala.map(getFee).sum

  /**
    * 返回总件数, 由所有的Staff中的数量相加而得
    *
    * @return 总件数
    */
  def getTotalNumber: Int = staff.asScala.map(_.number).sum

  override def equals(obj: Any): Boolean =
    if (obj == null || !obj.isInstanceOf[Object])
      false
    else {
      val o = obj.asInstanceOf[Order]
      Order.ATTRIBUTE_NAMES.asScala
        .forall(a ⇒ getAttribute(a) == o.getAttribute(a)) &&
        Order.FEE_NAMES.asScala
          .forall(f ⇒ getFee(f) == o.getFee(f)) &&
        staff == o.staff
    }
}