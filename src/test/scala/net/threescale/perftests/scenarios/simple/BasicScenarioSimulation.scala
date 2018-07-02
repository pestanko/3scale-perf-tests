package net.threescale.perftests.scenarios.simple

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import net.threescale.perftests.CollectionsFactory
import net.threescale.perftests.common.BaseSimulation
import net.threescale.perftests.entities.Api


class BasicScenarioSimulation extends BaseSimulation {

  lazy val userKeyApi: Api = CollectionsFactory.api("user_key")
  lazy val appKeyApi: Api = CollectionsFactory.api("app_key")

  lazy val userKeyStagingProtocolBuilder: HttpProtocolBuilder = http
    .baseURL(userKeyApi.stagingUrl)


  lazy val userKeyApiScenario: ScenarioBuilder = scenario("Simple User Key in params")
    .exec(
      http("User key GET request")
        .get("/get")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("User key POST request")
        .post("/post")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("User key PUT request")
        .put("/put")
        .addApiAuthParams(userKeyApi)
    )
    .exec(
      http("User key DELETE request")
        .delete("/delete")
        .addApiAuthParams(userKeyApi)
    )

  setUp(
    userKeyApiScenario.inject(atOnceUsers(1))
  ).protocols(userKeyStagingProtocolBuilder)

}
