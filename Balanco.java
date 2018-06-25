
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe que recupera do banco o balanco geral das festas
public class Balanco {
    public static void saldoFinal() {
		double total;
                String festa, edicao;
		
		ResultSet resultSaldo = Database.runQuery("SELECT F.NOME, F.EDICAO, SUM(COALESCE(R.VALOR, 0)) FROM " +
                    "RECARGA R JOIN BARRACA B ON (BARRACA = B.ID_BARRACA) " +
                    "RIGHT JOIN FESTA F " +
                        "ON (F.NOME = B.FESTA AND F.EDICAO = B.EDICAO) " +
                    "GROUP BY F.NOME, F.EDICAO " +
                    "ORDER BY F.NOME, F.EDICAO;");
                ResultSet resultContrato = Database.runQuery("SELECT SUM(COALESCE(C.VALOR,0)) FROM " +
                        "FESTA F LEFT JOIN CONTRATO C " +
                            "ON C.FESTA = F.NOME AND C.EDICAO = F.EDICAO " +
                        "GROUP BY F.NOME, F.EDICAO " +
                        "ORDER BY F.NOME, F.EDICAO;");
                ResultSet resultProdutos = Database.runQuery("SELECT SUM(COALESCE(P.CUSTO, 0) * COALESCE(P.QTD, 0)) FROM " +
                        "FESTA F LEFT JOIN PRODUTO P " +
                            "ON F.NOME = P.FESTA AND F.EDICAO = P.EDICAO " +
                        "GROUP BY F.NOME, F.EDICAO " +
                        "ORDER BY F.NOME, F.EDICAO;");
                ResultSet resultConsumacao = Database.runQuery("SELECT COALESCE(CONSUMACAO, 0) * count(P.COD_CARTAO) FROM\n" +
                        "FESTA F LEFT JOIN PARTICIPANTE P\n" +
                            "ON F.NOME = P.FESTA AND F.EDICAO = P.EDICAO\n" +
                        "GROUP BY F.NOME, F.EDICAO\n" +
                        "ORDER BY F.NOME, F.EDICAO;");
                
                ResultSet resultAluguel = Database.runQuery("SELECT COALESCE(A.VALOR, 0) FROM\n" +
                        "FESTA F LEFT JOIN ALUGUEL A\n" +
                            "ON F.NOME = A.FESTA AND F.EDICAO = A.EDICAO\n" +
                        "ORDER BY F.NOME, F.EDICAO;");
		try {
			if (!resultSaldo.isBeforeFirst() || !resultContrato.isBeforeFirst()
                            || !resultProdutos.isBeforeFirst() || !resultConsumacao.isBeforeFirst()
                                                               || !resultAluguel.isBeforeFirst() ){
			    System.out.println("Nao há nenhuma festa cadastrada.");
			    return;
			}
                        
                        resultSaldo.next();
                        resultContrato.next();
                        resultProdutos.next();
                        resultAluguel.next();
                        resultConsumacao.next();
                            
                        while(!resultSaldo.isAfterLast() && !resultContrato.isAfterLast()
                              && !resultProdutos.isAfterLast() && !resultConsumacao.isAfterLast()
                                                               && !resultAluguel.isAfterLast()){
                            festa = resultSaldo.getString(1);
                            edicao = resultSaldo.getString(2);
                            total = resultSaldo.getDouble(3);
                            total += resultContrato.getDouble(1);
                            total -= resultProdutos.getDouble(1);
                            total += resultConsumacao.getDouble(1);
                            total -= resultAluguel.getDouble(1);
                            System.out.println(festa + " na edicao " + edicao + " teve saldo final de: R$" +  
                                    String.format("%.2f", total));
                            resultSaldo.next();
                            resultContrato.next();
                            resultProdutos.next();
                        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public static void despesasPorServico() {
		double custo;
                int catSer;
                String festa, edicao, categoria;
		
		ResultSet rS = Database.runQuery("SELECT  F.NOME, F.EDICAO, E.CATEGORIA_SERVICO, SUM(C.VALOR) FROM " +
                                        "FESTA F JOIN CONTRATO C " +
                                        "	ON C.FESTA = F.NOME AND C.EDICAO = F.EDICAO " +
                                        "JOIN EMPRESA E " +
                                        "	ON E.CNPJ = C.EMPRESA " +
                                        "WHERE C.VALOR < 0 " +
                                        "GROUP BY F.NOME, F.EDICAO, E.CATEGORIA_SERVICO;");
		try {
			if (!rS.isBeforeFirst()) {
			    System.out.println("Nao há nenhum servico cadastrado.");
			    return;
			}
                        rS.next();
                        while(!rS.isAfterLast()){
                            
                            festa = rS.getString(1);
                            edicao = rS.getString(2);
                            catSer = rS.getInt(3);
                            custo = rS.getDouble(4);
                            switch (catSer){
                                case 0:
                                    categoria = "Forncedor";
                                    break;
                                case 1:
                                    categoria = "Seguranca";
                                    break;
                                case 2:
                                    categoria = "Limpeza";
                                    break;
                                case 3:
                                    categoria = "Atendentes";
                                    break;
                                case 4:
                                    categoria = "Outros";
                                    break;
                                default:
                                    categoria = "null";
                                    break;
                            }
                            System.out.println(festa + " na edicao " + edicao + " teve custo de: R$" +  
                                    String.format("%.2f", -custo) + " no servico " + categoria);
                            rS.next();
                        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
