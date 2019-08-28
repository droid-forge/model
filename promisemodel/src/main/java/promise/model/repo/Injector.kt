package promise.model.repo

/**
 * The interface provides an instance through the {@link Injector#inject(}
 *
 * @param T the supertype of what is to be injected
 */
interface Injector<out T> {
  /**
   * returns any that extends from T
   *
   * @param E return type can be T
   * @return T or its child
   */
  fun inject(): T
}