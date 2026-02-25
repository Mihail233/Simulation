package com.dobro.service;

import com.dobro.models.*;

import java.util.Optional;

import static com.dobro.models.CoinHunter.getNumberOfCoinsCollected;

public class RenderField {
    private final String ghostPicture = "\uD83D\uDC7B ";
    private final String coinHunterPicture = "\uD83D\uDC64 ";
    private final String coinPicture = "\uD83E\uDE99 ";
    private final String candlePicture = "\uD83D\uDD6F  ";
    private final String rockPicture = "️\uD83E\uDEA8 ";
    private final String vasePicture = "\uD83C\uDFFA ";
    private final String graniteBlockPicture = "◻\uFE0F  ";
    private final String blockBorder = "⬛ ";

    public void displayWorldMap(WorldMap worldMap) {
        printHorizontalBorder(worldMap.getMaxLengthField());
        for (int indexRow = worldMap.getOriginWorldMap().getY(); indexRow < worldMap.getMaxWidthField(); indexRow++) {
            printPartOfVerticalBorder();
            for (int indexColumn = worldMap.getOriginWorldMap().getX(); indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Optional<? extends Entity> entity = worldMap.getEntity(new Cell(indexRow, indexColumn));
                entity.ifPresentOrElse((presentEntity) -> {
                            switch (presentEntity) {
                                case Ghost ghost -> System.out.print(ghostPicture);
                                case CoinHunter CoinHunter -> System.out.print(coinHunterPicture);
                                case Coin Coin -> System.out.print(coinPicture);
                                case Candle candle -> System.out.print(candlePicture);
                                case Rock rock -> System.out.print(rockPicture);
                                case Vase vase -> System.out.print(vasePicture);
                                default -> throw new IllegalArgumentException("Unexpected value");
                            }
                        },
                        () -> System.out.print(graniteBlockPicture)
                );
            }
            printPartOfVerticalBorder();
            System.out.println();
        }
        printHorizontalBorder(worldMap.getMaxLengthField());
        System.out.printf("Собранные монеты %d\n", getNumberOfCoinsCollected());
    }

    public void printHorizontalBorder(int lengthField) {
        int lengthOfSideBorders = 2;
        String horizontalBorder = blockBorder.repeat(lengthField + lengthOfSideBorders);
        System.out.printf("%s \n", horizontalBorder);
    }

    public void printPartOfVerticalBorder() {
        System.out.printf("%s", blockBorder);
    }
}
