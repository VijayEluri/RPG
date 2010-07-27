/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rpg;

/**
 *
 * @author belimawr
 */
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Escrevedor extends Thread
{
	private DataInputStream in;
        private JTextArea txt_chat;
 
	Escrevedor(DataInputStream i, JTextArea txt_chat)
	{
		in = i;
                this.txt_chat = txt_chat;
	}
        
        @Override
	public void run()
	{
                String mensagem, texto;
		while(true)
		{
                        System.out.print("Jogador esperando uma mensagem!!");
			try
			{
                                mensagem = in.readUTF();
				System.out.printf("\n%s", mensagem);
                                //texto = txt_chat.getText();
                                //texto += mensagem;
                                //txt_chat.setText(texto);
                                txt_chat.append(mensagem);
                                txt_chat.setCaretPosition(txt_chat.getText().length()); 
			}
                        catch(EOFException e)
                        {
                                JOptionPane.showMessageDialog(null, "A conexão com o servidor foi interrompida.");
                                System.exit(1);
                        }
			catch(Exception e)
			{
				e.printStackTrace();
                                System.exit(1);
			}
		}
	}
}