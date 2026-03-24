package com.dobro;

import java.util.Objects;

public record Cell(int x, int y)  {

    @Override
    public String toString() {
        return "x = " + this.x() +
                ", y = " + this.y();
    }
}
