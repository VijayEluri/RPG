/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg;

/**
 *
 * @author belimawr
 */
import javax.swing.*;
import java.net.*;
import java.io.*;

public class Cliente
{

        private String nick, ip_servidor;
        private Socket s = null;
        private DataInputStream in;
        private DataOutputStream out = null;
        private String m;
        private Escrevedor e;
        private int porta;
        private JTextArea txt_chat;
        private Conexao_ficha conecxao_ficha;

        Cliente (JTextArea txt_chat)
        {
                this.txt_chat = txt_chat;
                try
                {
                        //Pergunda as informações da conecxão e do jogador
                        nick = JOptionPane.showInputDialog("Qual o seu nome?", "Jogador");
                        ip_servidor = JOptionPane.showInputDialog("Digite o IP do servidor", "127.0.0.1");
                        porta = Integer.parseInt(JOptionPane.showInputDialog("Digite a porta do servidor:", "2010"));
                        if (nick == null)
                        {
                                nick = "Sem nick";
                        }
                        //Seta o nome do jogador de modo que ele não possa mais ser alterado
                        RPG_globais.getMinha_ficha().setNome_jogador(nick);

                        //Cria o soket, o input e output stream para o chat, e o escrevedor
                        s = new Socket(ip_servidor, porta);
                        in = new DataInputStream(s.getInputStream());
                        out = new DataOutputStream(s.getOutputStream());
                        e = new Escrevedor(in, txt_chat);
                        e.start();

                        //Cria o socket para as fichas
                        Socket s1 = new Socket(ip_servidor, porta + 1);
                        conecxao_ficha = new Conexao_ficha(s1, false);
                        conecxao_ficha.start();


                }
                catch (NumberFormatException eeeeee)
                {
                        JOptionPane.showMessageDialog(null, "Erro nos dados informados!");
			throw new ExceptionInInitializerError();
                }
                catch (Exception eee)
                {
                        System.exit(1);
                }
        }

        public String getNick ()
        {
                return nick;
        }

        public void Escrever_mensagem (String mensagem)
        {
                if (mensagem.equals(""))
                {
                        return;
                }

                try
                {
                        if (mensagem == null)
                        {
                                s.close();
                                System.exit(0);
                        }
                        out.writeUTF("<" + nick + "> " + mensagem);
                }
                catch (Exception eee)
                {
                        System.out.printf("\n\nErro no servidor, conexão fechada!\n\n");
                        System.exit(1);
                }
        }

        public Conexao_ficha getConecxao_ficha ()
        {
                return conecxao_ficha;
        }
}
