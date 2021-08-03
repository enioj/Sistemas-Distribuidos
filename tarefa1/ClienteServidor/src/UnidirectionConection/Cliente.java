package UnidirectionConection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public void exemplo1(String endereco, int porta) {
		ObjectInputStream entrada;
		Socket conexao;
		int flag = 0;
		try {
			conexao = new Socket(endereco, porta);
			System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);

			// ligando as conexoes de saida e de entrada
			entrada = new ObjectInputStream(conexao.getInputStream());

			do {
				// lendo a mensagem enviada pelo servidor
				String mensagem = (String) entrada.readObject();
				System.out.println("Servidor>> " + mensagem);
				flag++;
			} while (flag < 5);

			System.out.println("Voce recebeu 5 mensagens, aplicação encerrada.");
			entrada.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("erro: " + e.toString());
		}
	}

	public void exemplo2(String endereco, int porta) {
		ObjectOutputStream saida;
		ObjectInputStream entrada;
		Socket conexao;
		Scanner ler = new Scanner(System.in);
		String mensagem = "";
		try {
			conexao = new Socket(endereco, porta);
			System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);
			System.out.println("Digite: SAIR para encerrar a conexao");

			// ligando as conexoes de saida e de entrada
			saida = new ObjectOutputStream(conexao.getOutputStream());
			saida.flush();
			entrada = new ObjectInputStream(conexao.getInputStream());

			// lendo a mensagem enviada pelo servidor
			mensagem = (String) entrada.readObject();
			System.out.println("Servidor>> " + mensagem);

			do {
				System.out.print("Digite para o Servidor: ");
				mensagem = ler.nextLine();
				saida.writeObject(mensagem);
				saida.flush();
				// lendo a mensagem enviada pelo servidor
				mensagem = (String) entrada.readObject();
				System.out.println("Servidor>> " + mensagem);
			} while (!mensagem.equals("SAIR"));

			saida.close();
			entrada.close();
			conexao.close();

		} catch (Exception e) {
			System.err.println("erro: " + e.toString());
		}

	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.exemplo1("127.0.0.1", 12345);
//		c.exemplo2("127.0.0.1", 12345);
	}

}
