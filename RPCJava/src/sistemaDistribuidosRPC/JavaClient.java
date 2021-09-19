package sistemaDistribuidosRPC;

/*Autor: Enio Rodrigues Bezerra Junior*/

import java.util.Vector; //Criar array de objetos
import java.util.Hashtable;
import java.util.Scanner;

import helma.xmlrpc.*;


public class JavaClient {

	// A localização do nosso servidor.
	private final static String server_url = "http://localhost:8080/RPC2";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {

		System.out.println("------------------------------");
		System.out.println("Sistema de Pedido de clientes");
		System.out.println("------------------------------");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		int questao;
		do {
			System.out.print(
					"\n################################\n[1] Fazer pedido para um cliente\n[2] Verficar pedido de uma mesa\n[3] Encerrar Programa\n################################\n-> ");
			questao = scan.nextInt();
			scan.nextLine();

			try {

				// Crie um objeto para representar nosso servidor.
				XmlRpcClient server = new XmlRpcClient(server_url);

				// Construa nossa lista de parâmetros.
				Vector params = new Vector();

				switch (questao) {
				case 1:
					System.out.println("==============================  ==========================  ==========================  =======================");
					System.out.println("Departamento de pratos prontos  Departamento de sobremesas  Departamento de sanduíches  Departamento de bebidas");
					System.out.println("==============================  ==========================  ==========================  =======================");
					System.out.println("          Feijoada                       Pudim                      xMec                        Suco\n" + 
							"        Estrogonofe                     Gelatina                    xTudo                    Refrigerante\n" + 
							"          Ravioli                         Torta                    xSalada                    Energetico\n" + 
							"          Ratatui                        Mousse                     xSabor\n" + 
							"           Bolo");
					System.out.println("===============================================================================================================");

					System.out.print("Informe o Pedido do cliente? (Escreva corretamente, como esta descrito acima os pratos do dia.)\n-> ");
					String pedido = scan.nextLine();
					while (pedido.equals("")) {
						System.out.print("Por favor, escreva o pedido do cliente.\n->");
						pedido = scan.nextLine();
					}

					params.addElement(new String(pedido));

					// Chama o servidor, e obtem o resultado.
					Hashtable result = (Hashtable) server.execute("rpc.pedidoCliente", params);

//					double novoSalario = ((Double) result.get("novoSalario")).doubleValue();
//					int difference = ((Integer) result.get("difference")).intValue();
					String pratoProntoEntregue = ((String) result.get("pratoProntoEntregue"));
					String sobremesaEntregue = ((String) result.get("sobremesaEntregue"));
					String sanduicheEntregue = ((String) result.get("sanduicheEntregue"));
					String bebidaEntregue = ((String) result.get("bebidaEntregue"));

					// Imprima o resultado.					
					System.out.println("Pedio do cliente já esta pronto, PratoPronto: " + pratoProntoEntregue + ", Sobremesa: "
							+ sobremesaEntregue + ", Sanduiche: " + sanduicheEntregue + ", Bebida: " + bebidaEntregue);
					break;
				case 2:
					System.out.print("Escreva o numero da mesa do cliente que deseja verificar?\n-> ");
					int mesaCliente = scan.nextInt();
					scan.nextLine();

					params.addElement(new Integer(mesaCliente));

					Hashtable resultMesa = (Hashtable) server.execute("rpc.verificaMesa", params);
					String pedidoDaMesa = ((String) resultMesa.get("pedidoDaMesa"));

					if (pedidoDaMesa == null) {
						System.out.println("Não há nenhuma mesa cadastrada no momento.");
					} else {
						System.out.println("O pedido da mesa " + mesaCliente + ", eh: " + pedidoDaMesa + "!");
					}

					break;
				case 3:
					break;

				default:
					System.out.println("Por favor informe alguma operaçao valida!");
					break;
				}

			} catch (XmlRpcException exception) {
				System.err.println(
						"JavaClient: XML-RPC Fault #" + Integer.toString(exception.code) + ": " + exception.toString());
			} catch (Exception exception) {
				System.err.println("JavaClient: " + exception.toString());
			}
		} while (questao != 3);

	}
}
