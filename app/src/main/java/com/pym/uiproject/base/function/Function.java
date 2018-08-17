package com.pym.uiproject.base.function;

/**
 * T -> R
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface Function<T, R> {

    R apply(T var1);
}
