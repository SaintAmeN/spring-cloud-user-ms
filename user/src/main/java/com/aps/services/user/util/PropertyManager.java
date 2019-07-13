package com.aps.services.user.util;

import com.aps.services.model.exception.usageerrors.DuplicateProperty;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

@NoArgsConstructor
public abstract class PropertyManager {

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor descriptor : propertyDescriptors) {
            Object srcValue = beanWrapper.getPropertyValue(descriptor.getName());
            if (srcValue == null) emptyNames.add(descriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyNonNullProperties(Object source, Object target) {
        copyProperties(source, target, getNullPropertyNames(source));
    }

    public static <T, S> void validateIfNotDuplicatedProperty(T dto, JpaRepository<S, Long> repository, String property){
        final BeanWrapper dtoWrapper = new BeanWrapperImpl(dto);
        if (repository.findAll().stream().anyMatch(x -> Objects.equals(dtoWrapper.getPropertyValue(property),
                new BeanWrapperImpl(x).getPropertyValue(property)))){
            throw new DuplicateProperty(property +": "+dtoWrapper.getPropertyValue(property));
        }
    }

    public static <T, S> void validateIfNotDuplicatedProperty(T dto, JpaRepository<S, Long> repository, String property, String referTo){
        final BeanWrapper dtoWrapper = new BeanWrapperImpl(dto);
        if (repository.findAll().stream().anyMatch(x -> Objects.equals(dtoWrapper.getPropertyValue(property),
                new BeanWrapperImpl(x).getPropertyValue(property)))){
            throw new DuplicateProperty(property+": "+dtoWrapper.getPropertyValue(property), referTo);
        }
    }
}
