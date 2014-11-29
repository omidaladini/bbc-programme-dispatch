package com.omidaladini.bbcprogrammedispatch

import play.api.libs.json._
import scala.io.Source
import java.text.SimpleDateFormat
import java.util.Calendar

object Programmes {

  val programFeeds = List(
    "http://www.bbc.co.uk/radio2/programmes/schedules",
    "http://www.bbc.co.uk/5livesportsextra/programmes/schedules",
    "http://www.bbc.co.uk/worldserviceradio/programmes/schedules")

  val urlDateFormat = new SimpleDateFormat("/yyyy/MM/dd")

  case class Programme(pid: String, start: String)

  def asJsonUrl(url: String) : String = {
    return url + ".json"
  }

  def loadUrlAsJson(url: String) : JsValue = {
    val jsonText = Source.fromURL(asJsonUrl(url)).mkString
    Json.parse(jsonText)
  }

  def loadTodaysFeed(url: String) : JsValue = {
    val today = Calendar.getInstance().getTime()
    val todayUrlSuffix = urlDateFormat.format(today)
    loadUrlAsJson(url + todayUrlSuffix)
  }

  def loadTomorrowsFeed(url: String) : JsValue = {
    val calendar = Calendar.getInstance()
    calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
    val tomorrow = calendar.getTime
    val tomorrowUrlSuffix = urlDateFormat.format(tomorrow)
    loadUrlAsJson(url + tomorrowUrlSuffix)
  }

  def loadProgramms(feed : JsValue) : List[Programme] = {
    implicit val programmeReader = Json.reads[Programme]
    (feed \ "schedule" \ "day" \ "broadcasts").as[List[Programme]]
  }

  def loadTodaysProgramme(url : String) : List[Programme] = {
    loadProgramms(loadTodaysFeed(url))
  }

  def loadTomorrowsProgramme(url : String) : List[Programme] = {
    loadProgramms(loadTomorrowsFeed(url))
  }

  // For testing purposes
  def printProgrammes(list: List[Programme]) {
    list.foreach( p => println(s"Program ${p.pid} ${p.start}"))
  }

}
