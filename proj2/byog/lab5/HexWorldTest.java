package byog.lab5;
import static org.junit.Assert.*;

import byog.Core.RandomUtils;
import org.junit.Test;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
public class HexWorldTest {
    @Test
    public void FindAdjPosTest() {
        HexWorld hw = new HexWorld(60,60, 2);
        hw.addHexagon(new HexWorld.Position(20,20), Tileset.GRASS);
        HexWorld.Position p0 = hw.findAjdPos(new HexWorld.Position(20,20), 0);
        HexWorld.Position p1 = hw.findAjdPos(new HexWorld.Position(20,20), 1);
        HexWorld.Position p2 = hw.findAjdPos(new HexWorld.Position(20,20), 2);
        HexWorld.Position p3 = hw.findAjdPos(new HexWorld.Position(20,20), 3);
        HexWorld.Position p4 = hw.findAjdPos(new HexWorld.Position(20,20), 4);
        HexWorld.Position p5 = hw.findAjdPos(new HexWorld.Position(20,20), 5);

        assertEquals(p0, (new HexWorld.Position(20,24)));
        assertEquals(p1, (new HexWorld.Position(23,22)));
        assertEquals(p2, (new HexWorld.Position(23,18)));
        assertEquals(p3, (new HexWorld.Position(20,16)));
        assertEquals(p4, (new HexWorld.Position(17,18)));
        assertEquals(p5, (new HexWorld.Position(17,22)));
    }

    @Test
    public void tileOccupied() {
        HexWorld hw = new HexWorld(60,60, 2);
        hw.addHexagon(new HexWorld.Position(20,20), Tileset.GRASS);
        HexWorld.Position p0 = new HexWorld.Position(0,0);
        HexWorld.Position p1 = new HexWorld.Position(59,57);
        HexWorld.Position p2 = new HexWorld.Position(0,60);
        HexWorld.Position p3 = new HexWorld.Position(-1,0);
        HexWorld.Position p4 = new HexWorld.Position(0,-2);
        HexWorld.Position p5 = new HexWorld.Position(61,0);
        HexWorld.Position p6 = new HexWorld.Position(0,62);

        assertFalse(hw.tileFitInMap(p0));
        assertFalse(hw.tileFitInMap(p1));
        assertFalse(hw.tileFitInMap(p2));
        assertFalse(hw.tileFitInMap(p3));
        assertFalse(hw.tileFitInMap(p4));
        assertFalse(hw.tileFitInMap(p5));
        assertFalse(hw.tileFitInMap(p6));

        assertTrue(hw.tileFitInMap(new HexWorld.Position(1,0)));
        assertTrue(hw.tileFitInMap(new HexWorld.Position(58,0)));
        assertTrue(hw.tileFitInMap(new HexWorld.Position(1,57)));
        assertTrue(hw.tileFitInMap(new HexWorld.Position(58,57)));
    }


    public static void main(String[] a) {
        HexWorld hw = new HexWorld(60,60, 2);
        Random r = new Random(200);
        hw.addHexagon(new HexWorld.Position(20,20), Tileset.GRASS);
        //hw.addHexagon(new HexWorld.Position(20,20), Tileset.mapTexture[2]);

        assertEquals(Tileset.mapTexture[2], Tileset.nextRanMapTex(r));
        assertEquals(Tileset.mapTexture[5], Tileset.nextRanMapTex(r));
        assertEquals(Tileset.mapTexture[3], Tileset.nextRanMapTex(r));
        assertEquals(Tileset.mapTexture[4], Tileset.nextRanMapTex(r));
        assertEquals(Tileset.mapTexture[5], Tileset.nextRanMapTex(r));

        System.out.println(RandomUtils.uniform(r, 7));
        System.out.println(RandomUtils.uniform(r, 7));
        System.out.println(RandomUtils.uniform(r, 7));
        System.out.println(RandomUtils.uniform(r, 7));
        System.out.println(RandomUtils.uniform(r, 7));

    }
}
