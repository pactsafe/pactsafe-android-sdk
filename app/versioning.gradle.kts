import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

fun readVersion(): Properties {
    val versionFile = File(project.rootDir, "version.properties")

    val version = Properties()

    FileInputStream(versionFile).use { stream ->
        version.load(stream)
    }

    return version
}

fun readVersionActual() {
    val version = readVersion()
    println("VERSION: ${version["version"]}")
}

fun incrementMajorVersion() {
    val versionFile = File(project.rootDir, "version.properties")
    val version = readVersion()

    var major = (version["major"] ?: 0) as Int
    major++

    version.setProperty("major", major.toString())
    version.setProperty("minor", "0")
    version.setProperty("revision", "0")
    version.setProperty("version", "${version["major"] ?: 0}.${version["minor"] ?: 0}.${version["revision"] ?: 0}")

    FileOutputStream(versionFile).use { stream ->
        version.store(stream, null)
    }

    println("Major Version has been updated to: ${version["version"]}")
}

fun incrementMinorVersion() {
    val versionFile = File(project.rootDir, "version.properties")
    val version = readVersion()

    var minor = (version["minor"] ?: 0) as Int
    minor++

    version.setProperty("minor", minor.toString())
    version.setProperty("revision", "0")
    version.setProperty("version", "${version["major"] ?: 0}.${version["minor"] ?: 0}.${version["revision"] ?: 0}")

    FileOutputStream(versionFile).use { stream ->
        version.store(stream, null)
    }

    println("Major Version has been updated to: ${version["version"]}")

}

fun incrementRevisionVersion() {
    val versionFile = File(project.rootDir, "version.properties")
    val version = readVersion()

    var revision = (version["revision"] ?: 0) as Int
    revision++

    version.setProperty("revision", revision.toString())
    version.setProperty("version", "${version["major"] ?: 0}.${version["minor"] ?: 0}.${version["revision"] ?: 0}")


    FileOutputStream(versionFile).use { stream ->
        version.store(stream, null)
    }

    println("Major Version has been updated to: ${version["version"]}")
}

tasks.register("incrementMajorVersion") {
    doLast {
        incrementMajorVersion()
    }
}

tasks.register("incrementMinorVersion") {
    doLast {
        incrementMinorVersion()
    }
}

tasks.register("incrementRevisionVersion") {
    doLast {
        incrementRevisionVersion()
    }
}

tasks.register("readVersionActual") {
    doLast {
        readVersionActual()
    }
}