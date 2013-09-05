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

package com.chronotrack.flurry.worker

import com.google.inject.Inject
import com.google.inject.name.Named

/**
 * User: ilya
 * Date: 8/16/13
 * Time: 4:19 PM
 */
class HostWorkerIdGenerator @Inject()(@Named("workerIdBits") val workerIdBits:Long) extends WorkerIdGenerator {

  private[this] val maxWorkerId = -1L ^ (-1L << workerIdBits)

  val workerId = java.lang.Long.parseLong(Integer.toBinaryString(java.net.InetAddress.getLocalHost.getHostName.hashCode), 2) % maxWorkerId

}
