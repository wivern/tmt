package net.anotheria.tmt.utils;

/**
 * @author VKoulakov
 * @since 04.06.14 15:22
 */
public final class StringUtils {
    private StringUtils(){}
    public static boolean notEmpty(String value){
        return value != null && !value.isEmpty();
    }
}
