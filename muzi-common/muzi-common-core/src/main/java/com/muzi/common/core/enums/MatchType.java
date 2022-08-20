package com.muzi.common.core.enums;

public enum MatchType {
    EQ,        // filed = value
    NE,         //filed != value
    //下面四个用于Number类型的比较
    GT,   // filed > value
    GTE,   // field >= value
    LT,              // field < value
    LTE,      // field <= value
    IN,      // field <= value
    NOT_IN,
    LIKE,   // field like value
    NOT_LIKE,    // field not like value
    BETWEEN,     // value0 <= field <= value1
}
