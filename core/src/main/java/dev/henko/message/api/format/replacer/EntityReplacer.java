package dev.henko.message.api.format.replacer;

import dev.henko.message.api.config.ConfigurationHandle;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.entity.placeholder.EntityPlaceholder;
import dev.henko.message.api.entity.placeholder.PlaceholderExtractor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;

public class EntityReplacer implements Replacer {

  private final ConfigurationHandle config;

  public EntityReplacer(
      ConfigurationHandle config
  ) {
    this.config = config;
  }

  @Override
  public @NotNull String replace(String message, Object... entities) {
    for(Object entity : entities) {
      Class<?> entityType = entity.getClass();
      EntityResolver<?> resolver = config
          .getEntityResolver(entityType);

      if(resolver == null) {
        continue;
      }

      EntityPlaceholder<?> placeholderHolder = resolver
          .getPlaceholder();

      if(placeholderHolder == null) {
        continue;
      }

      Matcher matcher = placeholderHolder
          .getPattern()
          .matcher(message);

      while(matcher.find()) {
        String group = matcher.group();
        String placeholder = PlaceholderExtractor
            .from(group, placeholderHolder.getPrefix());

        Object o = placeholderHolder.replace(entity, placeholder);

        if(o != null) {
          message = message.replace(group, o.toString());
        }
      }
    }
    return message;
  }

  @Override
  public @NotNull List<String> replace(List<String> message, Object... entities) {
    message.replaceAll(s -> replace(s, entities));
    return message;
  }
}
