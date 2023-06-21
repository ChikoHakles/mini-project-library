package id.co.indivara.library.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
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

    public static String generateCode(TransactionType type, LocalDateTime localDateTime) {
        return String.format(
                "%c%02d%02d%02d%s",
                type.toString().charAt(0),
                localDateTime.getDayOfMonth(),
                localDateTime.getMonth().getValue(),
                localDateTime.getYear(),
                randomChars(3)
                );
    }

    public static String randomChars(int loops) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < loops; i++) {
            stringBuilder.append(random.nextInt(26) + 65);
        }
        return stringBuilder.toString();
    }
}
