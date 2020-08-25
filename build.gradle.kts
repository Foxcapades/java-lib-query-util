plugins {
  `java-library`
  `maven-publish`
  signing
}

group = Project.group
version = Project.version

java {
  sourceCompatibility = Jvm.target
  targetCompatibility = Jvm.target

  withSourcesJar()
  withJavadocJar()
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  testImplementation("org.mockito:mockito-core:2.+")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

publishing {
  repositories {
    maven {
      name = "nexus"
      url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = project.findProperty("nexus.user") as String? ?: ""
        password = project.findProperty("nexus.pass") as String? ?: ""
      }
    }
  }
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
      pom {
        name.set(Project.name)
        description.set(Project.description)
        url.set(Project.site)
        licenses {
          license {
            name.set(Project.license)
          }
        }
        developers {
          Details.developers.forEach {
            developer {
              id.set(it.id)
              name.set(it.name)
              email.set(it.email)
              url.set(it.site)
            }
          }
        }
        scm {
          connection.set(Scm.connection)
          developerConnection.set(Scm.devConnection)
          url.set(Scm.url)
        }
      }
    }
  }
}

artifacts {
  archives(tasks["sourcesJar"])
  archives(tasks["javadocJar"])
  archives(tasks["jar"])
}

signing {
  useGpgCmd()
  sign(configurations.archives.get())
  sign(publishing.publications["maven"])
}
