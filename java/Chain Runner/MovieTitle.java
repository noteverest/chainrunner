import java.util.*;


public class MovieTitle
{
    List<String> words;
    String fullTitle;
    public MovieTitle(String title)
    {
        fullTitle = title;
        String[] strs = title.split(" ");
        words = Arrays.asList(strs);
        
    }
    public String getChunk(int start, int stop)
    {
        if (stop > words.size()) return "~~~~";
        String str = words.get(start);
        for(int i = start + 1; i < stop ; i++)
        {
            str += " " + words.get(i);
        }
        
        return str;
    }


    public boolean makesChain(MovieTitle otherMovie)
    {
        if(otherMovie.fullTitle == fullTitle) return false;
        
        for(int x = 0; x < otherMovie.words.size() - 1; x++)
        {
            String tempChunk = otherMovie.getChunk(0, x);
            
            //System.out.println("TC = " + tempChunk);
            boolean c1 = false;
            boolean c2 = false;
            
            if(fullTitle.endsWith(" " + tempChunk))
            {
                /**
                int spaceIndex = tempChunk.indexOf(" ");
                //System.out.println("tempChunk = " + tempChunk);
                if(spaceIndex == -1)
                {
                    
                    
                        if(fullTitle.contains(" " + tempChunk) == false)
                        {
                            c1 = true;
                        }
                    
                }
                else
                {
                     if((otherMovie.fullTitle.contains(" " + tempChunk) == false) || 
                        (otherMovie.fullTitle.contains(tempChunk + " ") == false))c2 = true;
                }
                System.out.println("c1 : " + c1 + " c2: " + c2);
                
                */
                //if((c1 || c2) == false)
                
                if(otherMovie.fullTitle.startsWith(tempChunk))
                {
                    
                    //System.out.println("tempChunk = " + tempChunk);
                    //System.out.println(fullTitle + " --> " + otherMovie.fullTitle + " , ");
                    return true;
                }
            }
            
            
        }
        //System.out.println("FALSE");
        return false;
    }
    public boolean equals(MovieTitle other)
    {
        return fullTitle.equals(other.fullTitle);
    }
    
}
