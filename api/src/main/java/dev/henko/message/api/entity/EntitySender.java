package dev.henko.message.api.entity;

@FunctionalInterface
public interface EntitySender<E, T> {
  void send(E entity, T message);
}
