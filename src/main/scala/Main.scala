package com.omidaladini.bbcprogrammedispatch

import org.joda.time._

object BBCProgrammeDispatch {

  import Programmes._
  import com.twitter.util._
  import com.twitter.util.Time._
  import com.twitter.conversions.time._

  def scheduleBroadcast(list: List[Programme]) {

    val timer = new JavaTimer

    list.foreach( prog => {
      val format = new com.twitter.util.TimeFormat("yyyy-MM-dd'T'HH:mm:ss")
      val time = format.parse(prog.start)
      val sinceNow = time.sinceNow.inNanoseconds / 1000000

      if(sinceNow > 0) {
        timer.doAt(Time.now + sinceNow.millis) {
          DAB.send_onairnow(prog.pid, "On Air Now")
        }
      }
    })
  }

  def main(args: Array[String]) {

    programFeeds.foreach( p => {
      scheduleBroadcast(loadTodaysProgramme(p))
      scheduleBroadcast(loadTomorrowsProgramme(p))
    })

  }
}
