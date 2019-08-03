
import net.minecraftforge.gradle.user.patcherUser.forge.ForgeExtension
import org.yaml.snakeyaml.Yaml
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*

buildscript {
    repositories {
        maven { setUrl("https://files.minecraftforge.net/maven") }
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT")
        classpath("org.yaml:snakeyaml:1.24")
    }
}

plugins {
    application
    java
    maven
    idea
    eclipse
    id("com.matthewprenger.cursegradle") version "1.3.0"
}

apply(plugin = "net.minecraftforge.gradle.forge")

val config = getBuildConfig()

version = config.mod!!.version!!
group = config.mod!!.group!!

base {
    archivesBaseName = config.mod!!.basename!!
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val sourceSets = the<JavaPluginConvention>().sourceSets
val minecraft: ForgeExtension = extensions.getByType(ForgeExtension::class.java)

configure<ForgeExtension> {
    version = config.forge!!.minecraftVersion!! + "-" + config.forge!!.forgeVersion!!
    runDir = "run";
    mappings = config.forge!!.mcpMappings!!.channel!! + "_" + config.forge!!.mcpMappings!!.version!!
//    makeObfSourceJar = false // an Srg named sources jar is made by default. uncomment this to disable.
}

tasks.getByName<Jar>("jar") {
    manifest {
        attributes(mapOf(
                "Specification-Title" to config.mod!!.name!!,
                "Specification-Vendor" to config.mod!!.vendor!!,
                "Specification-Version" to "1",
                "Implementation-Title" to config.mod!!.name!!,
                "Implementation-Version" to config.mod!!.version!!,
                "Implementation-Vendor" to config.mod!!.vendor!!,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        ))
    }
}

// Fixes IntelliJ bug that prevents assets from working
// https://stackoverflow.com/a/27624502
sourceSets.getByName("main").output.resourcesDir = sourceSets.getByName("main").output.classesDirs.singleFile

val processResources = tasks.getByName("processResources") as ProcessResources
processResources.apply {
    inputs.property("version", config.mod!!.version!!)
    inputs.property("mcversion", config.forge!!.minecraftVersion!!)

    // replace stuff in mcmod.info, nothing else
    from(sourceSets.getByName("main").resources.srcDirs) {
        include("mcmod.info", "Values.java")

        // replace version and mcversion
        expand(mapOf("version" to config.mod!!.version!!, "mcversion" to config.forge!!.minecraftVersion!!))
    }
}

class BuildConfig() {
    var mod : Mod? = null
    class Mod() {
        var version : String? = null
        var group : String? = null
        var basename : String? = null
        var vendor : String? = null
        var name : String? = null
    }
    var forge : Forge? = null
    class Forge() {
        var minecraftVersion: String? = null
        var forgeVersion: String? = null
        var mcpMappings: MCPMappings? = null
        class MCPMappings() {
            var channel: String? = null
            var version: String? = null
        }
    }
}

fun getBuildConfig() : BuildConfig {
    return Yaml().loadAs(FileReader("build.yaml"), BuildConfig::class.java)
}

//    // grab buildNumber
//    ext.buildnumber = "DEV" // this will be referenced as simply project.buildnumber from now on.
//    if (System.getenv().BUILD_NUMBER)
//        project.buildnumber = System.getenv().BUILD_NUMBER
//    logger.lifecycle "BUILDING VERSION: " + project.buildnumber
//}
