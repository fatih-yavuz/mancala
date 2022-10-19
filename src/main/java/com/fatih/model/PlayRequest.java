package com.fatih.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PlayRequest {

    private String playerId;

    private Integer pitIndex;

}
