package com.example.alex.emptyapp.Domain;

import com.google.gson.annotations.SerializedName;

public enum Role
{
    @SerializedName("0")
    ADMIN,
    @SerializedName("1")
    TRAINER,
    @SerializedName("2")
    USER
}

