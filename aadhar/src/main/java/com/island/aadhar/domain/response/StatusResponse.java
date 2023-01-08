package com.island.aadhar.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    private int statusCode;
    private StatusType statusType;
    private String statusMessage;
}
