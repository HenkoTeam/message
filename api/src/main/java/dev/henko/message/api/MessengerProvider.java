package dev.henko.message.api;

public interface MessengerProvider<T> {
  Messenger<T> get();
}
