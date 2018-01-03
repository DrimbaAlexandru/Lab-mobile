package com.awesomeproject.Domain;

import com.google.gson.annotations.SerializedName;

public enum Difficulty
{
    @SerializedName("0")
    BEGINNER,
    @SerializedName("1")
    INTERMEDIATE,
    @SerializedName("2")
    ADVANCED
}
