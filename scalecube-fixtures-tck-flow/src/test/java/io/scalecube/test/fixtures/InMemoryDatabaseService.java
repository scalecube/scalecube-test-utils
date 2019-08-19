package io.scalecube.test.fixtures;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InMemoryDatabaseService implements DatasourceService {
  Map<String, String> data = new HashMap<>();

  @Override
  public String get(String key) {
    return data.get(key);
  }

  @Override
  public void delete(String key) {
    data.remove(key);
  }

  @Override
  public void put(String key, String value) {
    data.put(key, value);
  }

  @Override
  public Collection<String> keys() {
    return new HashSet<>(data.keySet());
  }
}
