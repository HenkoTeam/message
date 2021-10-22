# message
 
_A simple lightweight message library_

## Repository

#### Maven (pom.xml)

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

#### Gradle (build.gradle)
build.gradle
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
```
build.gradle.kts
```kotlin
repositories {
  maven { url = uri("https://jitpack.io") }
}
```

### Dependency

#### Maven (pom.xml)
```xml
<dependency>
    <groupId>com.github.HenkoTeam.message</groupId>
    <artifactId>api</artifactId>
    <version>VERSION</version>
</dependency>
```

#### Gradle
**For Groovy DSL:** (build.gradle)
```groovy
dependencies {
  implementation 'com.github.HenkoTeam.message:api:VERSION'
}
```
**For Kotlin DSL:** (build.gradle.kts)
```kotlin
dependencies {
  implementation("com.github.HenkoTeam.message:api:VERSION")
}
```

#### How to use

Create a custom messenger configuration

```java
// Create a messenger config for message type
MessengerConfig<String> config = new MessengerConfigImpl<>();
// Can create custom sources implementing the interface Source
config.setSource(...)
  .addInterceptor(message -> colorize(message))
  .addEntity(Entity.class, (entity, message) -> entity.sendMessage(message));
  
// The same message type for the messenger
Messenger<String> messenger = new DefaultMessenger(config) // Also can create a custom messengers;
```

Send a message to entity with placeholders

```java
Messenger<...> messenger = ...
messenger.send(entity, "messages.welcome", "%example%", "I'm a example");
messenger.sendMany(entity, "messages.welcome-list", "%example2%", "Other example WOW!");
```

Get a message with placeholders

```java
Messenger<...> messenger = ...
Object o = messenger.get("messages.welcome", "%example%", "I'm a example");
List<Object> list = messenger.getMany("messages.welcome");
```

**Bukkit**

Create a new default bukkit messeger

```java
Messenger<String> messenger = new BukkitMessengerProvider(plugin, "lang").get();
```
