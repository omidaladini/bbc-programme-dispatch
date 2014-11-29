package com.omidaladini.bbcprogrammedispatch

import play.api.libs.json._
import scala.io.Source
import java.text.SimpleDateFormat
import java.util.Calendar

object BBCProgrammeDispatch {

  val programFeeds = List(
    "http://www.bbc.co.uk/radio2/programmes/schedules",
    "http://www.bbc.co.uk/5livesportsextra/programmes/schedules",
    "http://www.bbc.co.uk/worldserviceradio/programmes/schedules")

  val urlDateFormat = new SimpleDateFormat("/yyyy/mm/dd")

  def asJsonUrl(url: String) : String = {
    return url + ".json"
  }

  def loadUrlAsJson(url: String) : JsValue = {
    val jsonText = Source.fromURL(asJsonUrl(url)).mkString
    Json.parse(jsonText)
  }

  def loadTodaysProgramme(url: String) : JsValue = {
    val today = Calendar.getInstance().getTime()
    val todayUrlSuffix = urlDateFormat.format(today)
    loadUrlAsJson(url + todayUrlSuffix)
  }

  def loadTomorrowsProgramme(url: String) : JsValue = {
    val calendar = Calendar.getInstance()
    calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
    val tomorrow = calendar.getTime
    val tomorrowUrlSuffix = urlDateFormat.format(tomorrow)
    loadUrlAsJson(url + tomorrowUrlSuffix)
  }

  def main(args: Array[String]) {


  }
}

