package promise.model.store;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import kotlin.Pair;
import promise.commons.data.log.LogUtil;
import promise.commons.model.Identifiable;
import promise.commons.model.List;
import promise.commons.pref.Preferences;
import promise.commons.util.DoubleConverter;
import promise.model.repo.AbstractSyncIDataStore;

/**
 * Created on 7/17/18 by yoctopus.
 */
public class PreferenceStorage<T extends Identifiable<Integer>> extends AbstractSyncIDataStore<T> {
  public static final int SAVE_MODE_REPLACE = 1;
  public static final String TAG = LogUtil.makeTag(PreferenceStorage.class);
  private Preferences preferences;
  private DoubleConverter<T, JSONObject, JSONObject> converter;

  public PreferenceStorage(String name, DoubleConverter<T, JSONObject, JSONObject> converter) {
    this.preferences = new Preferences(name);
    this.converter = converter;
  }

  @NonNull
  @Override
  @Deprecated
  public Pair<List<? extends T>, Object> all(@Nullable Map<String, ?> args) {
    List<T> list = new List<>();
    for (Map.Entry<String, ?> key: preferences.getAll().entrySet()) {
      String val = (String) key.getValue();
      try {
        list.add(converter.deserialize(new JSONObject(val)));
      } catch (JSONException e) {
       LogUtil.e(TAG, "error de serializing  ", val);
      }
    }
    return new Pair<>(list, "all items");
  }

  @Nullable
  public List<? extends T> all(@Nullable Map<String, ?> args, MapFilter<? super T> mapFilter) throws Exception {
    List<T> list = new List<>();
    for (Map.Entry<String, ?> entry: preferences.getAll().entrySet()) {
      String val = (String) entry.getValue();
      list.add(converter.deserialize(new JSONObject(val)));
    }
    return list.filter(t -> mapFilter.filter(t, args));
  }

  public interface MapFilter<T extends Identifiable<?>> {
    boolean filter(T t, Map<String, ?> args);
  }

}
