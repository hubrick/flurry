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

import com.facebook.nifty.guice.NiftyModule
import com.chronotrack.flurry.gen.Flurry
import com.chronotrack.flurry.service.FlurryThriftHandler
import com.google.inject.{Scopes, Provider, Inject}
import com.chronotrack.flurry.Generator
import com.facebook.nifty.core.{ThriftServerDef, ThriftServerDefBuilder}
import com.google.inject.multibindings.Multibinder
import com.typesafe.config.Config
import org.jboss.netty.logging.{Slf4JLoggerFactory, InternalLoggerFactory}

/**
 * User: ilya
 * Date: 8/16/13
 * Time: 11:44 AM
 */
class ServerModule extends NiftyModule {
  def configureNifty() {

    InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory())
    // Bind the definition
    val thriftServerDefBinder = Multibinder.newSetBinder(binder(), classOf[ThriftServerDef])
    thriftServerDefBinder.addBinding().toProvider(classOf[ServiceDefProvider]).in(Scopes.SINGLETON)
  }
}

class ServiceDefProvider @Inject()(val generator:Generator, val config:Config) extends Provider[ThriftServerDef] {
  def get() = {
    val flurryService = new FlurryThriftHandler(generator)

    // Create the processor
    val processor = new Flurry.Processor(flurryService)

    // Build the server definition
    new ThriftServerDefBuilder().listen(config.getInt("flurry.server-port")).withProcessor(processor).build()
  }
}
