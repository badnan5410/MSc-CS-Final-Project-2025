package main;

import java.awt.*;

/**
 * Trigger rectangle used for map events.
 * Its {@code x/y} act as local offsets inside a tile (pixels from the tile's top-left).
 * {@code EventHandler} temporarily shifts this rect into world space to test hits, then restores the local offsets stored below.
 */
public class EventRect extends Rectangle {

    // default local X/Y offset (pixels within the tile) to restore after hit tests.
    int eventRectDefaultX, eventRectDefaultY;

    // marks this event as already handled (for one-shot triggers).
    boolean eventDone = false;

}
