import org.gradle.api.JavaVersion

data class Developer(
  val id: String,
  val name: String,
  val email: String,
  val site: String
)

object Project {
  val name = "lib-query-util"
  val description = "SQL Query boilerplate abstraction"
  val group = "io.vulpine.lib"
  val version = "2.0.0"
  val site = "https://github.com/Vulpine-IO/java-lib-query-util"
  val issues = "https://github.com/Vulpine-IO/java-lib-query-util/issues"
  val license = "MIT"
}

object Scm {
  val connection = "scm:git:git://github.com/Vulpine-IO/java-lib-query-util.git"
  val devConnection = "scm:git:ssh://github.com/Vulpine-IO/java-lib-query-util.git"
  val url = "https://github.com/Vulpine-IO/java-lib-query-util"
}

object Jvm {
  val target = JavaVersion.VERSION_11
}

object Details {
  val project = Project

  val developers = arrayOf(
    Developer("foxcapades", "Elizabeth Paige Harper", "foxcapade@gmail.com", "http://vulpine.io")
  )

  val scm = Scm
}
