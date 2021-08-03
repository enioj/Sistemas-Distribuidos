package UnidirectionConection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public void exemplo1(int porta) {
		ObjectOutputStream saida;
		String mensagem = "";
		int flag = 0;
		Scanner ler = new Scanner(System.in);

		try {
			// criando um socket para ouvir na porta e com uma fila de tamanho 10
			ServerSocket servidor = new ServerSocket(porta, 10);
			Socket conexao;
			System.out.println("Ouvindo na porta: " + porta);

			// ficarah bloqueado aqui ate' alguem cliente se conectar
			conexao = servidor.accept();

			System.out.println("Conexao estabelecida com: " + conexao.getInetAddress().getHostAddress());

			// obtendo os fluxos de entrada e de saida
			saida = new ObjectOutputStream(conexao.getOutputStream());
			saida.flush();

			do { // fica aqui ate' o cliente enviar a mensagem SAIR
				System.out.print("Digite para o Cliente: ");
				mensagem = ler.nextLine();
				saida.writeObject(mensagem);
				saida.flush();
				flag++;
			} while (flag < 5);

			System.out.println("Conexao encerrada");

			saida.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
		}
	}

	public void exemplo2(int porta) {
		ObjectOutputStream saida;
		ObjectInputStream entrada;
		boolean sair = false;
		String mensagem = "";
		Scanner ler = new Scanner(System.in);

		try {
			// criando um socket para ouvir na porta e com uma fila de tamanho 10
			ServerSocket servidor = new ServerSocket(porta, 10);
			Socket conexao;
			System.out.println("Ouvindo na porta: " + porta);

			// ficarah bloqueado aqui ate' alguem cliente se conectar
			conexao = servidor.accept();

			System.out.println("Conexao estabelecida com: " + conexao.getInetAddress().getHostAddress());

			// obtendo os fluxos de entrada e de saida
			saida = new ObjectOutputStream(conexao.getOutputStream());
			saida.flush();
			entrada = new ObjectInputStream(conexao.getInputStream());

			// obtendo a mensagem enviada pelo cliente
			mensagem = (String) entrada.readObject();
			System.out.println("Cliente>> " + mensagem);

			while (!sair) { // fica aqui ate' o cliente enviar a mensagem SAIR
				System.out.print("Digite para o Cliente: ");
				mensagem = ler.nextLine();
				saida.writeObject(mensagem);
				saida.flush();
				// obtendo a mensagem enviada pelo cliente
				mensagem = (String) entrada.readObject();

				if (mensagem.equals("SAIR")) {
					sair = true;
				}
				System.out.println("Servidor>> " + mensagem);
			}

			System.out.println("Conexao encerrada pelo cliente");

			saida.close();
			entrada.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("Erro: " + e.toString());
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
		s.exemplo1(12345);
//		s.exemplo2(12345);
	}

}
