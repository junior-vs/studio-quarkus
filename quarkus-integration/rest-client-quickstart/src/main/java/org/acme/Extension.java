package org.acme;

import java.util.List;

public record Extension(
    String id, 
    String name, 
    String shortName, 
    List<String> keywords
) {

}
