package com.example.hello.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.example.hello.api.Person
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class HellolagomEntitySpec extends WordSpec with Matchers with BeforeAndAfterAll {

  private val system = ActorSystem("HellolagomEntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor(HellolagomSerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private def withTestDriver(block: PersistentEntityTestDriver[HellolagomCommand[_], HellolagomEvent, HellolagomState] => Unit): Unit = {
    val driver = new PersistentEntityTestDriver(system, new HellolagomEntity, "hello-lagom-1")
    block(driver)
    driver.getAllIssues should have size 0
  }

  "hello-lagom entity" should {

    "say hello by default" in withTestDriver { driver =>
      val outcome = driver.run(Hello("Alice"))
      outcome.replies should contain only "Hello, Alice!"
    }

    "say hello to a fully specified person" in withTestDriver { driver =>
      val outcome = driver.run(Hello("Alice", "Bobadottir"))
      outcome.replies should contain only "Hello, Alice Bobadottir!"
    }

    "say hello to a person" in withTestDriver { driver =>
      val outcome = driver.run(Hello(Person("Alice", "Bobadottir")))
      outcome.replies should contain only "Hello, Alice Bobadottir!"
    }

    "allow updating the greeting message" in withTestDriver { driver =>
      val outcome1 = driver.run(UseGreetingMessage("Hi"))
      outcome1.events should contain only GreetingMessageChanged("Hi")
      val outcome2 = driver.run(Hello("Alice"))
      outcome2.replies should contain only "Hi, Alice!"
    }

  }
}
