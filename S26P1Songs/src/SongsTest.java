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
}