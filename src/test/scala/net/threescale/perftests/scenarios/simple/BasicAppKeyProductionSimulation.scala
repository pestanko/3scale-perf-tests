package net.threescale.perftests.scenarios.simple

import net.threescale.perftests.CollectionsFactory
import net.threescale.perftests.entities.Api

import scala.concurrent.duration._

class BasicAppKeyProductionSimulation extends SharedBasicSimulation {
  /*Api instance*/
  override lazy val api: Api = CollectionsFactory.api("app_key")
  /* Base URL of requests sent in scenario 1. */
  override lazy val scenario1BaseURL: String = api.productionUrl
  /* Final number of users in the simulation. */
  override lazy val finalUserCount = 4
  /* Time period after which the number of users is to reach the final number of users. */
  override lazy val userCountRampUpTime: FiniteDuration = 20 seconds
}
