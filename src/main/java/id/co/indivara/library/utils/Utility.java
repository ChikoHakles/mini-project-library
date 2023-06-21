package id.co.indivara.library.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class Utility {
    public static void copyNonNullField(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertiesName(source));
    }

    public static String[] getNullPropertiesName(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        String idField = source.getClass().getSimpleName().toLowerCase() + "Id";
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null || src.getPropertyDescriptor(propertyName).getName().equals(idField))
                .toArray(String[]::new);
    }
}
