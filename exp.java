import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;    
import java.awt.event.*;
import javax.swing.table.*;
public class exp 
{    
	static String data[][]=new String [10][10];
	static String computerData[][] = new String[10][10];
    JFrame fob;
    JPanel panel1,panel2;
    ButtonGroup grp1,grp2;
	JRadioButton r1,r2,r3,r4,r5,r6,r7;
	JButton btn1,btn2;
	JLabel l1,l2;
	int type;
	int orientation,x=-1,y=-1;
	static int count = 0;
	static JTable jt;
	static Battleship battleship = new Battleship();
	static BattleshipModel battleshipModel;
	static JTable jt1;
	static BattleshipComputerModel battleshipComputerModel;
    exp()
    {    
    	fob=new JFrame();    
    	fob.setSize(800,800);
    	fob.setLocation(350,50);
		fob.setLayout(null);
		fob.setTitle("BattleShip");
		
		battleshipModel = new BattleshipModel(battleship,data);
    	jt=new JTable(battleshipModel); 

    	jt.setCellSelectionEnabled(true);  
    	jt.addMouseListener(new MouseAdapter(){
            	public void mouseClicked(MouseEvent e)
            	{
					x = jt.rowAtPoint(e.getPoint());
        			y = jt.columnAtPoint(e.getPoint());
            	}
            });           
    	JScrollPane sp=new JScrollPane(jt);
    	sp.setBounds(50,100,300,300);    
    	fob.add(sp);
    	
    	l1=new JLabel("Player");
    	l1.setBounds(180,50,50,50);
    	
    	battleshipComputerModel = new BattleshipComputerModel(battleship,computerData);
    	jt1=new JTable(battleshipComputerModel);    
    	
    	jt1.setCellSelectionEnabled(true);  
    	
    	jt1.addMouseListener(new MouseAdapter(){
            	public void mouseClicked(MouseEvent e)
            	{
					x = jt1.rowAtPoint(e.getPoint());
        			y = jt1.columnAtPoint(e.getPoint());
        			battleshipComputerModel.fetchData(x,y);
            	}
            });           
    	
    	
    	jt1.setVisible(false);
    	    
    	JScrollPane sp1=new JScrollPane(jt1);
    	sp1.setBounds(450,100,300,300);    
    	fob.add(sp1);
    	
    	l2=new JLabel("Computer");
    	l2.setBounds(570,50,100,50);
    	
    	panel1=new JPanel();
    	panel1.setLayout(new BoxLayout(panel1,(BoxLayout.Y_AXIS)));
    	grp1=new ButtonGroup();
		r1=new JRadioButton("Aircraft Carrier");
		r2=new JRadioButton("Cruiser");
		r3=new JRadioButton("Destroyer");
		r4=new JRadioButton("Patrol Ship");
		r5=new JRadioButton("Submarine");
		grp1.add(r1);
		grp1.add(r2);
		grp1.add(r3);
		grp1.add(r4);
		grp1.add(r5);
		
		r1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				type = 4;
			}
		});
		r2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				type = 1;
			}
		});
		r3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				type = 0;
			}
		});
		r4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				type = 3;
			}
		});
		r5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				type = 2;
			}
		});
		
		
		panel1.add(r1);
		panel1.add(r2);
		panel1.add(r3);
		panel1.add(r4);
		panel1.add(r5);
		panel1.setBounds(50,500,150,145);
		panel1.setBorder(BorderFactory.createTitledBorder("Select Type of Ship"));
		
		panel2=new JPanel();
		panel2.setLayout(new BoxLayout(panel2,(BoxLayout.Y_AXIS)));
		grp2=new ButtonGroup();
		r6=new JRadioButton("Horizontal");
		r7=new JRadioButton("Vertical");
		grp2.add(r6);
		grp2.add(r7);
		
		r6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				orientation = 0;
			}
		});
		r7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				orientation = 1;
			}
		});
		
		panel2.add(r6);
		panel2.add(r7);
		panel2.setBounds(250,500,140,70);
		panel2.setBorder(BorderFactory.createTitledBorder("Select Orientation"));
		
		btn1=new JButton("Fix Position");
		btn1.setBounds(300,650,150,60);
		
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if((r1.isSelected()) && ((r6.isSelected()) || (r7.isSelected())) && x!=-1 && y!=-1)
				{
					if(r6.isSelected())
					{
						orientation = 0;
					}
					else if(r7.isSelected())
					{
						orientation = 1;
					}
					if (battleshipModel.fetchData(x,y,orientation,type))
					{
						r1.setVisible(false);
						grp1.clearSelection();
						grp2.clearSelection();
						count++;
						x=-1;
						y=-1;
					}
				}
				else if(r2.isSelected()&& ((r6.isSelected()) || (r7.isSelected())) && x!=-1 && y!=-1)
				{
				
					if(r6.isSelected())
					{
						orientation = 0;
					}
					else if(r7.isSelected())
					{
						orientation = 1;
					}
					if (battleshipModel.fetchData(x,y,orientation,type))
					{
						r2.setVisible(false);
						grp1.clearSelection();
						grp2.clearSelection();
						count++;
						x=-1;
						y=-1;
					}
				}
				else if(r3.isSelected() && ((r6.isSelected()) || (r7.isSelected())) && x!=-1 && y!=-1)
				{
				
					if(r6.isSelected())
					{
						orientation = 0;
					}
					else if(r7.isSelected())
					{
						orientation = 1;
					}
					if (battleshipModel.fetchData(x,y,orientation,type))
					{
						r3.setVisible(false);
						grp1.clearSelection();
						grp2.clearSelection();
						count++;
						x=-1;
						y=-1;
					}
				}
				else if(r4.isSelected() && ((r6.isSelected()) || (r7.isSelected())) && x!=-1 && y!=-1)
				{
					if(r6.isSelected())
					{
						orientation = 0;
					}
					else if(r7.isSelected())
					{
						orientation = 1;
					}
					if (battleshipModel.fetchData(x,y,orientation,type))
					{
						r4.setVisible(false);
						grp1.clearSelection();
						grp2.clearSelection();
						count++;
						x=-1;
						y=-1;
					}
				}
				else if(r5.isSelected() && ((r6.isSelected()) || (r7.isSelected())) && x!=-1 && y!=-1)
				{
					if(r6.isSelected())
					{
						orientation = 0;
					}
					else if(r7.isSelected())
					{
						orientation = 1;
					}
					if (battleshipModel.fetchData(x,y,orientation,type))
					{
						r5.setVisible(false);
						grp1.clearSelection();
						grp2.clearSelection();
						count++;
						x=-1;
						y=-1;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Select appropriate type option, orientation and location of ship.");
				}
				if(count==5)
				{
					btn1.setEnabled(false);
					jt.disable();
					x=-1;
					y=-1;
					jt1.enable();
					JOptionPane.showMessageDialog(null,"Now you can click on any cell of computer table to attack...");
					jt1.setVisible(true);
				}

			}
		});
		fob.add(panel1);
    	fob.add(panel2);
    	fob.add(btn1);
    	fob.add(l1);
    	fob.add(l2);
    	fob.setVisible(true);    
	}	     
	public static void main(String[] args)
	{    
    	new exp();    
	}    
}
class Process
{
	public void refreshTable()
	{
		exp.battleshipModel.fireTableDataChanged();
		exp.jt.repaint();
		exp.battleshipComputerModel.fireTableDataChanged();
		exp.jt1.repaint();
	}
}
