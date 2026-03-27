package com.dobro;

public record Cell(int x, int y)  {

    @Override
    public String toString() {
        return "x = " + this.x() +
                ", y = " + this.y();
    }
}
