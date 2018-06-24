
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe que recupera do banco o balanco geral das festas
public class Balanco {
    public static void saldoFinal() {
		double totalArrecadado, totalContrato, totalProduto;
                String festa, edicao;
		
		ResultSet resultSaldo = Database.runQuery("SELECT F.NOME, F.EDICAO, SUM(COALESCE(VALOR, 0)) FROM " +
                        "RECARGA R JOIN BARRACA B " +
                        "ON R.BARRACA = B.ID_BARRACA " +
                    "RIGHT JOIN FESTA F " +
                        "ON F.NOME = B.FESTA AND F.EDICAO = B.EDICAO " +
                    "GROUP BY F.NOME, F.EDICAO " +
                    "ORDER BY F.NOME, F.EDICAO;");
                ResultSet resultContrato = Database.runQuery("SELECT SUM(COALESCE(C.VALOR,0)) FROM " +
                        "FESTA F LEFT JOIN CONTRATO C " +
                            "ON C.FESTA = F.NOME AND C.EDICAO = F.EDICAO " +
                        "GROUP BY F.NOME, F.EDICAO " +
                        "ORDER BY F.NOME, F.EDICAO;");
                ResultSet resultProdutos = Database.runQuery("SELECT SUM(COALESCE(R.VALOR, 0) * COALESCE(P.QTD, 0)) FROM " +
                        "FESTA F JOIN PRODUTO P " +
                            "ON F.NOME = P.FESTA AND F.EDICAO = P.EDICAO " +
                        "GROUP BY F.NOME, F.EDICAO " +
                        "ORDER BY F.NOME, F.EDICAO;");
		try {
			if (!resultSaldo.isBeforeFirst() || !resultContrato.isBeforeFirst()
                                            || !resultProdutos.isBeforeFirst()) {
			    System.out.println("Nao há nenhuma festa cadastrada.");
			    return;
			}
                        
                        while(!resultSaldo.isAfterLast() && !resultContrato.isAfterLast()
                                                        && !resultProdutos.isAfterLast()){
                            resultSaldo.next();
                            resultContrato.next();
                            resultProdutos.next();
                            festa = resultSaldo.getNString(1);
                            edicao = resultSaldo.getNString(2);
                            totalArrecadado = resultSaldo.getDouble(3);
                            totalContrato = resultContrato.getDouble(1);
                            totalProduto = resultProdutos.getDouble(1);
                            System.out.println(festa + " na edicao " + edicao + " teve saldoFinal de: R$" +  
                                    String.format("%.2f", totalArrecadado + totalContrato - totalProduto));
                        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public static void despesasPorServico() {
		double custo;
                String festa, edicao, catSer;
		
		ResultSet rS = Database.runQuery("SELECT  F.NOME, F.EDICAO, E.CATEGORIA_SERVICO, SUM(C.VALOR) FROM\n" +
                                        "FESTA F JOIN CONTRATO C \n" +
                                        "	ON C.FESTA = F.NOME AND C.EDICAO = F.EDICAO\n" +
                                        "JOIN EMPRESA E\n" +
                                        "	ON E.CNPJ = C.EMPRESA\n" +
                                        "GROUP BY F.NOME, F.EDICAO, E.CATEGORIA_SERVICO;");
		try {
			if (!rS.isBeforeFirst()) {
			    System.out.println("Nao há nenhum servico cadastrado.");
			    return;
			}
                        
                        while(!rS.isAfterLast()){
                            rS.next();
                            festa = rS.getNString(1);
                            edicao = rS.getNString(2);
                            catSer = rS.getNString(3);
                            custo = rS.getDouble(4);
                            System.out.println(festa + " na edicao " + edicao + " teve custo de: R$" +  
                                    String.format("%.2f", custo) + " no servico " + catSer);
                        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
