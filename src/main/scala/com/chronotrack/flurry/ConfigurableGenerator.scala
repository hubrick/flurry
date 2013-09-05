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

package com.chronotrack.flurry

import com.google.inject.Inject
import com.typesafe.config.Config
import com.google.inject.name.Named
import com.chronotrack.flurry.worker.WorkerIdGenerator

/**
 * User: ilya
 * Date: 8/15/13
 * Time: 1:17 PM
 */
class ConfigurableGenerator @Inject()(val workerIdGenerator: WorkerIdGenerator,
                                      @Named("epochStart") val epochStart: Long,
                                      @Named("workerIdBits") val workerIdBits: Long,
                                      @Named("sequenceBits") val sequenceBits: Long) extends Generator {

}

object ConfigurableGenerator {

  def apply(workerIdGenerator: WorkerIdGenerator,
            epochStart: Long,
            workerIdBits: Long,
            sequenceBits: Long) = {
    new ConfigurableGenerator(workerIdGenerator, epochStart, workerIdBits, sequenceBits)
  }


}
