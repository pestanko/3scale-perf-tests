package net.threescale.perftests

import com.typesafe.config.Config
import net.threescale.perftests.entities.{Api, Tenant}

import scala.collection.mutable


object CollectionsFactory {

  private lazy val config: Config = PerfConfig.config

  lazy val tenants: mutable.Map[String, Tenant] = mutable.Map[String, Tenant]()
  lazy val apis: mutable.Map[String, Api] = mutable.Map[String, Api]()

  def tenant(name: String): Tenant = {
    if (!tenants.contains(name))
      tenants += (name -> loadTenant(name))
    tenants(name)
  }

  def api(name: String): Api = {
    if (!apis.contains(name))
      apis += (name -> loadApi(name))
    apis(name)
  }

  private def loadTenant(name: String): Tenant = {
    println(s"Loading tenant: $name")
    new Tenant(config.getConfig(s"tenants.$name"))
  }
  private def loadApi(name: String): Api = {
    println(s"Loading api: $name")
    new Api(config.getConfig(s"apis.$name"))
  }


}