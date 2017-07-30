import java.util.Scanner;

public class Battleship
{
    public static Scanner reader = new Scanner(System.in);
    private static String data[][];
    private static String computerData[][];
    private static int x,y,orientation,type,x1,y1;
    public Player userPlayer,computer;
    public void fetchData(int x,int y,int orientation,int type)
    {
	this.x = x;
	this.y = y;
	this.orientation = orientation;
	this.type = type;
	setup(userPlayer,data);
    }
    public void configureComputer(String [][] computerData)
    {
    	this.computerData = computerData;
    	computer = new Player();
    	computer.playerGrid.printShips(computerData);
    	setupComputer(computer);
    }
    public void configureUser(String [][]data)
    {
    	this.data = data;
        userPlayer = new Player();
        userPlayer.playerGrid.printShips(data);

    }
    public int something(int x,int y)
    {
    		this.x1=x;
    		this.y1=y;
        	String result = "";
            result = askForGuess(userPlayer, computer,x1,y1);
            Process pr =new Process();
            pr.refreshTable();
            if (userPlayer.playerGrid.hasLost())
            {
                return 1;
            }
            else if (computer.playerGrid.hasLost())
            {
                //System.out.println("HIT!...COMPUTER LOSES");
                return 0;
            }

            //System.out.println("\nCOMPUTER IS MAKING GUESS...");
            compMakeGuess(computer, userPlayer);
            
            pr.refreshTable();
            return 2;
    }
    
    private static void compMakeGuess(Player comp, Player user)
    {
        Randomizer rand = new Randomizer();
        int row = rand.nextInt(0, 9);
        int col = rand.nextInt(0, 9);
        
        // While computer already guessed this posiiton, make a new random guess
        while (comp.oppGrid.alreadyGuessed(row, col))
        {
            row = rand.nextInt(0, 9);
            col = rand.nextInt(0, 9);
        }
        
        if (user.playerGrid.hasShip(row, col))
        {
            comp.oppGrid.markHit(row, col);
            user.playerGrid.markHit(row, col);
            //System.out.println("COMP HIT AT " + convertIntToLetter(row) + convertCompColToRegular(col));
        }
        else
        {
            comp.oppGrid.markMiss(row, col);
            user.playerGrid.markMiss(row, col);
            //System.out.println("COMP MISS AT " + convertIntToLetter(row) + convertCompColToRegular(col));
        }
        
        
        user.playerGrid.printCombined(data);
    }

    private static String askForGuess(Player p, Player opp,int x,int y)
    {
    	//System.out.println("ask for guess");
        
        p.oppGrid.printStatus(computerData);
        if (opp.playerGrid.hasShip(x, y))
        {
            p.oppGrid.markHit(x, y);
            opp.playerGrid.markHit(x, y);
            return "** USER HIT AT " + x + y + " **";
        }
        else
        {
            p.oppGrid.markMiss(x, y);
            opp.playerGrid.markMiss(x, y);
            return "** USER MISS AT " + x + y + " **";
        }
    }
    
    private static void setup(Player p,String [][]data)
    {
        //p.playerGrid.printShips(data);
        if(p.numOfShipsLeft() > 0)
        {
        	if(!hasErrors(x,y,orientation,p,type))
        	{
                p.ships[type].setLocation(x, y);
                p.ships[type].setDirection(orientation);
                p.playerGrid.addShip(p.ships[type]);
                p.playerGrid.printShips(data);
                //table refresh
				Process process = new Process();
				process.refreshTable();
			}
        }
    }

    private static void setupComputer(Player p)
    {
        int counter = 1;
        int normCounter = 0;
        
        Randomizer rand = new Randomizer();
        
        while (p.numOfShipsLeft() > 0)
        {
            for (Ship s: p.ships)
            {
                int row = rand.nextInt(0, 9);
                int col = rand.nextInt(0, 9);
                int dir = rand.nextInt(0, 1);
                
                //System.out.println("DEBUG: row-" + row + "; col-" + col + "; dir-" + dir);
                
                while (hasErrorsComp(row, col, dir, p, normCounter)) // while the random nums make error, start again
                {
                    row = rand.nextInt(0, 9);
                    col = rand.nextInt(0, 9);
                    dir = rand.nextInt(0, 1);
                    //System.out.println("AGAIN-DEBUG: row-" + row + "; col-" + col + "; dir-" + dir);
                }
                
                //System.out.println("FURTHER DEBUG: row = " + row + "; col = " + col);
                p.ships[normCounter].setLocation(row, col);
                p.ships[normCounter].setDirection(dir);
                p.playerGrid.addShip(p.ships[normCounter]);
                
                normCounter++;
                counter++;
            }
        }
    }
    
    private static boolean hasErrors(int row, int col, int dir, Player p, int count)
    {
    	System.out.println(row+","+col);
        //System.out.println("DEBUG: count arg is " + count);
        
        int length = p.ships[count].getLength();
        
        // Check if off grid - Horizontal
        if (dir == 0)
        {
            int checker = length + col;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 10)
            {
                throw new IllegalArgumentException("SHIP DOES NOT FIT");
            }
        }
        
        // Check if off grid - Vertical
        if (dir == 1) // VERTICAL
        {
            int checker = length + row;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 10)
            {
                throw new IllegalArgumentException("SHIP DOES NOT FIT");
            }
        }
            
        // Check if overlapping with another ship
        if (dir == 0) // Hortizontal
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = col; i < col+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(row, i))
                {
                	throw new IllegalArgumentException("THERE IS ALREADY A SHIP AT THAT LOCATION");
                }
            }
        }
        else if (dir == 1) // Vertical
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = row; i < row+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(i, col))
                {
                	throw new IllegalArgumentException("THERE IS ALREADY A SHIP AT THAT LOCATION");                }
            }
        }
        
        return false;
    }
    
    private static boolean hasErrorsComp(int row, int col, int dir, Player p, int count)
    {
        //System.out.println("DEBUG: count arg is " + count);
        
        int length = p.ships[count].getLength();
        
        // Check if off grid - Horizontal
        if (dir == 0)
        {
            int checker = length + col;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 10)
            {
                return true;
            }
        }
        
        // Check if off grid - Vertical
        if (dir == 1) // VERTICAL
        {
            int checker = length + row;
            //System.out.println("DEBUG: checker is " + checker);
            if (checker > 10)
            {
                return true;
            }
        }
            
        // Check if overlapping with another ship
        if (dir == 0) // Hortizontal
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = col; i < col+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(row, i))
                {
                    return true;
                }
            }
        }
        else if (dir == 1) // Vertical
        {
            // For each location a ship occupies, check if ship is already there
            for (int i = row; i < row+length; i++)
            {
                //System.out.println("DEBUG: row = " + row + "; col = " + i);
                if(p.playerGrid.hasShip(i, col))
                {
                    return true;
                }
            }
        }
        
        return false;
    }


    /*HELPER METHODS*/
    private static int convertLetterToInt(String val)
    {
        int toReturn = -1;
        switch (val)
        {
            case "A":   toReturn = 0;
                        break;
            case "B":   toReturn = 1;
                        break;
            case "C":   toReturn = 2;
                        break;
            case "D":   toReturn = 3;
                        break;
            case "E":   toReturn = 4;
                        break;
            case "F":   toReturn = 5;
                        break;
            case "G":   toReturn = 6;
                        break;
            case "H":   toReturn = 7;
                        break;
            case "I":   toReturn = 8;
                        break;
            case "J":   toReturn = 9;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
    
    private static String convertIntToLetter(int val)
    {
        String toReturn = "Z";
        switch (val)
        {
            case 0:   toReturn = "A";
                        break;
            case 1:   toReturn = "B";
                        break;
            case 2:   toReturn = "C";
                        break;
            case 3:   toReturn = "D";
                        break;
            case 4:   toReturn = "E";
                        break;
            case 5:   toReturn = "F";
                        break;
            case 6:   toReturn = "G";
                        break;
            case 7:   toReturn = "H";
                        break;
            case 8:   toReturn = "I";
                        break;
            case 9:   toReturn = "J";
                        break;
            default:    toReturn = "Z";
                        break;
        }
        
        return toReturn;
    }
    
    private static int convertUserColToProCol(int val)
    {
        int toReturn = -1;
        switch (val)
        {
            case 1:   toReturn = 0;
                        break;
            case 2:   toReturn = 1;
                        break;
            case 3:   toReturn = 2;
                        break;
            case 4:   toReturn = 3;
                        break;
            case 5:   toReturn = 4;
                        break;
            case 6:   toReturn = 5;
                        break;
            case 7:   toReturn = 6;
                        break;
            case 8:   toReturn = 7;
                        break;
            case 9:   toReturn = 8;
                        break;
            case 10:   toReturn = 9;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
    
    private static int convertCompColToRegular(int val)
    {
        int toReturn = -1;
        switch (val)
        {
            case 0:   toReturn = 1;
                        break;
            case 1:   toReturn = 2;
                        break;
            case 2:   toReturn = 3;
                        break;
            case 3:   toReturn = 4;
                        break;
            case 4:   toReturn = 5;
                        break;
            case 5:   toReturn = 6;
                        break;
            case 6:   toReturn = 7;
                        break;
            case 7:   toReturn = 8;
                        break;
            case 8:   toReturn = 9;
                        break;
            case 9:   toReturn = 10;
                        break;
            default:    toReturn = -1;
                        break;
        }
        
        return toReturn;
    }
}
