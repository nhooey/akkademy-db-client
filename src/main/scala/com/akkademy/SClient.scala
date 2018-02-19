package com.akkademy

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.Logger
import scala.concurrent.Future
import scala.concurrent.duration._

import com.akkademy.messages.{DelRequest, GetRequest, SetRequest}

class SClient(remoteAddress: String) {
  private implicit val timeout: Timeout = Timeout(2.seconds)
  private implicit val system: ActorSystem = ActorSystem("LocalSystem")
  private val url = s"akka.tcp://akkademy@$remoteAddress/user/akkademy-db"
  private val remoteDb = system.actorSelection(url)
  private val log = Logger[SClient]

  log.info("SClient connecting to: '{}'", url)

  def set(key: String, value: Object): Future[Any] = {
    log.info("set key: {}, value: {}", key, value)
    remoteDb ? SetRequest(key, value)
  }

  def get(key: String): Future[Any] = {
    log.info("get key: {}", key)
    remoteDb ? GetRequest(key)
  }

  def del(key: String): Future[Any] = {
    log.info("del key: {}", key)
    remoteDb ? DelRequest(key)
  }
}