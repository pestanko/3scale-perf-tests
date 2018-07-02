package net.threescale.perftests

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object Engine {
  def start(): Unit = {
    val props = new GatlingPropertiesBuilder
    props.dataDirectory(PathHelper.dataDirectory.toString)
    props.resultsDirectory(PathHelper.resultsDirectory.toString)
    props.bodiesDirectory(PathHelper.bodiesDirectory.toString)
    props.binariesDirectory(PathHelper.mavenBinariesDirectory.toString)
    Gatling.fromMap(props.build)
  }

  def main(args: Array[String]): Unit = Engine.start()
}