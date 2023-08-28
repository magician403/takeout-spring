package com.example.takeout.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sex {
    public static final Sex MALE = new Sex((byte) 0, "male");
    public static final Sex FEMALE = new Sex((byte) 1, "female");
    private final Byte code;
    private final String description;
}