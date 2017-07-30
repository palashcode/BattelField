import javax.swing.*;    
import javax.swing.event.*;  
public class eg {    
      public static void main(String[] a) {  
            JFrame f = new JFrame("Table Example");  
            String data[][]={ {"101","Amit","670000"},    
                                                                       {"102","Jai","780000"},    
                                                                       {"101","Sachin","700000"}};    
                            String column[]={"ID","NAME","SALARY"};         
                            final JTable jt=new JTable(data,column);    
            jt.setCellSelectionEnabled(true);  
            jt.addMouseListener(new java.awt.event.MouseAdapter(){
            	public void mouseClicked(java.awt.event.MouseEvent e)
            	{
					int row = jt.rowAtPoint(e.getPoint());
        			int col = jt.columnAtPoint(e.getPoint());
        			System.out.println(row+","+col);
            	}
            }); 
            JScrollPane sp=new JScrollPane(jt);    
            f.add(sp);  
            f.setSize(300, 200);  
            f.setVisible(true);  
          }  
        }  