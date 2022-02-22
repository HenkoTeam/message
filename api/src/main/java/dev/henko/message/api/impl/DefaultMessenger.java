package dev.henko.message.api.impl;

import dev.henko.message.api.AbstractMessenger;
import dev.henko.message.api.MessageInterceptor;
import dev.henko.message.api.MessengerConfig;
import dev.henko.message.api.replacer.Replacer;
import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DefaultMessenger
  extends AbstractMessenger<String> {

  public DefaultMessenger(MessengerConfig<String> config) {
    super(new DefaultEntityReplacer(config), new DefaultMessageReplacer(), config);
  }

  @Override
  protected @NotNull String internalGet(
    String path,
    Replacer<String> replacer,
    Object... objects
  ) {
    Source source = config.getSource();
    String result = source.getString(path);
    if (result == null) {
      return path;
    }
    String finalResult = replacer.replace(result, objects);
    for (MessageInterceptor<String> interceptor : config.getInterceptors()) {
      finalResult = interceptor.intercept(finalResult);
    }
    return finalResult;
  }

  @Override
  protected @NotNull List<String> internalGetMany(
    String path,
    Replacer<String> replacer,
    Object... objects
  ) {
    Source source = config.getSource();
    List<String> result = source.getStringList(path);
    if(result == null || result.isEmpty()) {
      return Collections.singletonList(path);
    }
    config.getInterceptors()
      .forEach(interceptor -> interceptor.intercept(result));
    replacer.replace(result, objects);
    return result;
  }


}
