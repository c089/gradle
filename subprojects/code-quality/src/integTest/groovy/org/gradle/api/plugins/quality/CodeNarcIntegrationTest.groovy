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

import org.gradle.integtests.fixtures.AbstractIntegrationSpec

import static org.hamcrest.Matchers.*

class CodeNarcIntegrationTest extends AbstractIntegrationSpec {
    def setup() {
        writeBuildFile()
        writeConfigFile()
    }

    def "analyze empty project"() {
        expect:
        succeeds("check")
    }
  
    def "analyze good code"() {
        file("src/main/groovy/org/gradle/class1.java") << "package org.gradle; class class1 { }"
        file("src/test/groovy/org/gradle/testclass1.java") << "package org.gradle; class testclass1 { }"
        file("src/main/groovy/org/gradle/Class2.groovy") << "package org.gradle; class Class2 { }"
        file("src/test/groovy/org/gradle/TestClass2.groovy") << "package org.gradle; class TestClass2 { }"

        expect:
        succeeds("check")
        file("build/reports/codenarc/main.html").exists()
        file("build/reports/codenarc/test.html").exists()
    }

    def "analyze bad code"() {
        file("src/main/groovy/org/gradle/class1.java") << "package org.gradle; class class1 { }"
        file("src/main/groovy/org/gradle/Class2.groovy") << "package org.gradle; class Class2 { }"
        file("src/test/groovy/org/gradle/TestClass1.java") << "package org.gradle; class TestClass1 { }"
        file("src/test/groovy/org/gradle/testclass2.groovy") << "package org.gradle; class testclass2 { }"

        expect:
        fails("check")
        failure.assertHasDescription("Execution failed for task ':codenarcTest'")
        failure.assertThatCause(startsWith("CodeNarc rule violations were found. See the report at "))
        !file("build/reports/codenarc/main.html").text.contains("Class2")
        file("build/reports/codenarc/test.html").text.contains("testclass2")
    }

    private void writeBuildFile() {
        file("build.gradle") << """
apply plugin: "groovy"
apply plugin: "codenarc"

repositories {
    mavenCentral()
}

dependencies { 
    groovy localGroovy()
}
        """
    }
    
    private void writeConfigFile() {
        file("config/codenarc/codenarc.xml") << """
<ruleset xmlns="http://codenarc.org/ruleset/1.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://codenarc.org/ruleset/1.0 http://codenarc.org/ruleset-schema.xsd"
        xsi:noNamespaceSchemaLocation="http://codenarc.org/ruleset-schema.xsd">
    <ruleset-ref path="rulesets/naming.xml"/>
</ruleset>
        """
    }
}
