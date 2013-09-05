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

import com.chronotrack.flurry.bootstrap.ServiceInjector
import com.google.inject.Inject
import com.facebook.nifty.core.NiftyBootstrap

/**
 * User: ilya
 * Date: 8/16/13
 * Time: 10:21 AM
 */
object Runner extends App {

  ServiceInjector.injector.getInstance(classOf[NiftyRunner]).start()

}

class NiftyRunner @Inject()(val bootstrap: NiftyBootstrap) {

  def start() {
    // Start the server
    bootstrap.start()

    // Arrange to stop the server at shutdown
    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run() {
        bootstrap.stop()
      }
    })
  }

}
