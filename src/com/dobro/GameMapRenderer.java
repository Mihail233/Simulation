package com.dobro;

import com.dobro.entity.*;

import java.util.Optional;

public class GameMapRenderer {
    private final String ghostPicture = "\uD83D\uDC7B ";
    private final String coinHunterPicture = "\uD83D\uDC64 ";
    private final String coinPicture = "\uD83E\uDE99 ";
    private final String candlePicture = "\uD83D\uDD6F  ";
    private final String rockPicture = "️\uD83E\uDEA8 ";
    private final String vasePicture = "\uD83C\uDFFA ";
    private final String graniteBlockPicture = "◻\uFE0F  ";
    private final String blockBorder = "⬛ ";

    public void render(WorldMap worldMap) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        int startRow = worldMap.getOriginWorldMap().x();
        int startColumn = worldMap.getOriginWorldMap().y();

        printHorizontalBorder(worldMap.getHeight());
        for (int indexRow = startRow; indexRow < width; indexRow++) {
            printPartOfVerticalBorder();

            for (int indexColumn = startColumn; indexColumn < height; indexColumn++) {
                Optional<Entity> entity = worldMap.getEntity(new Cell(indexRow, indexColumn));
                if (entity.isPresent()) {
                    String sprite = toSprite(entity.get());
                    System.out.print(sprite);
                } else {
                    System.out.print(graniteBlockPicture);
                }
            }

            printPartOfVerticalBorder();
            System.out.println();
        }
        printHorizontalBorder(worldMap.getHeight());
    }

    private String toSprite(Entity entity) {
        switch (entity) {
            case Ghost ghost -> {
                return ghostPicture;
            }
            case CoinHunter CoinHunter -> {
                return coinHunterPicture;
            }
            case Coin Coin -> {
                return coinPicture;
            }
            case Candle candle -> {
                return candlePicture;
            }
            case Rock rock -> {
                return rockPicture;
            }
            case Vase vase -> {
                return vasePicture;
            }
            default -> throw new IllegalArgumentException("Unexpected value");
        }
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
