package com.example.friendmangerment.model.response;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class ListRetrieveFriend {
    private boolean success;
    private List<String> friends;
    private int count;
}
