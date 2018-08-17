package com.pym.uiproject.base.function;

/**
 * (T, T) -> T
 *
 * @author Peng YanMing 2017/8/10
 */
@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T, T, T> {

}
