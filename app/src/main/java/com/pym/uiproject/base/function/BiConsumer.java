package com.pym.uiproject.base.function;

/**
 * (T, U) -> Void
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface BiConsumer<T, U> {

    void accept(T t, U u);
}
