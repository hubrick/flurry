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

package com.chronotrack.flurry.util

/**
 * User: ilya
 * Date: 8/19/13
 * Time: 5:12 PM
 */
object BitUtils {

  case class BitTokens(val time: Long, val worker: Long, val sequence: Long)

  def idToTokens(id: Long, workerBits: Long = 14, sequenceBits: Long = 10) = {
    BitTokens(
      id >>> (workerBits + sequenceBits - 1),
      (id >> sequenceBits) & ((1 << workerBits) - 1),
      id & ((1 << sequenceBits) - 1)
    )
  }

}
