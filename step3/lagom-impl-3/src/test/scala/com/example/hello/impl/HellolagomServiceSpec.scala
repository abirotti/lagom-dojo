package com.example.hello.impl

import com.example.hello.api.{GreetingMessage, HellolagomService}
import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

class HellolagomServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private val server = ServiceTest.startServer(
    ServiceTest.defaultSetup
      .withCassandra(true)
  ) { ctx =>
    new HellolagomApplication(ctx) with LocalServiceLocator
  }

  val client = server.serviceClient.implement[HellolagomService]

  override protected def afterAll() = server.stop()

  "hello-lagom service" should {

    "say hello" in {
      client.hello("Alice").invoke().map { answer =>
        answer should ===("Hello, Alice!")
      }
    }

    "say hello to a person" in {
      client.helloPerson("Alice", "Bobadottir").invoke().map { answer =>
        answer should ===("Hello, Alice Bobadottir!")
      }
    }

    "allow responding with a custom message" in {
      for {
        _ <- client.useGreeting("Bob").invoke(GreetingMessage("Hi"))
        answer <- client.hello("Bob").invoke()
      } yield {
        answer should ===("Hi, Bob!")
      }
    }
  }
}
