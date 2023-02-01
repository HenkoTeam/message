package dev.henko.message.api.config;

import com.google.common.reflect.TypeToken;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.format.MessageInterceptor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationBuilder {

  private YamlConfiguration messageSource;

  private final List<MessageInterceptor> interceptors;
  private final Map<TypeToken<?>, EntityResolver<?>> entityResolvers;

  ConfigurationBuilder() {
    this.interceptors = new LinkedList<>();
    this.entityResolvers =new ConcurrentHashMap<>();
  }

  public ConfigurationBuilder source(YamlConfiguration messageSource) {
    this.messageSource = messageSource;
    return this;
  }

  public ConfigurationBuilder intercept(MessageInterceptor interceptor) {
    this.interceptors.add(interceptor);
    return this;
  }

  public <E> ConfigurationBuilder entity(
      EntityResolver<E> resolver
  ) {
    this.entityResolvers.put(TypeToken.of(resolver.getType()), resolver);
    return this;
  }

  public ConfigurationHandle build() {
    return new ConfigurationHandle(interceptors, entityResolvers, messageSource);
  }
}
