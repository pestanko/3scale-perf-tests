package net.threescale.perftests

import com.typesafe.config.{Config, ConfigFactory}

import java.nio.file.Path

import io.gatling.commons.util.PathHelper._


object PerfConfig {
  // https://stackoverflow.com/questions/35779151/merging-multiple-typesafe-config-files-and-resolving-only-after-they-are-all-mer
  private lazy val globalConfig: Config = parseConfigFile("global.conf")

  def getResourceConfig(path: String, useLocal: Boolean): Config = {
    lazy val external = ConfigFactory.parseResources(path)
    external.withFallback(globalConfig).resolve()
  }

  private def parseConfigFile(name: String): Config = {
    val fullPath: Path = PathHelper.configResourcesDirectory / name
    println(s"[CONFIG] Parsing file: $fullPath")
    ConfigFactory.parseFile(fullPath.toFile)
  }

  lazy val config: Config = {
    val config = globalConfig.resolve()
    println(s"Loaded config: $config")
    config
  }

}
