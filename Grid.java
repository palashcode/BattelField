public class Grid
{
    private Location[][] grid;
    private int points;

    // Constants for number of rows and columns.
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;
    
    public Grid()
    {
        grid = new Location[NUM_ROWS][NUM_COLS];
        
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                Location tempLoc = new Location();
                grid[row][col] = tempLoc;
            }
        }
    }
    
    // Mark a hit in this location by calling the markHit method
    // on the Location object.  
    public void markHit(int row, int col)
    {
        grid[row][col].markHit();
        points++;
    }
    
    // Mark a miss on this location.    
    public void markMiss(int row, int col)
    {
        grid[row][col].markMiss();
    }
    
    // Set the status of this location object.
    public void setStatus(int row, int col, int status)
    {
        grid[row][col].setStatus(status);
    }
    
    // Get the status of this location in the grid  
    public int getStatus(int row, int col)
    {
        return grid[row][col].getStatus();
    }
    
    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col)
    {
        return !grid[row][col].isUnguessed();
    }
    
    // Set whether or not there is a ship at this location to the val   
    public void setShip(int row, int col, boolean val)
    {
        grid[row][col].setShip(val);
    }
    
    // Return whether or not there is a ship here   
    public boolean hasShip(int row, int col)
    {
        return grid[row][col].hasShip();
    }
    
    // Get the Location object at this row and column position
    public Location get(int row, int col)
    {
        return grid[row][col];
    }
    
    // Return the number of rows in the Grid
    public int numRows()
    {
        return NUM_ROWS;
    }

    // Return the number of columns in the grid
    public int numCols()
    {
        return NUM_COLS;
    }
    
    public void printStatus(String [][]data)
    {
        generalPrintMethod(0,data);
    }
    
    public void printShips(String [][] data)
    {
        generalPrintMethod(1,data);
    }
    
    public void printCombined(String [][]data)
    {
        generalPrintMethod(2,data);
    }
    
    public boolean hasLost()
    {
        if (points >= 17)
            return true;
        else
            return false;
    }
    
    public void addShip(Ship s)
    {
        int row = s.getRow();
        int col = s.getCol();
        int length = s.getLength();
        int dir = s.getDirection();
        if (!(s.isDirectionSet()) || !(s.isLocationSet()))
            throw new IllegalArgumentException("ERROR! Direction or Location is unset/default");
        
        // 0 - hor; 1 - ver
        if (dir == 0) // Hortizontal
        {	
	    	if(col+length > 10) 
	    	{
				throw new IllegalArgumentException("Select appropriate cell according to length : "+length);
            }
            for (int i = col; i < col+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                grid[row][i].setShip(true);
                grid[row][i].setLengthOfShip(length);
                grid[row][i].setDirectionOfShip(dir);
            }
        }
        else if (dir == 1) // Vertical
        {
        	if(row+length > 10) 
        	{
				throw new IllegalArgumentException("Select appropriate cell according to length : "+length);
            }
            for (int i = row; i < row+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                grid[i][col].setShip(true);
                grid[i][col].setLengthOfShip(length);
                grid[i][col].setDirectionOfShip(dir);
            }
        }
    }
    
    // Type: 0 for status, 1 for ships, 2 for combined
    private void generalPrintMethod(int type,String [][]data)
    {
        // Print rows
        for (int i = 0; i <10; i++)
        {
            
            for (int j = 0; j < NUM_COLS; j++)
            {
                if (type == 0) // type == 0; status
                {
                    if (grid[i][j].isUnguessed())
                        data[i][j]="~";
                    else if (grid[i][j].checkMiss())
                        data[i][j]="O";
                    else if (grid[i][j].checkHit())
                        data[i][j]="X";
                }
                else if (type == 1) // type == 1; ships
                {
                    if (grid[i][j].hasShip())
                    {
                        // System.out.print("X ");
                        if (grid[i][j].getLengthOfShip() == 2)
                        {
                            data[i][j]="D";
                        }
                        else if (grid[i][j].getLengthOfShip() == 3)
                        {
                            data[i][j]="C";
                        }
                        else if (grid[i][j].getLengthOfShip() == 4)
                        {
                            data[i][j]="B";
                        }
                        else if (grid[i][j].getLengthOfShip() == 5)
                        {
                            data[i][j]="A";
                        }
                    }
                        
                    else if (!(grid[i][j].hasShip()))
                    {
                        data[i][j] = "~";
                    }
                        
                }
                else // type == 2; combined
                {
                	if(grid[i][j].checkMiss())
                		data[i][j]="0";
                    if (grid[i][j].checkHit())
                        data[i][j]="X";
                    else if (grid[i][j].hasShip())
                    {
                        // System.out.print("X ");
                        if (grid[i][j].getLengthOfShip() == 2)
                        {
                            data[i][j]="D";
                        }
                        else if (grid[i][j].getLengthOfShip() == 3)
                        {
                            data[i][j]="C";
                        }
                        else if (grid[i][j].getLengthOfShip() == 4)
                        {
                            data[i][j]="B";
                        }
                        else if (grid[i][j].getLengthOfShip() == 5)
                        {
                            data[i][j]="A";
                        }
                    }
                    else if (!(grid[i][j].hasShip()))
                    {
                        data[i][j]="~";
                    }  
                }
            }
            System.out.println();
        }
    }
    
    public int switchCounterToIntegerForArray (int val)
    {
        int toReturn = -1;
        switch (val)
        {
            case 65:    toReturn = 0;
                        break;
            case 66:    toReturn = 1;
                        break;
            case 67:    toReturn = 2;
                        break;
            case 68:    toReturn = 3;
                        break;
            case 69:    toReturn = 4;
                        break;
            case 70:    toReturn = 5;
                        break;
            case 71:    toReturn = 6;
                        break;
            case 72:    toReturn = 7;
                        break;
            case 73:    toReturn = 8;
                        break;
            case 74:    toReturn = 9;
                        break;
            case 75:    toReturn = 10;
                        break;
            case 76:    toReturn = 11;
                        break;
            case 77:    toReturn = 12;
                        break;
            case 78:    toReturn = 13;
                        break;
            case 79:    toReturn = 14;
                        break;
            case 80:    toReturn = 15;
                        break;
            case 81:    toReturn = 16;
                        break;
            case 82:    toReturn = 17;
                        break;
            case 83:    toReturn = 18;
                        break;
            case 84:    toReturn = 19;
                        break;
            case 85:    toReturn = 20;
                        break;
            case 86:    toReturn = 21;
                        break;
            case 87:    toReturn = 22;
                        break;
            case 88:    toReturn = 23;
                        break;
            case 89:    toReturn = 24;
                        break;
            case 90:    toReturn = 25;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        if (toReturn == -1)
        {
            throw new IllegalArgumentException("ERROR OCCURED IN SWITCH");
        }
        else
        {
            return toReturn;
        }
    }   
}
