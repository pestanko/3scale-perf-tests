package net.threescale.perftests.scenarios.simple

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import net.threescale.perftests.CollectionsFactory
import net.threescale.perftests.common.SimulationBase
import net.threescale.perftests.entities.Api


abstract class BasicScenarioSimulation extends SimulationBase {

  lazy val userKeyApi: Api = CollectionsFactory.api("user_key")
  lazy val appKeyApi: Api = CollectionsFactory.api("app_key")

  lazy val userKeyStagingProtocolBuilder: HttpProtocolBuilder = http
    .baseURL(userKeyApi.stagingUrl)

  lazy val userKeyProductionProtocolBuilder: HttpProtocolBuilder = http
    .baseURL(userKeyApi.productionUrl)


  lazy val userKeyApiScenario: ScenarioBuilder = scenario("Simple User Key in params")
    .exec(
      http("GET request")
        .get("/get")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("POST request")
        .post("/post")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("PUT request")
        .put("/put")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("DELETE request")
        .delete("/delete")
        .addApiAuthParams(userKeyApi)
    )

  setUp(
    userKeyApiScenario.inject(atOnceUsers(4))
      .protocols(userKeyStagingProtocolBuilder)
  )
}
