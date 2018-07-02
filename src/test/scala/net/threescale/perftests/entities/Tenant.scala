package net.threescale.perftests.entities

import com.typesafe.config.Config
import net.threescale.perftests.PerfConfig


class Tenant(config: Config) {
  lazy val tenantName: String = config.getString("tenant_name")
  lazy val superdomain: String =
    getProp("superdomain", PerfConfig.config.getString("threescale.superdomain"))
  lazy val adminEndpoint: String =
    getProp("admin_endpoint", s"$tenantName-admin.$superdomain")
  lazy val devEndpoint: String =
    getProp("dev_endpoint", s"$tenantName.$superdomain")
  lazy val stagingDomain: String = getEnvDomain("staging")
  lazy val productionDomain: String = getEnvDomain("production")


  private def getProp(path: String, value: String) =
    if (config.hasPathOrNull(path)) config.getString(path) else value

  private def getEnvDomain(name: String): String =
    getProp(s"${name}_domain", s"$tenantName-apicast-$name.$superdomain")

}
