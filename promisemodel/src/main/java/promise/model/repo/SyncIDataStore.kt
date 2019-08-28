package promise.model.repo

import promise.commons.model.List

/**
 * abstract implementation of sync data store
 * interacts with data sources on a synchronous manner
 * @param T must implement S so it can have a reference id
 */
interface SyncIDataStore<T> {
  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun all(args: Map<String, Any?>? = null): Pair<List<out T>?, Any?>

  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun one(args: Map<String, Any?>? = null): Pair<T?, Any?>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: T, args: Map<String, Any?>? = null): Pair<T, Any?>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: List<in T>, args: Map<String, Any?>? = null): Any?

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: T, args: Map<String, Any?>? = null): Pair<T, Any?>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: List<in T>, args: Map<String, Any?>? = null): Any?

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun delete(t: T, args: Map<String, Any?>? = null): Any?

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun delete(t: List<in T>, args: Map<String, Any?>? = null): Any?

  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun clear(args: Map<String, Any?>? = null): Any?

}

/**
 *
 *
 * @param T
 */
open class AbstractSyncIDataStore<T>: SyncIDataStore<T> {
  /**
   *
   *
   * @param args
   * @return
   */
  override fun all(args: Map<String, Any?>?): Pair<List<out T>?, Any?> = Pair(List(), Any())

  /**
   *
   *
   * @param args
   * @return
   */
  override fun one(args: Map<String, Any?>?): Pair<T?, Any?> = Pair(null, Any())

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun save(t: T, args: Map<String, Any?>?): Pair<T, Any?> = Pair(t, Any())

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun save(t: List<in T>, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun update(t: T, args: Map<String, Any?>?): Pair<T, Any?> = Pair(t, Any())

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun update(t: List<in T>, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun delete(t: T, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun delete(t: List<in T>, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param args
   * @return
   */
  override fun clear(args: Map<String, Any?>?): Any? = Any()
}