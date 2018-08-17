package com.pym.uiproject.base.function;

/**
 * (T, U) -> R
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface BiFunction<T, U, R> {

    R apply(T t, U u);
}
