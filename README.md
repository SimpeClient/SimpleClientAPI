# SimpleClientAPI

SimpleClientAPI is the Bukkit/Spigot/Paper API for SimpleClient to enable/disable Legacy PvP.

### Download
[here](https://github.com/SimpleClientDevelopment/SimpleClientAPI/releases/latest)

### Gradle
```gradle
repositories {
    maven { url = 'https://maven.pkg.github.com/SimpleClientDevelopment/SimpleClientAPI' }
}
```
```gradle
dependencies {
    modImplementation "simpleclient:simpleclient-api:VERSION"
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>simpleclient-api</id>
        <url>https://maven.pkg.github.com/SimpleClientDevelopment/SimpleClientAPI</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>simpleclient</groupId>
    <artifactId>simpleclient-api</artifactId>
    <version>VERSION</version>
</dependency>
```