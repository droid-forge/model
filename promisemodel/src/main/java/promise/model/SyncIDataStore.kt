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
  fun findAll(args: Map<String, Any?>? = null): List<out T>?

  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun findOne(args: Map<String, Any?>? = null): T?

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: T, args: Map<String, Any?>? = null): T

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: List<out T>, args: Map<String, Any?>? = null): Any?

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: T, args: Map<String, Any?>? = null): T

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: List<out T>, args: Map<String, Any?>? = null): Any?

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
  fun delete(t: List<out T>, args: Map<String, Any?>? = null): Any?

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
  override fun findAll(args: Map<String, Any?>?): List<out T>? = List()

  /**
   *
   *
   * @param args
   * @return
   */
  override fun findOne(args: Map<String, Any?>?): T? = null

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun save(t: T, args: Map<String, Any?>?): T = t

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun save(t: List<out T>, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun update(t: T, args: Map<String, Any?>?): T = t

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  override fun update(t: List<out T>, args: Map<String, Any?>?): Any? = Any()

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
  override fun delete(t: List<out T>, args: Map<String, Any?>?): Any? = Any()

  /**
   *
   *
   * @param args
   * @return
   */
  override fun clear(args: Map<String, Any?>?): Any? = Any()
}