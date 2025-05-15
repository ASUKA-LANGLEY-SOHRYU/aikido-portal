package com.prosvirnin.trainersportal.core.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

public interface Mapper<U, T> {
    T map(U source);

    default Collection<T> map(Collection<U> source)
    {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
