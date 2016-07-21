package com.logistical.tools

/**
  * ErrorHandler有一个方法onError, 接受String, 就是出错的时候对出错信息执行的操作, 输出 或者日志之类的
  */
trait ErrorHandler {
  def onError(msg: String)
}