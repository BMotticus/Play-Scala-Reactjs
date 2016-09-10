package filters

import akka.stream.Materializer
import javax.inject._
import akka.util.ByteString
import play.api.libs.streams.Accumulator
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

/**
 * This is a simple filter that adds a header to all requests. It's
 * added to the application's list of filters by the
 * [[ExampleFilter]] class.
 *
 * @param mat [[Materializer]]This object is needed to handle streaming of requests
 * and responses.
 * @param exec [[ExecutionContext]]This class is needed to execute code asynchronously.
 * It is used below by the `map` method.
 */
@Singleton
class ExampleFilter @Inject()(
    implicit override val mat: Materializer,
    exec: ExecutionContext) extends Filter {

  override def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {
    // Run the next filter in the chain. This will call other filters
    // and eventually call the action. Take the result and modify it
    // by adding a new header.
    nextFilter(requestHeader).map { result =>
      result.withHeaders("X-ExampleFilter" -> "foo")
    }
  }

}

/**
  * A `lower level` filter API called [[EssentialFilter]] which gives you full access to the body of the request. 
  * This API allows you to wrap [[EssentialAction]] with another action. 
  * Here is the above filter example rewritten as an [[EssentialFilter]]:
  * [[ExampleEssentialFilter]] class.
  *
  * @param exec [[ExecutionContext]]This class is needed to execute code asynchronously.
  * It is used below by the `map` method.
  */
@Singleton
class ExampleEssentialFilter @Inject()(exec: ExecutionContext) extends EssentialFilter {
  def apply(nextFilter: EssentialAction) = new EssentialAction {
    def apply(requestHeader: RequestHeader): Accumulator[ByteString, Result] = {
      val accumulator = nextFilter(requestHeader)
      accumulator.map { result =>
        result.withHeaders("X-ExampleFilter" -> "foo")
      }
    }
  }

}