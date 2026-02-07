import java.io.IOException;

import student.TestCase;

/**
 * @author CS3114/5040 Staff
 * @version December 2025
 */
public class SongsTest extends TestCase {
    private Songs it;


    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing to do
    }


    // ----------------------------------------------------------
    /**
     * Test various bad inputs
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        it = new SongsDB();
        assertFalse(it.clear()); // Not been initialized yet
        assertFuzzyEquals(
            "Initial hash table size must be positive",
            it.create(-1, 32));
        assertFuzzyEquals(
            "Initial memory manager size must be positive",
            it.create(10, 0));
        assertFuzzyEquals(
            "Initial memory manager size must be a power of 2",
            it.create(10, 3));

        assertFuzzyEquals(
            "Database not initialized",
            it.insert("a", "b"));
        assertFuzzyEquals(
            "Database not initialized",
            it.remove("song", "a"));
        assertFuzzyEquals(
            "Database not initialized",
            it.print("blocks"));

        it.create(32, 32);
        assertFuzzyEquals(
            "Bad print parameter",
            it.print("dum"));
        assertFuzzyEquals(
            "Bad type value |Dum| on remove",
            it.remove("Dum", "Dum"));

        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.print(""));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.print(null));

        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.insert("", "b"));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.insert(null, "b"));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.insert("a", ""));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.insert("a", null));

        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.remove("song", ""));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.remove("song", null));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.remove("", "a"));
        assertFuzzyEquals(
            "Input strings cannot be null or empty",
            it.remove(null, "a"));
    }


    // ----------------------------------------------------------
    /**
     * Test various uses of empty or missing data
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        assertFuzzyEquals(
            "total artists: 0",
            it.print("artist"));
        assertFuzzyEquals(
            "total songs: 0",
            it.print("song"));
        it.insert("Hello World", "Hello World2");
        assertFuzzyEquals(
            "No free blocks are available.",
            it.print("blocks"));
        assertFuzzyEquals(
            "|Dum| does not exist in the Artist database",
            it.remove("artist", "Dum"));
        assertFuzzyEquals(
            "|Dum| does not exist in the song database",
            it.remove("song", "Dum"));
    }


    // ----------------------------------------------------------
    /**
     * Show output formats
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        assertFuzzyEquals(
            "|When Summer's Through| does not exist in the Song database",
            it.remove("song", "When Summer's Through"));
        assertFuzzyEquals(
            "|Blind Lemon Jefferson| is added to the Artist database\r\n"
            + "Memory pool expanded to be 64 bytes\r\n"
            + "|Long Lonesome Blues| is added to the Song database",
                it.insert("Blind Lemon Jefferson", "Long Lonesome Blues"));
        assertFuzzyEquals(
            "Memory pool expanded to be 128 bytes\r\n"
            + "|Ma Rainey| is added to the Artist database\r\n"
            + "|Ma Rainey's Black Bottom| is added to the Song database",
                it.insert("Ma Rainey", "Ma Rainey's Black Bottom"));
        assertFuzzyEquals(
            "|Charley Patton| is added to the Artist database\r\n"
            + "Memory pool expanded to be 256 bytes\r\n"
            + "|Mississippi Boweavil Blues| is added to the Song database",
                it.insert("Charley Patton", "Mississippi Boweavil Blues"));
        assertFuzzyEquals(
            "|Sleepy John Estes| is added to the Artist database\r\n"
            + "|Street Car Blues| is added to the Song database",
                it.insert("Sleepy John Estes", "Street Car Blues"));
        assertFuzzyEquals(
            "|Bukka White| is added to the Artist database\r\n"
            + "|Fixin' To Die Blues| is added to the Song database",
                it.insert("Bukka White", "Fixin' To Die Blues"));
        assertFuzzyEquals(
            "0: |Blind Lemon Jefferson|\r\n"
            + "1: |Sleepy John Estes|\r\n"
            + "4: |Charley Patton|\r\n"
            + "5: |Bukka White|\r\n"
            + "7: |Ma Rainey|\r\n"
            + "total artists: 5",
            it.print("artist"));
        assertFuzzyEquals(
            "1: |Fixin' To Die Blues|\r\n"
            + "2: |Mississippi Boweavil Blues|\r\n"
            + "5: |Long Lonesome Blues|\r\n"
            + "6: |Ma Rainey's Black Bottom|\r\n"
            + "9: |Street Car Blues|\r\n"
            + "total songs: 5",
            it.print("song"));
        assertFuzzyEquals(
            "Memory pool expanded to be 512 bytes\r\n"
            + "Artist hash table size doubled\r\n"
            + "|Guitar Slim| is added to the Artist database\r\n"
            + "Song hash table size doubled\r\n"
            + "|The Things That I Used To Do| is added to the Song database",
                it.insert("Guitar Slim", "The Things That I Used To Do"));
        assertFuzzyEquals(
            "|Style Council| does not exist in the Artist database",
            it.remove("artist", "Style Council"));
        assertFuzzyEquals(
            "|Ma Rainey| is removed from the Artist database",
            it.remove("artist", "Ma Rainey"));
        assertFuzzyEquals(
            "|Mississippi Boweavil Blues| is removed from the Song database",
            it.remove("song", "Mississippi Boweavil Blues"));
        assertFuzzyEquals(
            "|(The Best Part Of) Breakin' Up| does not exist in the Song database",
            it.remove("song", "(The Best Part Of) Breakin' Up"));
        assertFuzzyEquals(
            "16: 64 272\r\n"
            + "32: 128\r\n"
            + "64: 320\r\n"
            + "128: 384",
            it.print("blocks"));
        assertFuzzyEquals(
            "|Blind Lemon Jefferson| duplicates a record already in the Artist database\r\n"
            + "|Got The Blues| is added to the Song database",
            it.insert("Blind Lemon Jefferson", "Got The Blues"));
        assertFuzzyEquals(
            "|Little Eva| is added to the Artist database\r\n"
            + "|The Loco-Motion| is added to the Song database",
            it.insert("Little Eva", "The Loco-Motion"));
        assertFuzzyEquals(
            "0: |Blind Lemon Jefferson|\r\n"
            + "4: |Bukka White|\r\n"
            + "7: TOMBSTONE\r\n"
            + "10: |Sleepy John Estes|\r\n"
            + "12: |Guitar Slim|\r\n"
            + "14: |Charley Patton|\r\n"
            + "18: |Little Eva|\r\n"
            + "total artists: 6",
            it.print("artist"));
        assertFuzzyEquals(
            "1: |Fixin' To Die Blues|\r\n"
            + "2: TOMBSTONE\r\n"
            + "5: |Street Car Blues|\r\n"
            + "8: |Got The Blues|\r\n"
            + "15: |Long Lonesome Blues|\r\n"
            + "16: |Ma Rainey's Black Bottom|\r\n"
            + "17: |The Things That I Used To Do|\r\n"
            + "18: |The Loco-Motion|\r\n"
            + "total songs: 7",
            it.print("song"));
        assertFuzzyEquals(
            "|Jim Reeves| is added to the Artist database\r\n"
            + "|Jingle Bells| is added to the Song database",
            it.insert("Jim Reeves", "Jingle Bells"));
        assertFuzzyEquals(
            "|Mongo Santamaria| is added to the Artist database\r\n"
            + "|Watermelon Man| is added to the Song database",
            it.insert("Mongo Santamaria", "Watermelon Man"));
        assertFuzzyEquals(
            "16: 368\r\n"
            + "128: 384",
            it.print("blocks"));
    }
    
    // ----------------------------------------------------------
    /**
     * Test that using create resets the songdb
     *
     * @throws Exception
     */
    public void testCreate() throws Exception {
        
        it = new SongsDB();
        assertEquals("", it.create(10, 32));

        it.insert("Tate McRae", "TIT FOR TAT");
        assertEquals("", it.create(5, 64));

        String[] artists = it.print("artist").split("\r\n");
        assertEquals(1, artists.length);
        assertEquals("total artists: 0", artists[0]);

        String[] songs = it.print("song").split("\r\n");
        assertEquals(1, songs.length);
        assertEquals("total songs: 0", songs[0]);
    }
    
    // ----------------------------------------------------------
    /**
     * Test that clear method fully resets db
     *
     * @throws Exception
     */
    public void testClear() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("Katelyn", "Purple Rain");
        
        assertTrue(it.clear());
        
        assertFuzzyEquals("total artists: 0", it.print("artist"));
        assertFuzzyEquals("total songs: 0", it.print("song"));
        
        assertFuzzyEquals("|Katelyn| does not exist in the Artist database", 
            it.remove("artist", "Katelyn"));
        
        assertFuzzyEquals("|Purple Rain| does not exist in the Song database", 
            it.remove("song", "Purple Rain"));
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test that print method clears memory manager to free block
     *
     * @throws Exception
     */
    public void testClearPrintBlock() throws Exception 
    {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("Katelyn", "Purple Rain");

        assertTrue(it.clear());

        String blocks = it.print("blocks").trim();

        assertEquals("32: 0", blocks);
    }
    
    // ----------------------------------------------------------
    /**
    * Test that duplicating a song does not add to count in insert
    *
    * @throws Exception
    */
    public void testDuplicateCount() throws Exception {
        it = new SongsDB();
        it.create(11, 64);

        it.insert("Rihanna", "Stay");
        it.insert("Post Malone", "Stay");
        
        String[] lines = it.print("song").split("\r\n");
        assertEquals("total songs: 1", lines[lines.length - 1]);
    }
    
    // ----------------------------------------------------------
    /**
    * Test that duplicating a song prints output in insert
    *
    * @throws Exception
    */   
    public void testDuplicateSong() throws Exception {
        it = new SongsDB();
        it.create(11, 64);

        it.insert("Rihanna", "Stay");

        String out = it.insert("Post Malone", "Stay");
        String[] lines = out.split("\r\n");

        assertEquals(2, lines.length);
        assertEquals("|Post Malone| is added to the Artist database", lines[0]);
        assertEquals("|Stay| duplicates a record already in the Song database", lines[1]);
    }
    
    // ----------------------------------------------------------
    /**
    * Tests printing artist and songs
    *
    * @throws Exception
    */    
    public void testPrint() throws Exception {
        it = new SongsDB();
        it.create(10, 32);

        it.insert("Men I Trust", "Sugar");

        String[] a = it.print("artist").split("\r\n");
        assertEquals("total artists: 1", a[a.length - 1]);

        String[] s = it.print("song").split("\r\n");
        assertEquals("total songs: 1", s[s.length - 1]);
    }
    
    // ----------------------------------------------------------
    /**
    * Test removing an artist
    *
    * @throws Exception
    */
    public void testRemove() throws Exception {
        it = new SongsDB();
        it.create(11, 64);

        it.insert("Katelyn", "Happy Song");

        assertEquals("|Katelyn| is removed from the Artist database",
            it.remove("artist", "Katelyn"));

        assertEquals("|Katelyn| does not exist in the Artist database",
            it.remove("artist", "Katelyn"));
    }
    
    
    // ----------------------------------------------------------
    /**
    * Test removing a missing artist and missing song
    *
    * @throws Exception
    */
    public void testRemoveMissing() throws Exception {
        it = new SongsDB();
        it.create(11, 32);

        assertEquals("|Nada| does not exist in the Artist database",
            it.remove("artist", "Nada"));
        assertEquals("|Nada| does not exist in the Song database",
            it.remove("song", "Nada"));
    }
    
    // ----------------------------------------------------------
    /**
    * Test print routing
    *
    * @throws Exception
    */
    public void testRemoveRoutes() throws Exception {
        it = new SongsDB();
        it.create(11, 64);

        it.insert("The Marias", "No One Noticed");

        assertEquals("|No One Noticed| is removed from the Song database",
            it.remove("song", "No One Noticed"));
        assertEquals("|The Marias| is removed from the Artist database",
            it.remove("artist", "The Marias"));
    }
    
    // ----------------------------------------------------------
    /**
    * Test the isPowerOfTwo helper
    *
    * @throws Exception
    */
    public void testIsPowerOfTwo() throws Exception {
        it = new SongsDB();
        assertEquals("", it.create(11, 2));

        it = new SongsDB();
        assertEquals("Initial memory manager size must be a power of 2",
            it.create(11, 6));
    }
}

