package net.threescale.perftests.entities

import com.typesafe.config.Config
import net.threescale.perftests.PerfConfig

class Api(config: Config) {
  lazy val tenantName: String = getProp("tenant_name", "default")
  lazy val apiName: String = getProp("api_name", "api")
  lazy val superdomain: String =
    getProp("superdomain", PerfConfig.config.getString("threescale.superdomain"))

  lazy val api_type: String = getProp("type", "app-key")

  lazy val stagingDomain: String = getEnvDomain("staging")
  lazy val stagingUrl: String = s"https://$stagingDomain"
  lazy val productionDomain: String = getEnvDomain("production")
  lazy val productionUrl: String = s"https://$productionDomain"

  object auth {
    lazy val userKey: String = getProp("user_key", apiName)
    lazy val appKey: String = getProp("app_key", apiName)
    lazy val appId: String = getProp("app_id", apiName)
  }


  private def getProp(path: String, value: String) =
    if (config.hasPathOrNull(path)) config.getString(path) else value

  private def getEnvDomain(name: String): String =
    getProp(s"${name}_domain", s"$apiName-$tenantName-apicast-$name.$superdomain")
}
