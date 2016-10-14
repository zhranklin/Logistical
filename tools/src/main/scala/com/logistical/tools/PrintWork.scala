package com.logistical.tools

import java.io.{ByteArrayOutputStream, OutputStream, OutputStreamWriter}

import com.logistical.model.Order

import scala.annotation.varargs

object PrintWork {
  import Font._

  def testPrintWork(o: OutputStream) = builder().text("你妈嗨").build(o)
  def testPrintWork1(o: OutputStream) = builder()
    .text("默认")
//    .lMargin(8)
//    .rMargin(8)
    .fontSize(0).text("大号")
    .fontSize(1).text("中号")
    .fontSize(2).text("小号")
    .font(ITALIC).text("斜体")
    .font(BOLD).text("粗体")
    .font(DOUBLE_HEIGHT).text("倍高")
    .font(DOUBLE_WIDTH).text("倍宽")
    .font(FRAME).text("边框")
    .font(UNDERLINE).text("下划线\n")
    .barcode("12345678")
    .build(o)

  /**
    * 获得一个builder, 用来构造PrintWork
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
    * @see [[Builder]]
    */
  class Builder private[PrintWork](encoding: String) {

    private val bao = new ByteArrayOutputStream()
    private val writer = new OutputStreamWriter(bao, encoding)
    private val ESC = 0x1B
    private val GS = 0x1D

    //初始化打印机
    write(ESC, 0x40)
    write(GS, 'h', 30)
    align(1)

    /**
      * 打印订单 未分页
      * @param o 订单对象
      */
    def printOrder(o: Order) = {

    }

    def write(chars: Int*): Builder = {
      chars foreach writer.write
      this
    }

    /**
      * 输出字符串
      * @param str 输出的内容
      * @return this
      */
    def text(str: String): Builder = {
      writer write str
      this
    }

    /**
      * 设置对齐方式
      * @param align 0: 左对齐(默认), 1: 居中, 2: 右对齐
      * @return this
      */
    def align(align: Int): Builder = write(ESC, 0x61, align)

    /**
      * 打印制表符
      * @param times 数字, 表示打印的制表符的个数
      * @return this
      */
    def tab(times: Int): Builder = write(Seq.fill[Int](times)('\t'): _*)

    /**
      * 设置行间距
      * @param gap 行间距的像素点数, 最大值255
      * @return this
      */
    def gap(gap: Int): Builder = write(ESC, 0x33, gap)

    /**
      * 设置左边距
      * @param margin 以8点为单位, 范围(左+右): 58mm宽: [0, 48), 80mm宽: [0, 72)
      * @return this
      */
    def lMargin(margin: Int): Builder = write(ESC, 0x6C, margin)

    def rMargin(margin: Int): Builder = write(ESC, 0x51, margin)

    /**
      * 设置字体样式
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
      * @param size 范围为[0, 2]
      * @return this
      */
    def fontSize(size: Int): Builder = write(ESC, 0x4D, size)

    /**
      * 打印条形码, 使用CODE128编码, 支持所有ASCII字符
      * @param str 条码内容, 必须在ASCII范围内[0, 128)
      * @return this
      */
    def barcode(str: String): Builder = {
      write(0x1D, 0x6B, 73, str.length)
      text(str)
    }

    /**
      * 得到一个PrintWork, 从而可以执行打印
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
    * @param os 输出流
    */
  def resetOutputStream(os: OutputStream) = this.os = os
}
