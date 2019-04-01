package ru.otus.services.message;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Address {
    private static final AtomicInteger globalId = new AtomicInteger(0);
    private final String id;

    public Address() {
        id = String.valueOf(globalId.incrementAndGet());
    }

    public Address(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
