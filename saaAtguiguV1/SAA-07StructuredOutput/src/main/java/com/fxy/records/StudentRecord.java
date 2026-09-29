package com.fxy.records;

/**
 * StudentRecord
 * <p>
 * jdk14以后的新特性，记录类record= equals + hashCode + toString +entity + lombok
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-29 22:45
 */
public record StudentRecord(String id, String name, String major, String email) {

    

}
