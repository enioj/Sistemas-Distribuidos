package sistemaDistribuidosRPC;

/*Autor: Enio Rodrigues Bezerra Junior*/

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import helma.xmlrpc.*;

public class JavaServer {

	public JavaServer() {
		// Our handler is a regular Java object. It can have a
		// constructor and member variables in the ordinary fashion.
		// Public methods will be exposed to XML-RPC clients.
	}

	Vector<Mesa> listMesas = new Vector<Mesa>();
	int countMesa = 0;

//	Em vez de fazer um Objeto para cada Departamento com os pedidos resolvi colocar em um enum para simplificar.
	private enum PratosProntos {
		Feijoada, Estrogonofe, Ravioli, Ratatui;
	}

	private enum Sobremesas {
		Pudim, Gelatina, Torta, Mousse, Bolo, Sorvete;
	}

	private enum Sanduiche {
		xMec, xTudo, xSalada, xSabor;
	}

	private enum Bebidas {
		Suco, Refrigerante, Energetico;
	}

	public void departamentoPratosProntos(Hashtable<String, String> result, String pratoPronto) {
		result.put("pratoProntoEntregue", new String(pratoPronto));
	}

	public void departamentoSobremesas(Hashtable<String, String> result, String sobremesa) {
		result.put("sobremesaEntregue", new String(sobremesa));
	}

	public void departamentoSanduiche(Hashtable<String, String> result, String sanduiche) {
		result.put("sanduicheEntregue", new String(sanduiche));
	}

	public void departamentoBebidas(Hashtable<String, String> result, String bebida) {
		result.put("bebidaEntregue", new String(bebida));
	}

	public Hashtable<String, String> pedidoCliente(String pedido) {
		Hashtable<String, String> result = new Hashtable<String, String>();
		List<String> lista = new ArrayList<>();

		adicionarPedidoMesa(pedido);

		String[] colunas = pedido.split(",");
		for (String item : colunas) {
			lista.add(item);
		}

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).contains(PratosProntos.Estrogonofe.toString())
					|| lista.get(i).contains(PratosProntos.Feijoada.toString())
					|| lista.get(i).contains(PratosProntos.Ratatui.toString())
					|| lista.get(i).contains(PratosProntos.Ravioli.toString())) {
				departamentoPratosProntos(result, lista.get(i));
			}
		}

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).contains(Sobremesas.Bolo.toString())
					|| lista.get(i).contains(Sobremesas.Gelatina.toString())
					|| lista.get(i).contains(Sobremesas.Mousse.toString())
					|| lista.get(i).contains(Sobremesas.Pudim.toString())
					|| lista.get(i).contains(Sobremesas.Sorvete.toString())
					|| lista.get(i).contains(Sobremesas.Torta.toString())) {
				departamentoSobremesas(result, lista.get(i));
			}
		}

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).contains(Sanduiche.xMec.toString()) || lista.get(i).contains(Sanduiche.xTudo.toString())
					|| lista.get(i).contains(Sanduiche.xSalada.toString())
					|| lista.get(i).contains(Sanduiche.xSabor.toString())) {
				departamentoSanduiche(result, lista.get(i));
			}
		}

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).contains(Bebidas.Energetico.toString())
					|| lista.get(i).contains(Bebidas.Refrigerante.toString())
					|| lista.get(i).contains(Bebidas.Suco.toString())) {
				departamentoBebidas(result, lista.get(i));
			}
		}

//        double reajuste;
//        switch (cargo){
//        case "operador":
//            reajuste = salario + (salario * 0.2);
//            result.put("novoSalario", new Double(reajuste));
//            result.put("nomeRecebido", new String(nome));
//        break;
//        case "programador":
//            reajuste = salario + (salario * 0.18);
//            result.put("novoSalario", new Double(reajuste));
//            result.put("nomeRecebido", new String(nome));
//        break;
//        }
		return result;
	}

	public void adicionarPedidoMesa(String pedidoMesa) {
		countMesa++;

		Mesa mesa = new Mesa();
		mesa.setPedidoMesa(pedidoMesa);
		mesa.setNumeroMesa(countMesa);

		listMesas.addElement(mesa);
	}

	public Hashtable<String, String> verificaMesa(int verificamesa) {
		Hashtable<String, String> result = new Hashtable<String, String>();		

		for (int i = 0; i < listMesas.size(); i++) {
			Mesa mesa = (Mesa) listMesas.get(i);
			if (mesa.getNumeroMesa() == verificamesa) {
				result.put("pedidoDaMesa", new String(mesa.getPedidoMesa()));

			}
		}
		return result;
	}

	public static void main(String[] args) {
		try {

			// Invoke me as <http://localhost:8080/RPC2>.
			WebServer server = new WebServer(8080); // Cria a instancia de um server
			server.addHandler("rpc", new JavaServer()); // SOAP Message handlers can be used to process messages to and from a Web service
			server.start();

			System.out.println("Iniciado com sucesso.");
			System.out.println("Aceitando solicitações. (Pare o programa para parar.)");

		} catch (Exception exception) {
			System.err.println("JavaServer: " + exception.toString());
		}
	}
}
