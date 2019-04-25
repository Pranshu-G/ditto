/*
 * Copyright (c) 2017-2018 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.services.base.config;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.assertj.core.api.JUnitSoftAssertions;
import org.eclipse.ditto.services.base.config.MetricsConfig.MetricsConfigValue;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * Unit test for {@link org.eclipse.ditto.services.base.config.DefaultMetricsConfig}.
 */
public final class DefaultMetricsConfigTest {

    private static Config metricsTestConf;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @BeforeClass
    public static void initTestFixture() {
        metricsTestConf = ConfigFactory.load("metrics-test");
    }

    @Test
    public void assertImmutability() {
        assertInstancesOf(DefaultMetricsConfig.class, areImmutable());
    }

    @Test
    public void testHashCodeAndEquals() {
        EqualsVerifier.forClass(DefaultMetricsConfig.class)
                .usingGetClass()
                .verify();
    }

    @Test
    public void testSerializationAndDeserialization() throws IOException, ClassNotFoundException {
        final DefaultMetricsConfig underTest = DefaultMetricsConfig.of(metricsTestConf);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutput objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(underTest);
        objectOutputStream.close();

        final byte[] underTestSerialized = byteArrayOutputStream.toByteArray();

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(underTestSerialized);
        final ObjectInput objectInputStream = new ObjectInputStream(byteArrayInputStream);
        final Object underTestDeserialized = objectInputStream.readObject();

        softly.assertThat(underTestDeserialized).isEqualTo(underTest);
    }

    @Test
    public void gettersReturnDefaultValuesIfNotConfigured() {
        final DefaultMetricsConfig underTest = DefaultMetricsConfig.of(ConfigFactory.empty());

        softly.assertThat(underTest.isSystemMetricsEnabled())
                .as(MetricsConfigValue.SYSTEM_METRICS_ENABLED.getConfigPath())
                .isEqualTo(MetricsConfigValue.SYSTEM_METRICS_ENABLED.getDefaultValue());
        softly.assertThat(underTest.isPrometheusEnabled())
                .as(MetricsConfigValue.PROMETHEUS_ENABLED.getConfigPath())
                .isEqualTo(MetricsConfigValue.PROMETHEUS_ENABLED.getDefaultValue());
        softly.assertThat(underTest.getPrometheusHostname())
                .as(MetricsConfigValue.PROMETHEUS_HOSTNAME.getConfigPath())
                .isEqualTo(MetricsConfigValue.PROMETHEUS_HOSTNAME.getDefaultValue());
        softly.assertThat(underTest.getPrometheusPort())
                .as(MetricsConfigValue.PROMETHEUS_PORT.getConfigPath())
                .isEqualTo(MetricsConfigValue.PROMETHEUS_PORT.getDefaultValue());
    }

    @Test
    public void gettersReturnConfiguredValues() {
        final DefaultMetricsConfig underTest = DefaultMetricsConfig.of(metricsTestConf);

        softly.assertThat(underTest.isSystemMetricsEnabled())
                .as(MetricsConfigValue.SYSTEM_METRICS_ENABLED.getConfigPath())
                .isTrue();
        softly.assertThat(underTest.isPrometheusEnabled())
                .as(MetricsConfigValue.PROMETHEUS_ENABLED.getConfigPath())
                .isTrue();
        softly.assertThat(underTest.getPrometheusHostname())
                .as(MetricsConfigValue.PROMETHEUS_HOSTNAME.getConfigPath())
                .isEqualTo("1.1.1.1");
        softly.assertThat(underTest.getPrometheusPort())
                .as(MetricsConfigValue.PROMETHEUS_PORT.getConfigPath())
                .isEqualTo(9999);
    }

}
