package com.example.ecommerce.domain;

import java.util.List;

public interface Addresses {

    List<String> getAddresses();
    void setAddresses(List<String> addresses);

    String toStringPersist(List<String> list);
    List<String> toListPersist(String str);
}
