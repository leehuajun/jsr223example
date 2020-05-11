package com.example.jsr223example;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionObject {
    private String url;
    private String username;
    private String password;
    private String driver;
}
