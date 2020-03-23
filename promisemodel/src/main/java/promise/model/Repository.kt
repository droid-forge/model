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
 */
interface Repository<T> {
  /**
   *
   */
  @Throws(Exception::class)
  fun findAll(
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((List<out T>?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): List<out T>?

  /**
   *
   */
  @Throws(Exception::class)
  fun findOne(
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((T?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): T?

  /**
   *
   */
  @Throws(Exception::class)
  fun save(
      /**
       *
       */
      t: T,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((T) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): T

  /**
   *
   */
  @Throws(Exception::class)
  fun save(
      /**
       *
       */
      t: List<out T>,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((Any?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): Any?

  /**
   *
   */
  @Throws(Exception::class)
  fun update(
      /**
       *
       */
      t: T,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((T) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): T

  /**
   *
   */
  @Throws(Exception::class)
  fun update(
      /**
       *
       */
      t: List<out T>,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((Any?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): Any?

  /**
   *
   */
  @Throws(Exception::class)
  fun delete(
      /**
       *
       */
      t: T,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((Any?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): Any?

  /**
   *
   */
  @Throws(Exception::class)
  fun delete(
      /**
       *
       */
      t: List<out T>,
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((Any?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): Any?

  /**
   *
   */
  @Throws(Exception::class)
  fun clear(
      /**
       *
       */
      args: MutableMap<String, Any?>?,
      /**
       *
       */
      res: ((Any?) -> Unit)? = null,
      /**
       *
       */
      err: ((Exception) -> Unit)? = null): Any?

  /**
   *
   *
   * @param args
   * @return
   */
  fun checkCallbacksNotNull(
      /**
       *
       */
      vararg args: Any?): Boolean {
    for (arg in args) if (arg != null) return true
    return false
  }

}