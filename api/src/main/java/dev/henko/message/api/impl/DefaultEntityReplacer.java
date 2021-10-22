package dev.henko.message.api.impl;

import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.entity.placeholder.EntityPlaceholderHolder;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.entity.placeholder.StringPlaceholderObtainer;
import dev.henko.message.api.replacer.EntityReplacer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class DefaultEntityReplacer
  implements EntityReplacer<String> {

  private final MessengerConfig<String> config;

  public DefaultEntityReplacer(MessengerConfig<String> config) {
    this.config = config;
  }

  @Override
  public @NotNull String replace(String message, Object... entities) {
    for(Object entity : entities) {
      Class<?> entityType = entity.getClass();
      EntityResolver<?, String> resolver = config
        .getEntityResolver(entityType);
      if(resolver == null) {
        continue;
      }

      EntityPlaceholderHolder<?> placeholderHolder = resolver
        .getPlaceholderHolder();
      if(placeholderHolder == null) {
        continue;
      }

      Matcher matcher = placeholderHolder
        .getPattern()
        .matcher(message);

      while(matcher.find()) {
        String placeholder = StringPlaceholderObtainer
          .from(matcher.group(), placeholderHolder.getPrefix());

        Object o = placeholderHolder.replace(entity, placeholder);

        if(o != null) {
          message = matcher.replaceAll(o.toString());
        }
      }
    }
    return message;
  }
}
