package net.threescale.perftests.common

import io.gatling.core.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import net.threescale.perftests.entities.Api

abstract class SimulationBase extends Simulation {


  /**
    * Extends the HttpRequestBuilder to add custom api auth method based on the API type
    * @param builder HttpRequestBuilder instance
    */
  implicit class RequestBuilderExtendedForKeysExtraction(builder: HttpRequestBuilder) {
    def addApiAuthParams(api: Api): HttpRequestBuilder = {
      addAuthForApi(api, builder)
    }
  }

  /**
    * Adds User key to the request
    * @param api Api Instance
    * @param builder HttpRequestBuilder instance
    * @return HttpRequestBuilder instance
    */
  protected def addUserKeyParam(api: Api, builder: HttpRequestBuilder): HttpRequestBuilder =
    builder.queryParam("user_key", api.auth.userKey)


  /**
    * Adds app key and app id to the request
    * @param api Api Instance
    * @param builder HttpRequestBuilder instance
    * @return HttpRequestBuilder instance
    */
  protected def addAppKeyParam(api: Api, builder: HttpRequestBuilder): HttpRequestBuilder =
    builder.queryParam("app_key", api.auth.appKey).queryParam("app_id", api.auth.appId)


  /**
    * Authentication to the to the request
    * @param api Api Instance
    * @param builder HttpRequestBuilder instance
    * @return HttpRequestBuilder instance
    */
  protected def addAuthForApi(api: Api, builder: HttpRequestBuilder): HttpRequestBuilder = {
    api.api_type match {
      case "user_key" => addUserKeyParam(api, builder)
      case "app_key" => addAppKeyParam(api, builder)
    }
  }

}
