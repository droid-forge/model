package promise.model;


import androidx.annotation.Nullable;

import promise.commons.model.List;

/**
 *
 * @param <T>
 */
public interface Extras<T> {
    /**
     *
     * @return
     */
    @Nullable
    T first();

    /**
     *
     * @return
     */
    @Nullable T last();

    /**
     *
     * @return
     */
    List<? extends T> all();

    /**
     *
     * @param limit
     * @return
     */
    List<? extends T> limit(int limit);

    /**
     *
     * @param x
     * @param <X>
     * @return
     */
    <X> List<? extends T> where(X... x);
}
