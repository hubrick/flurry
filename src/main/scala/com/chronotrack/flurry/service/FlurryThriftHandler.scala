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

package com.chronotrack.flurry.service

import com.chronotrack.flurry.gen.{IdDetailed, Flurry}
import com.chronotrack.flurry.Generator
import com.chronotrack.flurry.util.BitUtils

/**
 * User: ilya
 * Date: 8/16/13
 * Time: 11:33 AM
 */
class FlurryThriftHandler(val generator:Generator) extends Flurry.Iface {

  def get_worker_id() = {
    generator.workerId
  }

  def get_id() = {
    generator.getId
  }

  def get_id_detailed() = {
    val id = generator.getId
    val tokens = BitUtils.idToTokens(id, generator.workerIdBits, generator.sequenceBits)
    new IdDetailed(id, tokens.time, tokens.worker.toInt, tokens.sequence.toInt)
  }
}
