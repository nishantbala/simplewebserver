package com.example.addition.server.common;

import java.math.BigInteger;

public class Constants {
    public static final String END_STRING = "end";
    public static final String SECRET_KEY = "itsReallySecret";
    public static final String CACHE_SUM = "sum";
    public static final String CACHE_ADDITION = "addition";
    public static final BigInteger MAX_VALUE = new BigInteger("10000000000");
    public static final String HASH_ALGORITHM = "SHA-256";
    public static final String ERROR_MSG_INVALID_INPUT = "Invalid input: ";
    public static final String ERROR_MSG_MAX_VALUE_EXCEEDED = "Max Value Exceeded";
}
