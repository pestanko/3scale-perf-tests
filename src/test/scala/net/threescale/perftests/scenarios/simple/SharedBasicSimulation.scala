package net.threescale.perftests.scenarios.simple

import net.threescale.perftests.common.SimulationBase
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import net.threescale.perftests.entities.Api

import scala.concurrent.duration._


abstract class SharedBasicSimulation extends SimulationBase {

  /* Api instance */
  lazy val api: Api = null
  /* Base URL of requests sent in scenario 1. */
  lazy val scenario1BaseURL = ""
  /* Final number of users in the simulation. */
  lazy val finalUserCount = 10
  /* Time period after which the number of users is to reach the final number of users. */
  lazy val userCountRampUpTime: FiniteDuration = 20 seconds


  /*
    * Performs initialization of the simulation before it is executed.
    * Initialization must be performed this way in order to allow for subclasses to
    * modify instance variables and for those modifications to affect the resulting
    * simulation configuration.
    */

  /**
    * Creates the HTTP protocol builder used in the simulation.
    */
  def createHttpProtocolBuilder(): HttpProtocolBuilder = {
    val httpProtocolBuilder = http
      .baseURL(scenario1BaseURL)
      .acceptHeader("text/plain")
      .userAgentHeader("Gatling")
    httpProtocolBuilder
  }

  /**
    * Creates the scenario builder used in the simulation.
    */
  def createScenarioBuilder(): ScenarioBuilder = {
    val scenarioBuilder = scenario("Scenario: " + getClass.getSimpleName)
      .exec(
        http("GET request")
          .get("/get")
          .addApiAuthParams(api)
      )
      .exec(
        http("POST request")
          .post("/post")
          .addApiAuthParams(api)
      )
      .exec(
        http("PUT request")
          .put("/put")
          .addApiAuthParams(api)
      )
      .exec(
        http("DELETE request")
          .delete("/delete")
          .addApiAuthParams(api)
      )
    scenarioBuilder
  }


  /**
    * Performs setup of the simulation.
    */
  def doSetUp(): Unit = {
    val theScenarioBuilder: ScenarioBuilder = createScenarioBuilder()
    val theHttpProtocolBuilder: HttpProtocolBuilder = createHttpProtocolBuilder()

    // https://gatling.io/docs/2.3/general/simulation_setup/
    setUp(
      theScenarioBuilder.inject(
        nothingFor(2 seconds),
        atOnceUsers(10),
        rampUsers(finalUserCount) over userCountRampUpTime,
        constantUsersPerSec(5) during (15 seconds), // 4
        constantUsersPerSec(5) during (15 seconds) randomized,
        rampUsersPerSec(10) to 20 during (10 seconds),
        rampUsersPerSec(10) to 20 during (10 seconds) randomized,
        heavisideUsers(1000) over (20 seconds) // 10
      )
    ).protocols(theHttpProtocolBuilder)
  }

  doSetUp()

}
