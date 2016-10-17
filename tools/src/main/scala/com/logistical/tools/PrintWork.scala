package com.logistical.tools

import java.io.{ByteArrayOutputStream, OutputStream, OutputStreamWriter}
import java.util.Date

import scala.collection.JavaConverters._
import com.logistical.model.{Order, Staff}

import scala.annotation.varargs

object PrintWork {
  import Font._

  val testOrder = new Order(Map(
    "发站" → "成都",
    "到站" → "攀枝花",
    "发货人" → "张三",
    "发货人电话" → "13000000000",
    "收货人" → "李四",
    "收货人电话" → "13111111111",
    "客户单号" → "12345678",
    "付款方式" → "现结").asJava, Map(
    "代收款" → new Integer(200)
  ).asJava, List(Staff("电脑配件", "显示器", 3, 50)).asJava,
    Order.getBarcode("CDPZH", new Date, 3))

  def testPrintWork1(o: OutputStream) = builder()
    .printOrder(testOrder)
    .printStaff(testOrder, testOrder.staff.get(0), 1)
    .printStaff(testOrder, testOrder.staff.get(0), 2)
    .printStaff(testOrder, testOrder.staff.get(0), 3)
    .build(o)

  /**
    * 获得一个builder, 用来构造PrintWork
    *
    * @return PrintWork.Builder
    */
  def builder(): Builder = builder("GBK")

  def builder(encoding: String): Builder = new Builder(encoding)

  /**
    * 用来构造PrintWork的Builder, 以此作为打印指令的DSL, 样例代码:
    * {{{
    * import com.logistical.tools._
    * pw = PrintWork.builder()
    *   .text("testing\n")
    *   .align(1)
    *   .barcode("12345")
    *   .build(outputStream) //outputStream打印机的输出流
    * pw.run() //开始打印
    * }}}
    *
    * @see [[Builder]]
    */
  class Builder private[PrintWork](encoding: String) {

    private val bao = new ByteArrayOutputStream()
    private val writer = new OutputStreamWriter(bao, encoding)
    private val ESC = 0x1B
    private val GS = 0x1D

    //初始化打印机
    write(ESC, 0x40)
    write(GS, 'h', 50)
    align(1)

    /**
      * 打印订单 未分页
      *
      * @param o 订单对象
      */
    def printOrder(o: Order) = {
      import o._
      val 发站 = getAttribute("发站")
      val 到站 = getAttribute("到站")
      val 发货人 = getAttribute("发货人")
      val 发货人电话 = getAttribute("发货人电话")
      val 收货人 = getAttribute("收货人")
      val 收货人电话 = getAttribute("收货人电话")
      val 客户单号 = getAttribute("客户单号")
      val 付款方式 = getAttribute("付款方式")
      newPage()
      barcode(bar)
      text(s"$bar\n")
      fontSize(2)
      font(DOUBLE_HEIGHT, DOUBLE_WIDTH, BOLD)
      text(s"$发站 $到站\n")
      font()
      text(
        s"""单号: $客户单号 付款方式: $付款方式
            |发: $发货人 $发货人电话
            |收: $收货人 $收货人电话
       """.stripMargin)
      text(staff.asScala
        .map(st ⇒ s"${st.`type`} ${st.number}件 * ${st.price}元\n")
        .mkString(""))
      text(Order.FEE_NAMES.asScala
        .filter(getFee(_) != 0)
        .map(name ⇒ s"$name: ${getFee(name)}")
        .grouped(2)
        .map(_.mkString(" ") + "\n")
        .mkString(""))
      font(DOUBLE_HEIGHT, DOUBLE_WIDTH, BOLD)
      text(s"总费:$getTotalFee 共${getTotalNumber}件\n")
      nextPage()
    }

    def printStaff(o: Order, s: Staff, i: Int) = {
      import o._
      val bar = s"${o.bar}-$i"
      val 发站 = getAttribute("发站")
      val 到站 = getAttribute("到站")
      val 发货人 = getAttribute("发货人")
      val 发货人电话 = getAttribute("发货人电话")
      val 收货人 = getAttribute("收货人")
      val 收货人电话 = getAttribute("收货人电话")
      newPage()
      barcode(bar)
      text(s"$bar\n")
      fontSize(2)
      font(DOUBLE_HEIGHT, DOUBLE_WIDTH, BOLD)
      text(s"$发站 $到站 $i/${s.number}\n")
      font()
      text(
        s"""${s.`type`}
           |发: $发货人 $发货人电话
           |收: $收货人 $收货人电话
       """.stripMargin)
      nextPage()
    }

    def write(chars: Int*): Builder = {
      chars foreach writer.write
      this
    }

    /**
      * 输出字符串
      *
      * @param str 输出的内容
      * @return this
      */
    def text(str: String): Builder = {
      writer write str
      this
    }

    /**
      * 设置对齐方式
      *
      * @param align 0: 左对齐(默认), 1: 居中, 2: 右对齐
      * @return this
      */
    def align(align: Int): Builder = write(ESC, 0x61, align)

    /**
      * 打印制表符
      *
      * @param times 数字, 表示打印的制表符的个数
      * @return this
      */
    def tab(times: Int): Builder = write(Seq.fill[Int](times)('\t'): _*)

    /**
      * 设置行间距
      *
      * @param gap 行间距的像素点数, 最大值255
      * @return this
      */
    def gap(gap: Int): Builder = write(ESC, 0x33, gap)

    /**
      * 设置左边距
      *
      * @param margin 以8点为单位, 范围(左+右): 58mm宽: [0, 48), 80mm宽: [0, 72)
      * @return this
      */
    def lMargin(margin: Int): Builder = write(ESC, 0x6C, margin)

    def rMargin(margin: Int): Builder = write(ESC, 0x51, margin)

    /**
      * 设置字体样式
      *
      * @param fonts [[Font]] (ITALIC, FRAME, BOLD, DOUBLE_HEIGHT, DOUBLE_WIDTH, INVERSE, UNDERLINE)中的字体, 可以添加多个参数组合使用
      * @return this
      */
    @varargs def font(fonts: Font*): Builder = {
      val fontTable = Font.values map
        (font => if (fonts contains font) 1 else 0)
      write(ESC, 0x21, (0 /: fontTable) (_ * 2 + _))
    }

    /**
      * 设置字体大小, 允许三种值:
      * size 类型
      * 0 中文:24×24,外文:12×24
      * 1 中文:16×16,外文:8×16
      * 2 中文:12×12,外文:6×12
      *
      * @param size 范围为[0, 2]
      * @return this
      */
    def fontSize(size: Int): Builder = write(ESC, 0x4D, size)

    /**
      * 打印条形码, 使用CODE128编码, 支持所有ASCII字符
      *
      * @param str 条码内容, 必须在ASCII范围内[0, 128)
      * @return this
      */
    def barcode(str: String): Builder = {
      write(0x1D, 0x6B, 73, str.length)
      text(str)
    }

    def nextPage() = write(0x0c)

    def newPage() = {
      font()
      fontSize(0)
      text("\n\n\n")
    }

    /**
      * 得到一个PrintWork, 从而可以执行打印
      *
      * @param outputStream 打印机的outputStream
      * @return 构造好的PrintWork
      */
    def build(outputStream: OutputStream): PrintWork = {
      writer.flush()
      new PrintWork(bao.toByteArray, outputStream)
    }
  }

  def handleCmd(cmd: String)(implicit builder: Builder): Unit = cmd match {
    case null ⇒ builder.lMargin(0)
  }

}

/**
  * 表示一个打印工作的对象, 包含了打印指令, 内容等, 调用run来执行打印,
  * 没有提供公有的构造器, 必须通过Builder来构造, 可以反复调用run, 但是
  */
class PrintWork private(val bytes: Array[Byte], private var os: OutputStream) {

  /**
    * 执行打印任务
    */
  def run() = {
    os write bytes
    os.flush()
  }

  /**
    * 重新设定输出流
    *
    * @param os 输出流
    */
  def resetOutputStream(os: OutputStream) = this.os = os
}
