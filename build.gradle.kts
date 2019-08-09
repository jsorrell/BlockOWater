
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*
import net.minecraftforge.gradle.user.patcherUser.forge.ForgeExtension
import org.yaml.snakeyaml.Yaml

buildscript {
    repositories {
        maven { url = uri("https://files.minecraftforge.net/maven") }
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT")
        classpath("org.yaml:snakeyaml:1.24")
    }
}

plugins {
    java
    maven
    idea
    eclipse
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

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
}

val sourceSets = the<JavaPluginConvention>().sourceSets

// Fixes IntelliJ missing assets issue
val mainOutput = File("build/main")
sourceSets.getByName("main").java.outputDir = mainOutput
sourceSets.getByName("main").output.resourcesDir = mainOutput

configure<ForgeExtension> {
    version = config.forge!!.minecraftVersion!! + "-" + config.forge!!.forgeVersion!!
    runDir = "run";
    mappings = config.forge!!.mcpMappings!!.channel!! + "_" + config.forge!!.mcpMappings!!.version!!
//    makeObfSourceJar = false // an Srg named sources jar is made by default. uncomment this to disable.

    replaceIn("Values.java")
    replace("@VERSION@", config.mod!!.version)
    replace("@BUILD_NUMBER@", config.mod!!.buildNo)
    replace("@MC_VERSION@", config.forge!!.minecraftVersion)
    replace("@FORGE_VERSION@", config.forge!!.forgeVersion)
}

tasks.getByName<Jar>("jar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val processResources = tasks.getByName("processResources") as ProcessResources
processResources.apply {
    inputs.property("version", config.mod!!.version!!)
    inputs.property("mcversion", config.forge!!.minecraftVersion!!)

    // replace stuff in mcmod.info, nothing else
    from(sourceSets.getByName("main").resources.srcDirs) {
        include("mcmod.info")

        // replace version and mcversion
        expand(mapOf("version" to config.mod!!.version!!, "mcversion" to config.forge!!.minecraftVersion!!))

        // copy everything else, that's not the mcmod.info
        from(sourceSets.getByName("main").resources.srcDirs) {
            exclude("mcmod.info")
        }
    }
}

class BuildConfig() {
    var mod : Mod? = null
    class Mod() {
        var version : String? = null
        var buildNo : String? = null
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
    val config = Yaml().loadAs(FileReader("build.yaml"), BuildConfig::class.java)
    config.mod!!.buildNo = "DEV";
    if (System.getenv().containsKey("BUILD_NUMBER")) {
        config.mod!!.buildNo = System.getenv("BUILD_NUMBER")
    }
    logger.lifecycle("BUILDING VERSION: " + config.mod!!.buildNo)

    return config
}
