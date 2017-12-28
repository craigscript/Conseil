package tech.cryptonomic.conseil.tezos

import slick.jdbc.PostgresProfile.api._
import tech.cryptonomic.conseil.util.DatabaseUtil

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

/**
  * Functionality for fetching data from the Conseil database.
  */
object ApiOperations {

  lazy val dbHandle: Database = DatabaseUtil.db

  /**
    * Fetches the level of the most recent block stored in the database.
    * @return Max level.
    */
  def fetchMaxLevel(): Try[Int] = Try {
    val op: Future[Option[Int]] = dbHandle.run(Tables.Blocks.map(_.level).max.result)
    val maxLevelOpt = Await.result(op, Duration.Inf)
    maxLevelOpt match {
      case Some(maxLevel) => maxLevel
      case None => throw new Exception("No blocks in the database!")
    }
  }

  /**
    * Fetches the most revent block stored in the database.
    * @return Latest block.
    */
  def fetchLatestBlock(): Try[Tables.BlocksRow] = {
    fetchMaxLevel().flatMap { maxLevel =>
      Try {
          val op = dbHandle.run(Tables.Blocks.filter(_.level === maxLevel).take(1).result)
          val result: Seq[Tables.BlocksRow] = Await.result(op, Duration.Inf)
          result.head
      }
    }
  }
}
