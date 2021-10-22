package dev.henko.message.example;

import dev.henko.message.api.Messenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.impl.DefaultMessenger;
import dev.henko.message.example.source.YamlSource;
import dev.henko.message.example.user.User;

public class Main {

  public static void main(String... args) {
    MessengerConfig<String> config = new MessengerConfig<>();
    config.setSource(new YamlSource("example"));
    config.addEntity(User.class)
      .setSender(((entity, message) -> System.out.println("[message] " + message)))
      .setPlaceholderProvider("user", (entity, placeholder) -> {
        switch (placeholder) {
          case "name":
            return entity.getName();
          case "password":
            return entity.getPassword();
        }
        return null;
      });

    User user = new User("Juan", "12356");
    Messenger<String> messenger = new DefaultMessenger(config);
    // Sends a message using entity placeholders
    messenger.send(user, "example-entity", user);
    // Sends a message using custom placeholders
    messenger.sendReplacing(user, "example-replacing",
      "%name%", user.getName(),
      "%password%", user.getPassword());

  }
}
