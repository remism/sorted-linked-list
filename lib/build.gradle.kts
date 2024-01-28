plugins {
    `java-library`
    `maven-publish`
    jacoco
    id ("com.palantir.git-version") version "3.0.0"
    id ("org.ec4j.editorconfig") version "0.0.3"
    id ("io.freefair.lombok") version "8.4"
}

repositories {
    mavenCentral()
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()
group = "demo.sm.sdk"


dependencies {
    testImplementation("org.assertj:assertj-core:3.11.1")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.1")
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

jacoco {
    toolVersion = "0.8.9"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }

    }
}

tasks.check {
    dependsOn("jacocoTestCoverageVerification")
}

tasks.jar {
    archiveBaseName.set(rootProject.name)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = rootProject.name

            from(components["java"])
        }
    }
}