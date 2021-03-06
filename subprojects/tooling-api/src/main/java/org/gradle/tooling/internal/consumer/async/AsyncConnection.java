/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.tooling.internal.consumer.async;

import org.gradle.tooling.internal.consumer.parameters.ConsumerOperationParameters;
import org.gradle.tooling.internal.protocol.BuildParametersVersion1;
import org.gradle.tooling.internal.protocol.ResultHandlerVersion1;

public interface AsyncConnection {
    void executeBuild(BuildParametersVersion1 buildParameters, ConsumerOperationParameters operationParameters, ResultHandlerVersion1<? super Void> handler) throws IllegalStateException;

    <T> void getModel(Class<T> type, ConsumerOperationParameters operationParameters, ResultHandlerVersion1<T> handler) throws UnsupportedOperationException, IllegalStateException;

    void stop();

    String getDisplayName();
}
