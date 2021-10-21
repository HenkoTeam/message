package dev.henko.message.api.impl;

import dev.henko.message.api.MessageInterceptor;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.entity.EntitySender;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessengerConfigImpl<T>
  implements MessengerConfig<T> {

  private Source source;
  private final List<MessageInterceptor<T>> interceptors;
  private final Map<Class<?>, EntitySender<?,T>> entitySenders;

  public MessengerConfigImpl() {
    this.interceptors = new LinkedList<>();
    this.entitySenders = new ConcurrentHashMap<>();
  }

  @Override
  public @NotNull MessengerConfig<T> setSource(Source source) {
    this.source = source;
    return this;
  }

  @Override
  public @NotNull MessengerConfig<T> addInterceptor(MessageInterceptor<T> interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  @Override
  public @NotNull <E> MessengerConfig<T> addEntity(
    Class<E> entityType,
    EntitySender<E, T> sender
  ) {
    entitySenders.put(entityType, sender);
    return this;
  }


  @Override
  public @NotNull Source getSource() {
    return source;
  }

  @Override
  public @NotNull List<MessageInterceptor<T>> getInterceptors() {
    return interceptors;
  }

  @Override
  public @Nullable EntitySender<?,T> getEntitySender(Class<?> type) {
    return entitySenders.get(type);
  }

}
