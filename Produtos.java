import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Produtos {

	public static void produtosCompradosPorEmpresa(Festa festa) {
		String empresa;
		
		System.out.println("Digite o CNPJ da empresa (XXX.XXX.XXX-XX): ");
        System.out.print("> ");
        empresa = Keyboard.readLine();
        if(!verifyEmpresa(empresa)) return;
        System.out.println("Empresa encontrada!");
        printProducts(festa, empresa);
	}
	
	private static void printProducts(Festa festa, String empresa) {
		System.out.println("Produtos fornecidos:");
		System.out.println("ID\tCUSTO\tQTD\tNOME");
		ResultSet result = Database.runQuery("SELECT ID_PRODUTO, CUSTO, QTD, NOME" + 
				" FROM PRODUTO" + 
				" WHERE (FORNECEDOR = '" + empresa + "') AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
		try {
			if (!result.isBeforeFirst() ) {
			    System.out.println("Essa empresa nao forneceu produtos para esta festa.");
			    return;
			}
	        ResultSetMetaData rsmd = result.getMetaData();
	        int columnsNumber = rsmd.getColumnCount();
	        while (result.next()) {
	            for (int i = 1; i <= columnsNumber; i++) {
	                String columnValue = result.getString(i);
	                if (i == 1) System.out.print(columnValue);
	                else if (i == 2) System.out.print("\t" + String.format("%.2f", Double.parseDouble(columnValue)));
	                else System.out.print("\t" + columnValue);
	            }
	            System.out.println("");
	        }
			
		} catch (SQLException e) {
			System.out.println("Essa empresa nao forneceu produtos para esta festa.");
		}
	}

	private static boolean verifyEmpresa(String empresa) {
        ResultSet result = Database.runQuery("SELECT CNPJ FROM EMPRESA WHERE CNPJ = '" + empresa + "'");
        try {
            if (!result.isBeforeFirst() ) {
                System.out.println("Empresa inexistente.");
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
