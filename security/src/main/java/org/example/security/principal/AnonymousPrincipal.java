package org.example.security.principal;

import java.security.Principal;

public class AnonymousPrincipal implements Principal {

    public static final String ANONYMOUS = "Anonymous";

    @Override
    public String getName() {
        return ANONYMOUS;
    }
}
