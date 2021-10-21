package dev.henko.message.api;

import java.util.List;

@FunctionalInterface
public interface MessageInterceptor<T> {

  void intercept(T message);

  default void intercept(List<T> list) {
    list.replaceAll(s -> {
      intercept(list);
      return s;
    });
  }
}
