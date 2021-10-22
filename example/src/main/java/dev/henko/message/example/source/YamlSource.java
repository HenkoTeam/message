package dev.henko.message.example.source;

import dev.henko.message.api.source.Source;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class YamlSource
  implements Source {

  private Yaml yaml;
  private File file;

  public YamlSource(String fileName){
    this.yaml = new Yaml();
    try {
      URL resource = getClass().getClassLoader().getResource(fileName + ".yml");
      this.file = new File(resource.toURI());
      if (!file.exists()) {
        file.getParentFile().mkdir();
        file.createNewFile();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public @Nullable String getString(String path) {
    try {
      InputStream inputStream = new FileInputStream(file);
      Map<String, Object> data = yaml.load(inputStream);
      return (String) data.get(path);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // Just for test
  @Override
  public @Nullable List<String> getStringList(String path) {
    return null;
  }
}
