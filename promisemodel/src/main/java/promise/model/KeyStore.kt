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
import promise.commons.tx.PromiseResult

/**
 * @param <T>
 * @param <K>
 * @param <X>
</X></K></T> */
interface KeyStore<T: Any, K, X : Throwable?> {
  /**
   * @param k
   * @param callBack
   */
  operator fun get(k: K, callBack: PromiseResult<Extras<T>, X>,  type: Class<T>? = null)

  /**
   *
   */
  operator fun get(k: K, type: Class<T>? = null): Extras<T>

  fun delete(k: K, t: T, callBack: PromiseResult<Boolean, X>)
  /**
   * @param k
   * @param t
   */
  fun delete(k: K, t: T): Boolean

  fun update(k: K, t: T, callBack: PromiseResult<Boolean, X>)
  /**
   * @param k
   * @param t
   */
  fun update(k: K, t: T): Boolean

  fun save(k: K, t: T, callBack: PromiseResult<Boolean, X>)
  /**
   * @param k
   * @param t
   */
  fun save(k: K, t: T): Boolean

  fun save(k: K, t:  List<out T>, callBack: PromiseResult<Boolean, X>)

  fun save(k: K, t: List<out T>): Boolean

  fun clear(k: K, callBack: PromiseResult<Boolean, X>)
  /**
   * @param k
   */
  fun clear(k: K): Boolean?

  fun clear(callBack: PromiseResult<Boolean, X>)

  fun clear(): Boolean?

  fun <E : Throwable?> getExtras(list: List<out T?>, callBack: PromiseResult<Extras<T>?, E>) {
    callBack.response(getExtras(list))
  }

  fun getExtras(list: List<out T?>): Extras<T> = object : Extras<T> {
    override fun first(): T? = list.first()
    override fun last(): T? = list.last()
    override fun all(): List<out T?> = list
    override fun limit(limit: Int): List<out T?>? = list.take(limit)
    @SafeVarargs
    override fun <X> where(vararg x: X): List<out T?> = filter(list, *x)
  }

  fun <Y> filter(list: List<out T?>, vararg y: Y): List<out T?>

  interface Extras<T> {
    /**
     * @return
     */
    fun first(): T?

    /**
     * @return
     */
    fun last(): T?

    /**
     * @return
     */
    fun all(): List<out T?>

    /**
     * @param limit
     * @return
     */
    fun limit(limit: Int): List<out T?>?

    /**
     * @param x
     * @param <X>
     * @return
    </X> */
    fun <X> where(vararg x: X): List<out T?>
  }
}