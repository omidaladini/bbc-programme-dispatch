package com.omidaladini.bbcprogrammedispatch


object DAB {

  def send_onairnow(pid: String, text: String) : Unit = {
    println(s"DAB says: $pid $text")
  }

  def send_onairnext(pid: String, text: String) : Unit = {
    println(s"DAB says: $pid $text")
  }

}
