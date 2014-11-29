package com.omidaladini.bbcprogrammedispatch

import org.joda.time._

object BBCProgrammeDispatch {

  import Programmes._
  import com.twitter.util._
  import com.twitter.util.Time._
  import com.twitter.conversions.time._

  val timer = new JavaTimer

  def scheduleBroadcast(list: List[Programme]) {

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

  def scheduleToday = {
    programFeeds.foreach( p => {
      scheduleBroadcast(loadTodaysProgramme(p))
    })
  }

  val dayMillis = 86400 * 1000

  def scheduleTomorrow : Unit = {
    programFeeds.foreach( p => {
      scheduleBroadcast(loadTodaysProgramme(p))
    })

    timer.doAt(Time.now + dayMillis.millis) {
      scheduleTomorrow
    }

  }

  def main(args: Array[String]) {

    scheduleToday
    scheduleTomorrow

  }
}
