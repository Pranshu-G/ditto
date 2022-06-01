/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.edge.service.dispatching;

import akka.actor.AbstractActor.Receive;
import akka.actor.ActorContext;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;

public final class NoOpEdgeCommandForwarderExtension implements EdgeCommandForwarderExtension {

    public NoOpEdgeCommandForwarderExtension(final ActorSystem actorSystem) {
    }

    @Override
    public Receive getReceiveExtension(final ActorContext actorContext) {
        return ReceiveBuilder.create().build();
    }

}
