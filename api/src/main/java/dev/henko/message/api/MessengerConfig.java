package dev.henko.message.api;

import dev.henko.message.api.entity.EntitySender;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents the configuration that will be used
 * for a new messenger
 *
 * @param <T> The message type
 */
public interface MessengerConfig<T> {

  /**
   * Set the source of the messenger config
   *
   * @param resource Source to set
   * @return The current messenger config
   */
  @NotNull
  MessengerConfig<T> setSource(Source resource);

  /**
   * Add a interceptor for the messages
   *
   * @param interceptor Interceptor to be added
   * @return The current messenger config
   */
  @NotNull
  MessengerConfig<T> addInterceptor(MessageInterceptor<T> interceptor);

  /**
   * Add a entity sender for a specific entity
   *
   * @param entityType The entity type
   * @param sender Entity sender to be added
   * @return The current messenger config
   */
  @NotNull
  <E> MessengerConfig<T> addEntity(Class<E> entityType, EntitySender<E, T> sender);

  /**
   * Get the messenger config source
   *
   * @return The source
   */
  @NotNull
  Source getSource();

  /**
   * Get all interceptors
   *
   * @return The interceptors
   */
  @NotNull
  Collection<MessageInterceptor<T>> getInterceptors();

  /**
   * Get a specific entity sender
   *
   * @param type Entity type to get
   * @return The entity sender
   */
  @Nullable
  EntitySender<?, T> getEntitySender(Class<?> type);

}
