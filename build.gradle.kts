plugins {
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    google()
    maven(url = "https://maven.google.com")
    mavenCentral()
}

application {
    mainClassName = "com.olekdia.Main"
    applicationDefaultJvmArgs = listOf("-javaagent:InstrumentationAgent.jar")
}

dependencies {
    compile("org.pcollections", "pcollections", "3.1.2")
    compile("androidx.collection", "collection", "1.1.0")
    compile("org.javimmutable", "javimmutable-collections", "3.0.2")
    compile("org.eclipse.collections", "eclipse-collections-api", "10.1.0")
    compile("org.eclipse.collections", "eclipse-collections", "10.1.0")
    compile("com.google.guava", "guava-collections", "r03")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}