package dev.henko.message.api;

import dev.henko.message.api.entity.EntityResolver;
import dev.henko.message.api.error.EntityResolverNotFoundException;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents the configuration that will be used
 * for a new messenger
 *
 * @param <T> The message type
 */
public final class MessengerConfig<T> {

  private Source source;
  private final List<MessageInterceptor<T>> interceptors;
  private final Map<Class<?>, EntityResolver<?, T>> entityResolvers;

  public MessengerConfig() {
    this.interceptors = new LinkedList<>();
    this.entityResolvers = new ConcurrentHashMap<>();
  }

  /**
   * Set the source of the messenger config
   *
   * @param source Source to set
   * @return The current messenger config
   */
  public @NotNull MessengerConfig<T> setSource(Source source) {
    this.source = source;
    return this;
  }

  /**
   * Add a interceptor for the messages
   *
   * @param interceptor Interceptor to be added
   * @return The current messenger config
   */
  public @NotNull MessengerConfig<T> addInterceptor(MessageInterceptor<T> interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  /**
   * Creates a new entity resolver for a specific entity
   *
   * @param entityType The entity type
   * @return The current messenger config
   */
  public @NotNull <E> EntityResolver<E, T> addEntity(Class<E> entityType) {
    EntityResolver<E, T> entityResolver = new EntityResolver<>();
    entityResolvers.put(entityType, entityResolver);
    return entityResolver;
  }

  /**
   * Get the messenger config source
   *
   * @return The source
   */
  public @NotNull Source getSource() {
    return source;
  }


  /**
   * Get all interceptors
   *
   * @return The interceptors
   */
  public @NotNull List<MessageInterceptor<T>> getInterceptors() {
    return interceptors;
  }

  /**
   * Get a specific entity resolver
   *
   * @param type Entity type to get
   * @return The entity resolver
   */@SuppressWarnings("unchecked")
  public @Nullable <E> EntityResolver<E, T> getEntityResolver(Class<E> type) {
    return (EntityResolver<E, T>) entityResolvers.get(type);
  }

  public @NotNull <E> EntityResolver<E, T> getNotNullResolver(Class<E> type) {
    EntityResolver<E, T> resolver = getEntityResolver(type);
    if(resolver == null) {
      throw new EntityResolverNotFoundException(type);
    }
    return resolver;
  }
}
