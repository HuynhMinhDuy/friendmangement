package com.example.friendmangerment.model.request;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DoubleEmailRequest {
    @NotNull
    List<String> friends;
}
