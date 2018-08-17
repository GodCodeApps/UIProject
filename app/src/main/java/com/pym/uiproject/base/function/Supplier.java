package com.pym.uiproject.base.function;

/**
 * () -> T
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface Supplier<T> {

    T get();
}
