/*
 * Copyright 2007-2008 the original author or authors.
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

package org.gradle.integtests.samples

import org.gradle.integtests.fixtures.Sample
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.util.TestFile
import org.junit.Rule

/**
 * @author Hans Dockter
 */
class SamplesWebQuickstartIntegrationTest extends AbstractIntegrationSpec {
    @Rule public final Sample sample = new Sample('webApplication/quickstart')

    def "can build a war"() {
        given:
        sample sample

        when:
        run 'clean', 'build'

        then:
        // Check contents of War
        TestFile warContents = file('war-tmp')
        sample.dir.file("build/libs/quickstart.war").unzipTo(warContents)
        warContents.assertHasDescendants(
                'META-INF/MANIFEST.MF',
                'index.jsp',
                'WEB-INF/classes/org/gradle/sample/Greeter.class',
                'WEB-INF/classes/greeting.txt',
                'WEB-INF/lib/log4j-1.2.15.jar',
                'WEB-INF/lib/commons-io-1.4.jar',
        )
    }

    def "can execute servlet"() {
        def portFinder = org.gradle.util.AvailablePortFinder.createPrivate()

        given:
        // Inject some int test stuff
        sample.dir.file('build.gradle') << """
httpPort = ${portFinder.nextAvailable}
stopPort = ${portFinder.nextAvailable}

task runTest << {
    URL url = new URL("http://localhost:\$httpPort/quickstart")
    long expiry = System.currentTimeMillis() + 20000
    while (System.currentTimeMillis() <= expiry) {
        try {
            println url.text
            return
        } catch (ConnectException e) {
            Thread.sleep(200)
        }
    }
    throw new RuntimeException("Timeout waiting for jetty to become available.")
}

"""

        when:
        sample sample
        def runJetty = executer.withTasks("jettyRun").start()

        sample sample
        run 'runTest', 'jettyStop'
        runJetty.waitForFinish()

        then:
        output.contains('hello Gradle')

        when:
        sample sample
        def runJettyWar = executer.withTasks("jettyRunWar").start()

        sample sample
        run 'runTest', 'jettyStop'
        runJettyWar.waitForFinish()

        then:
        output.contains('hello Gradle')
    }
}
