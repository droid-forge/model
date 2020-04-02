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
import promise.commons.tx.Either
import promise.commons.tx.Left
import promise.commons.tx.Right

interface EitherIDataStore<T: Any> {
  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun findAll(args: Map<String, Any?>? = null): Either<List<out T>>

  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun findOne(args: Map<String, Any?>? = null): Either<T>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: T, args: Map<String, Any?>? = null): Either<T>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun save(t: List<out T>, args: Map<String, Any?>? = null): Either<Any>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: T, args: Map<String, Any?>? = null): Either<T>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun update(t: List<out T>, args: Map<String, Any?>? = null): Either<Any>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun delete(t: T, args: Map<String, Any?>? = null): Either<Any>

  /**
   *
   *
   * @param t
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun delete(t: List<out T>, args: Map<String, Any?>? = null): Either<Any>

  /**
   *
   *
   * @param args
   * @return
   */
  @Throws(Exception::class)
  fun clear(args: Map<String, Any?>? = null): Either<Any>
}

open class AbstractEitherIDataStore<T: Any>: EitherIDataStore<T> {
  override fun findAll(args: Map<String, Any?>?): Either<List<out T>> = Right(List())

  override fun findOne(args: Map<String, Any?>?): Either<T> = Right(null)


  override fun save(t: T, args: Map<String, Any?>?): Either<T> = Right(t)

  override fun save(t: List<out T>, args: Map<String, Any?>?): Either<Any> =
      Left(Exception("please override"))

  override fun update(t: T, args: Map<String, Any?>?): Either<T> = Right(t)

  override fun update(t: List<out T>, args: Map<String, Any?>?): Either<Any> =
      Left(Exception("please override"))

  override fun delete(t: T, args: Map<String, Any?>?): Either<Any> =
      Left(Exception("please override"))

  override fun delete(t: List<out T>, args: Map<String, Any?>?): Either<Any> {
    return Left(Exception("please override"))
  }

  override fun clear(args: Map<String, Any?>?): Either<Any> = Left(Exception("please override"))
}