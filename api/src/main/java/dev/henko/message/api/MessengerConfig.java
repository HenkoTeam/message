package dev.henko.message.api;

import dev.henko.message.api.entity.EntitySender;
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
  private final Map<Class<?>, EntitySender<?,T>> entitySenders;

  public MessengerConfig() {
    this.interceptors = new LinkedList<>();
    this.entitySenders = new ConcurrentHashMap<>();
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
   * Add a entity sender for a specific entity
   *
   * @param entityType The entity type
   * @param sender Entity sender to be added
   * @return The current messenger config
   */
  public @NotNull <E> MessengerConfig<T> addEntity(
    Class<E> entityType,
    EntitySender<E, T> sender
  ) {
    entitySenders.put(entityType, sender);
    return this;
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
   * Get a specific entity sender
   *
   * @param type Entity type to get
   * @return The entity sender
   */
  public @Nullable EntitySender<?,T> getEntitySender(Class<?> type) {
    return entitySenders.get(type);
  }

}
