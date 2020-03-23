/*
 *  Copyright 2017, Peter Vincent
 *  Licensed under the Apache License, Version 2.0, Android Promise.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.model
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
  fun findAll(res: (List<out T>?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun findOne(res: (T?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun save(t: T, res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

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
  fun update(t: T, res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

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
  override fun findAll(res: (List<out T>?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(List())

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun findOne(res: (T?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
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
  override fun save(t: T, res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t)

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
  override fun update(t: T, res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t)

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