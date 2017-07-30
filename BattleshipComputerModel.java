import javax.swing.*;
import javax.swing.table.*;
public class BattleshipComputerModel extends AbstractTableModel
{
    public static String computerData[][] = new String[10][10];
    public static String column[] = new String[10];
    public static Battleship battleship;
    public BattleshipComputerModel(Battleship battleship,String [][] computerData)
    {
    	this.computerData = computerData;
    	this.battleship = battleship;
        battleship.configureComputer(this.computerData);
       for(int i=0; i<10; i++)
            column[i] = ""+(i+1);
    }
public int getColumnCount()
{
return 10;
//return title.length;
}
public int getRowCount()
{
return 10;
}
public String getColumnName(int c)
{
return column[c];
}
public Object getValueAt(int row,int col)
{
return computerData[row][col];
}
public Class getColumnClass(int c)
{
try
{
if(c==0) return Class.forName("java.lang.String");
if(c==1) return Class.forName("java.lang.String");
if(c==2) return Class.forName("java.lang.String");
if(c==3) return Class.forName("java.lang.String");
if(c==4) return Class.forName("java.lang.String");
if(c==5) return Class.forName("java.lang.String");
if(c==6) return Class.forName("java.lang.String");
if(c==7) return Class.forName("java.lang.String");
if(c==8) return Class.forName("java.lang.String");
if(c==9) return Class.forName("java.lang.String");
}catch(Exception e)
{
System.out.println("com.thinking.machines.automation.model.ElectronicUnitModel:Class getColumnClass(int)"+
e.getMessage());//remove after testing
}
return null;
}
public boolean isCellEditable(int row,int col)
{
return true;
}
	public boolean fetchData(int x,int y)
	{
		try
		{
			int check;
			check=battleship.something(x,y);
			if(check == 1)
			{
				JOptionPane.showMessageDialog(null,"Computer wins...");
				System.exit(0);
			}
			else if(check == 0)
			{
				JOptionPane.showMessageDialog(null,"User wins...");
				System.exit(0);
			}
			
		}
		catch(IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage());
			return false;
		}
		return true;
	}
}
