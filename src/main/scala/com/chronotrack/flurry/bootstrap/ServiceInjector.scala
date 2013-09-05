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

package com.chronotrack.flurry.bootstrap

import com.google.inject.{Stage, Guice}

/**
 * User: ilya
 * Date: 8/15/13
 * Time: 7:29 PM
 */


trait ServiceInjector {
  ServiceInjector.inject(this)
}

object ServiceInjector {
  lazy val injector = Guice.createInjector(Stage.PRODUCTION,
                                           new ApplicationModule, new ServerModule)

  def inject(obj: AnyRef) {
    injector.injectMembers(obj)
  }
}