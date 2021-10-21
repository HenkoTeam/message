package dev.henko.message.api.impl;

import dev.henko.message.api.AbstractMessenger;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DefaultMessenger
  extends AbstractMessenger<String> {

  public DefaultMessenger(MessengerConfig<String> config) {
    super(new DefaultMessageReplacer(), config);
  }

  @Override
  public @NotNull String get(String path, Object... replacements) {
    Source source = config.getSource();
    String result = source.getString(path);
    if(result == null) {
      return path;
    }
    String finalResult = replacer.replace(result, replacements);
    config.getInterceptors()
      .forEach(interceptor -> interceptor.intercept(finalResult));
    return finalResult;
  }

  @Override
  public @NotNull List<String> getMany(String path, Object... replacements) {
    Source source = config.getSource();
    List<String> result = source.getStringList(path);
    if(result == null || result.isEmpty()) {
      return Collections.singletonList(path);
    }
    config.getInterceptors()
      .forEach(interceptor -> interceptor.intercept(result));
    replacer.replace(result, replacements);
    return result;
  }

}
