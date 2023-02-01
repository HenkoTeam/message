package dev.henko.message.api.config;

import com.google.common.reflect.TypeToken;
import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.format.MessageInterceptor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Represents the configuration that will be used
 * for a new messenger
 *
 */
public final class ConfigurationHandle {

  private final List<MessageInterceptor> interceptors;
  private final Map<TypeToken<?>, EntityResolver<?>> entityResolvers;

  private final YamlConfiguration messageSource;

  ConfigurationHandle(
      List<MessageInterceptor> interceptors,
      Map<TypeToken<?>, EntityResolver<?>> entityResolvers,
      YamlConfiguration messageSource
  ) {
    this.interceptors = interceptors;
    this.entityResolvers = entityResolvers;
    this.messageSource = messageSource;
  }

  public static ConfigurationBuilder builder() {
    return new ConfigurationBuilder();
  }

  /**
   * Get the message source
   *
   * @return The source
   */
  public @NotNull YamlConfiguration getSource() {
    return messageSource;
  }

  /**
   * Get all interceptors
   *
   * @return The interceptors
   */
  public @NotNull List<MessageInterceptor> getInterceptors() {
    return interceptors;
  }

  /**
   * Get entity resolver for an entity
   *
   * @param type Entity type to get
   * @return The entity resolver
   */
  @SuppressWarnings("unchecked")
  public @Nullable <E> EntityResolver<E> getEntityResolver(Class<E> type) {
    return (EntityResolver<E>) entityResolvers.get(TypeToken.of(type));
  }
}
