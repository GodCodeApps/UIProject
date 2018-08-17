package com.pym.uiproject.base.function;

/**
 * (T, U) -> boolean
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface BiPredicate<T, U> {

    boolean test(T t, U u);
}
