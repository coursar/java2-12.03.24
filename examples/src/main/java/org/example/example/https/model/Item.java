package org.example.example.https.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private long id;
    private String value;
    private String owner;
    private Instant created;
}
