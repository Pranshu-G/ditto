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

import * as API from '../api.js';
import * as Utils from '../utils.js';
/* eslint-disable prefer-const */
/* eslint-disable require-jsdoc */
import * as Environments from './environments.js';

let dom = {
  bearer: null,
  userName: null,
  password: null,
  devOpsUserName: null,
  devOpsPassword: null,
  dittoPreAuthenticatedUsername: null,
  collapseConnections: null,
};

export function ready() {
  Utils.getAllElementsById(dom);

  document.getElementById('authorizeBearer').onclick = () => {
    Environments.current().useBasicAuth = false;
    Environments.current().useDittoPreAuthenticatedAuth = false;
    Environments.current().bearer = dom.bearer.value;
    Environments.environmentsJsonChanged('authorization');
  };

  document.getElementById('authorizeBasic').onclick = () => {
    Environments.current().useBasicAuth = true;
    Environments.current().useDittoPreAuthenticatedAuth = false;
    Environments.current().usernamePassword = dom.userName.value + ':' + dom.password.value;
    Environments.current().usernamePasswordDevOps = dom.devOpsUserName.value + ':' + dom.devOpsPassword.value;
    Environments.environmentsJsonChanged('authorization');
  };

  document.getElementById('authorizePreAuthenticated').onclick = () => {
    Environments.current().useBasicAuth = false;
    Environments.current().useDittoPreAuthenticatedAuth = true;
    Environments.current().dittoPreAuthenticatedUsername = dom.dittoPreAuthenticatedUsername.value;
    Environments.environmentsJsonChanged();
  };
}

export function onEnvironmentChanged() {
  let usernamePassword = Environments.current().usernamePassword ? Environments.current().usernamePassword : ':';
  dom.userName.value = usernamePassword.split(':')[0];
  dom.password.value = usernamePassword.split(':')[1];
  usernamePassword = Environments.current().usernamePasswordDevOps ?
      Environments.current().usernamePasswordDevOps :
      ':';
  dom.devOpsUserName.value = usernamePassword.split(':')[0];
  dom.devOpsPassword.value = usernamePassword.split(':')[1];
  dom.bearer.value = Environments.current().bearer ? Environments.current().bearer : '';
  dom.dittoPreAuthenticatedUsername.value = Environments.current().dittoPreAuthenticatedUsername ?
                                            Environments.current().dittoPreAuthenticatedUsername :
                                            '';
  API.setAuthHeader(dom.collapseConnections.classList.contains('show'));
}
