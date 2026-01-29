package com.dobro.service;

//for graniteBlockPicture [*], ⬛, ⬜, ◻️
import com.dobro.models.*;

public class RenderField {
    private final String ghostPicture = "  \uD83D\uDC7B  ";
    private final String treasureHunterPicture = "\uD83D\uDC64";
    private final String treasurePicture = "\uD83E\uDE99";

    private final String candlePicture = "\uD83D\uDD6F  ";
    private final String rockPicture = "️\uD83E\uDEA8 ";
    private final String vasePicture = "\uD83C\uDFFA ";
    private final String graniteBlockPicture = "◻\uFE0F  ";

    public void displayWorldMap(WorldMap worldMap) {
        for (int indexRow = 0; indexRow < worldMap.getMaxWidthField(); indexRow++) {
            for (int indexColumn = 0; indexColumn < worldMap.getMaxLengthField(); indexColumn++) {
                Entity entity = worldMap.getEntities().get(new Cell(indexRow, indexColumn));
                switch (entity) {
                    case Ghost ghost -> System.out.print(ghostPicture);
                    case TreasureHunter treasureHunter -> System.out.print(treasureHunterPicture);
                    case Treasure treasure -> System.out.print(treasurePicture);
                    case Candle candle -> System.out.print(candlePicture);
                    case Rock rock -> System.out.print(rockPicture);
                    case Vase vase -> System.out.print(vasePicture);
                    case GraniteBlock graniteBlock -> System.out.print(graniteBlockPicture);
                    default -> throw new IllegalArgumentException("Unexpected value");
                }
            }
            System.out.println();
        }
    }
}
