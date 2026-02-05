import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        //Remembering the old memory pool and hash table size to compare after insert
        int oldPoolSize = mm.poolSize;
        int oldHashSize = artist.hashTable.length;
        
        //Inserting the artist
        artist.insert(artistString, initSizeH);
        
        //Checking if memory pool doubled
        if (mm.poolSize > oldPoolSize)
        {
            ans.append("Memory pool expanded to be " + mm.poolSize + " bytes\r\n");
        }
        //Checking if artist hash table doubled
        if (artist.hashTable.length > oldHashSize)
        {
            ans.append("Artist hash table size doubled\r\n");
        }
        
        //Stating that the specific song was added to database
        ans.append("|" + artistString + "| is added to the Artist database\r\n");

        
        //Remembering the old memory pool and hash table size to compare after insert
        oldPoolSize = mm.poolSize;
        oldHashSize = song.hashTable.length;
        
        //Inserting the song
        song.insert(songString, initSizeH);
        
        //Checking if memory pool doubled
        if (mm.poolSize > oldPoolSize)
        {
            ans.append("Memory pool expanded to be " + mm.poolSize + " bytes\r\n");
        }
        //Checking if song hash table doubled
        if (song.hashTable.length > oldHashSize)
        {
            ans.append("Song hash table size doubled\r\n");
        }
        
        //Stating that the specific artist was added to database
        ans.append("|" + songString + "| is added to the Song database\r\n");

        return ans.toString();
        
        
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
        MemHandle rm = table.remove(nameString);
        
        if (rm == null)
        {
            if (type.equals("artist"))
            {
                return "|" + nameString + "| does not exist in the Artist database";
            }
            else {
                return "|" + nameString + "| does not exist in the Song database";
            }
        }
        
        mm.release(rm);
        
        if (type.equals("artist"))
        {
            return "|" + nameString + "| is removed from the Artist database";
        }
        else {
            return "|" + nameString + "| is removed from the Song database";
        }
        
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
        
        //test if initialized
        if (!init)
        {
            return "Database not initialized";
        }
        
        //test if empty or null
        if (type == null || type.isEmpty())
        {
            return "Input strings cannot be null or empty";
        }
        
        String ans = "";
        //prints according to type
        if (type.equals("artist"))
        {
        	
        	for (int i = 0; i < this.artist.hashTable.length; i++)
        	{
        		if (this.artist.hashTable[i] != null)
        		{
        			MemHandle currHandle = this.artist.hashTable[i];
        			String currArtistName = new String(mm.getRecord(currHandle), 0, currHandle.getRecordSize(), StandardCharsets.ISO_8859_1);
        			ans += i + ": |" + currArtistName + "|\r\n";
        		}
        		
        	}
            return ans + "total artists: " + artist.printTable();
        }
        
        else if (type.equals("song"))
        {
        	for (int i = 0; i < this.song.hashTable.length; i++)
        	{
        		if (this.song.hashTable[i] != null)
        		{
        			MemHandle currHandle = this.song.hashTable[i];
        			String currSongName = new String(mm.getRecord(currHandle), 0, currHandle.getRecordSize(), StandardCharsets.ISO_8859_1);
        			ans += i + ": |" + currSongName + "|\r\n";
        		}
        	}
            return ans + "total songs: " + song.printTable();
        }
        
        else if (type.equals("blocks"))
        {
            return mm.printBlocks();
        }
        
        else
        {
            return "Bad print parameter";
        }
        
        
    }
    
    /**Checks if value is a power of two by subtracting 1 to flip 
    * the bit and all bits below it, then AND'ing them, which 
    * would result in a 0 if value is a power of 2
    * @param x
    * @return True if power of 2, false if not
    */
    private boolean isPowerOfTwo(int x)
    {
        return (x & (x - 1)) == 0;
    }
}
