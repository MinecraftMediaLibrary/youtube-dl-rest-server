plugins{
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.pulsebeat02"
version = "v1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {

    compileJava {
        options.encoding = "UTF-8"
    }

    shadowJar {
        archiveBaseName.set("youtube-dl-rest-server")
        relocate("spark", "io.github.pulsebeat02.lib.spark")
    }

    withType(Jar::class) {
        manifest {
            attributes["Manifest-Version"] = "1.0"
            attributes["Main-Class"] = "io.github.pulsebeat02.RestServer"
        }
    }

}

sourceSets {
    main {
        java.srcDir("src/main/java")
        resources.srcDir("src/main/resources")
    }
}