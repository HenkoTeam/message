package dev.henko.message.api;

import dev.henko.message.api.entity.EntitySender;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface MessengerConfig<T> {

  @NotNull
  MessengerConfig<T> setSource(Source resource);

  @NotNull
  MessengerConfig<T> addInterceptor(MessageInterceptor<T> interceptor);

  @NotNull
  <E> MessengerConfig<T> addEntity(Class<E> entityType, EntitySender<E, T> sender);

  @NotNull
  Source getSource();

  @NotNull
  Collection<MessageInterceptor<T>> getInterceptors();

  @Nullable
  EntitySender<?, T> getEntitySender(Class<?> type);

}
