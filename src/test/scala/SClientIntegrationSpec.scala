import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FunSpecLike, Matchers}
import scala.concurrent.Await
import scala.concurrent.duration._

import com.akkademy.SClient
import com.akkademy.messages.KeyNotFoundException

class SClientIntegrationSpec extends FunSpecLike with Matchers {
  private val configFactory: Config = ConfigFactory.load()
  private val host: String = configFactory.getString("akkademy-db-client.host")
  private val client = new SClient(host)
  private val timeout: FiniteDuration = 10.seconds

  describe("akkademyDb set") {
    it("should set a value") {
      Await.result(client.set("123", new Integer(123)), timeout)
      val futureResult = client.get("123")
      val result = Await.result(futureResult, 10.seconds)
      result should equal(123)
    }
  }

  describe("akkademyDb get undefined") {
    it("should return an error for an unset value") {
      val key = "456"
      val exception = intercept[KeyNotFoundException] {
        Await.result(client.get(key), timeout)
      }
      assert(exception.key == key)
    }
  }

  describe("akkademyDb del") {
    it("should successfully delete a key") {
      val key = "monkey"
      val value = new Integer(42)

      Await.result(client.set(key, value), timeout)
      val result = Await.result(client.get(key), timeout)
      result should equal(value)
      Await.result(client.del(key), timeout)

      val exception = intercept[KeyNotFoundException] {
        Await.result(client.get(key), timeout)
      }
      assert(exception.key == key)
    }
  }
}