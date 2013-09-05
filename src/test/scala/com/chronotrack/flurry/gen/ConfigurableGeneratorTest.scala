/*
 * Copyright 2013 ChronoTrack
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.chronotrack.flurry.gen

import org.scalatest.FunSuite
import java.util.concurrent.{TimeUnit, ConcurrentHashMap, Executors, ExecutorService}
import java.util.concurrent.atomic.AtomicInteger
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory
import com.chronotrack.flurry.bootstrap.ServiceInjector
import com.google.inject.Inject
import com.chronotrack.flurry.Generator

class ConfigurableGeneratorTest extends FunSuite with ServiceInjector {

  @Inject
  val generator:Generator = generator

//  private def newGenerator() =
//    ConfigurableGenerator(1L, 1376587750000L, 13L, 12L)

  test("generate a few ids") {
//    val generator = newGenerator()
    println(generator.getId)
    println(generator.getId)
    println(generator.getId)
  }

  test("generate multiple ids in thread and make sure no dups are generated") {
//    val generator = newGenerator()
    val ids = new ConcurrentHashMap[Long, AtomicInteger]()

    val pool: ExecutorService = Executors.newFixedThreadPool(50)
    val tasks = for (i <- 0 until 100) yield
      new Runnable {
        def run() {
          for (i <- 0 until 10) {
            val id = generator.getId
            ids.putIfAbsent(id, new AtomicInteger(0))
            ids.get(id).incrementAndGet()
          }
        }
      }
    tasks.foreach(pool.submit(_))
    pool.awaitTermination(2, TimeUnit.SECONDS)

    ids.values().foreach(v => assert(v.get() === 1))

//    println(ids)

  }

}
