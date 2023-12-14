package org.acme;

import io.smallrye.mutiny.Multi;

public class MutinyTester {

    public static void main(String[] args) {

        Multi.createFrom().items(1, 2, 3, 4, 5)
                .onItem().transform(i -> i * 2)
                .select().first(3)
                .onFailure().recoverWithItem(0)
                .subscribe().with(System.out::println);

    }
}
