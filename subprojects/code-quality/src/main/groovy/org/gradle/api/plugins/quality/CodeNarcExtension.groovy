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
package org.gradle.api.plugins.quality

import org.gradle.api.Project

class CodeNarcExtension extends CodeQualityExtension {
    CodeNarcExtension(Project project) {
        super(project)
    }

    /**
     * The CodeNarc configuration file to use. Defaults to <tt>config/codenarc/codenarc.xml</tt>.
     */
    File configFile

    /**
     * The format type of the CodeNarc report. One of <tt>html</tt>, <tt>xml</tt>, <tt>text</tt>, <tt>console</tt>.
     * Defaults to <tt>html</tt>.
     */
    String reportFormat

    /**
     * The directory where CodeNarc reports will be saved. Defaults to <tt>$reportsDir/codenarc</tt>.
     */
    File reportsDir
}
