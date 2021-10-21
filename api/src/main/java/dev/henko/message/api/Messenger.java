package dev.henko.message.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Messenger<T> {

  <E> void send(E entity, String path, Object... replacements);

  <E> void sendMany(E entity, String path, Object... replacements);

  @NotNull
  T get(String path, Object... replacements);

  @NotNull
  List<T> getMany(String path, Object... replacements);

  @NotNull
  MessengerConfig<T> getConfig();

}
