package promise.model.repo
import promise.commons.model.List

/**
 *
 *
 * @param T
 */
interface AsyncIDataStore<T> {
  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun all(res: (List<out T>, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun one(res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun save(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun save(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun update(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun update(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun delete(t: T, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun delete(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun clear(res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
}

/**
 *
 *
 * @param T
 */
open class AbstractAsyncIDataStore<T> : AsyncIDataStore<T> {
  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun all(res: (List<out T>, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(List(), Any())

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun one(res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    err?.invoke(Exception())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun save(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t, Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun save(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun update(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t, Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun update(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun delete(t: T, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun delete(t: List<out T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun clear(res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(Any())
}