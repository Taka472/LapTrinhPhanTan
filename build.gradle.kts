plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val junitVersion = "5.9.2"
    val lombokVersion = "1.18.36"

    // Hibernate Core for ORM
    implementation("org.hibernate:hibernate-core:6.2.5.Final")

    // Jakarta Persistence API
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // JAXB for XML processing
    implementation("org.glassfish.jaxb:jaxb-runtime:3.0.2")

    // Database drivers
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.1")
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.3.0.jre17-preview")

    // Lombok for boilerplate code
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Datafaker for test data generation
    implementation("net.datafaker:datafaker:2.4.2")

    // Jakarta JSON APIs
    implementation("jakarta.json:jakarta.json-api:2.1.3")
    implementation("jakarta.json.bind:jakarta.json.bind-api:3.0.1")
    implementation("org.eclipse.parsson:parsson:1.1.7")
    implementation("org.eclipse:yasson:3.0.4")

    // JUnit for testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}
