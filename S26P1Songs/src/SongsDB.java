import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and a memory manager.
 *
 * @author Katelyn Cao, Hunter Dillon
 * @version feb 3, 2026
 */
public class SongsDB implements Songs
{

    private MemManager mm;
    private Hash artist;
    private Hash song;
    
    private boolean init = false;
    private int initSizeM;
    private int initSizeH;
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SongsDB object.
     * But don't set anything -- that gets done by "create"
     */
    public SongsDB()
    {
    }


    /**
     * Create a brave new World.
     *
     * @param inHash
     *            Initial size for hash tables
     * @param inMemMan
     *            Initial size for the memory manager
     * @return Error messages if appropriate
     */
    public String create(int inHash, int inMemMan)
    {
        //test hash size input
        if (inHash <= 0)
        {
            init = false;
            return "Initial hash table size must be positive";
        }
        
        // test memory manager input (size and power)
        if (inMemMan <= 0)
        {
            init = false;
            return "Initial memory manager size must be positive";
        }
        
        if (!isPowerOfTwo(inMemMan))
        {
            init = false;
            return "Initial memory manager size must be a power of 2";
        }
        
        //set fields
        mm = new MemManager(inMemMan);
        artist = new Hash(inHash, mm);
        song = new Hash(inHash, mm);
        init = true;
        initSizeM = inMemMan;
        initSizeH = inHash;
        
        return "";
    }


    /**
     * Re-initialize the database
     * @return true on successful clear of database
     */
    public boolean clear() {
        
        //false if re-initialize failed
        if (!init)
        {
            return false;
        }
        
        //set to new mem manager and hashes
        mm = new MemManager(initSizeM);
        artist = new Hash(initSizeH, mm);
        song = new Hash(initSizeH, mm);
        
        //return true if success
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Insert to the hash table
     *
     * @param artistString
     *            Artist string to insert
     * @param songString
     *            Song string to insert
     * @return Error message if appropriate
     * @throws IOException
     */
    public String insert(String artistString, String songString)
        throws IOException
    {
        // test initialized
        if (!init)
        {
            return "Database not initialized";
        }
        
        // test if empty
        if (artistString == null || artistString.isEmpty() || songString == null
            || songString.isEmpty())
        {
            return "Input strings cannot be null or empty";
        }
        
        StringBuilder ans = new StringBuilder();
        
        //artist
        int old = mm.poolSize();
        
        return "";
        
        
    }


    // ----------------------------------------------------------
    /**
     * Remove from the hash table
     *
     * @param type
     *            The table to be removed
     * @param nameString
     *            The string to be removed from the table
     * @return Error message if appropriate
     * @throws IOException
     */
    public String remove(String type, String nameString) throws IOException {
        
        //test initialized
        if (!init)
        {
            return "Database not initialized";
        }
        
        //test empty/null
        if (type == null || type.isEmpty() || nameString == null || 
            nameString.isEmpty())
        {
            return "Input strings cannot be null or empty";
        }
        
        //test type
        if (!type.equals("artist") && !type.equals("song"))
        {
            return "Bad type value |" + type + "| on remove";
        }
        
        //find table to remove from
        Hash table;
        if (type.equals("artist"))
        {
            table = artist;
        }
        else {
            table = song;
        }
        
        //remove from table
        
        return "";
    }


    // ----------------------------------------------------------
    /**
     * Print out the hash table contents
     *
     * @param type
     *            Controls what object is being printed
     * @return The string that was printed
     * @throws IOException
     */
    public String print(String type)
        throws IOException {
        return "";
    }
}
