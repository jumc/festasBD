import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Produtos {
	
    public static void MenuGerenciarProdutos(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Gerenciar Produtos ");
        System.out.println("1) Produtos comprados por empresa");
        System.out.println("2) Cadastrar Produto");
        System.out.println("3) Voltar");
        System.out.print("> ");
        
        int i = Keyboard.readInt();
        switch (i){
            case 1:
            	produtosCompradosPorEmpresa(festa);
            	MenuGerenciarProdutos(festa);
                break;
            case 2:
            	cadastrarProduto(festa);
            	MenuGerenciarProdutos(festa);
                break;
            default:
                break;
        }
    }
    
    private static void cadastrarProduto(Festa festa) {
    	String nome, fornecedor;
    	float custo;
    	int qtd, tipo = -1;
    	
    	System.out.println("Digite o nome do produto: ");
    	System.out.print("> ");
    	nome = Keyboard.readLine();
    	if (nome.length() > 32) nome = nome.substring(0, 32);
    	
    	System.out.println("Digite o CNPJ do fornecedor do produto (XXX.XXX.XXX-XX):");
    	System.out.print("> ");
    	fornecedor = Keyboard.readLine();
    	fornecedor = verifyCNPJ(fornecedor);
    	
    	if (!verifyContrato(fornecedor, festa)) return;
    	
    	System.out.println("Digite o custo do produto: ");
    	System.out.print("> ");
    	custo = Keyboard.readFloat();
    	
    	System.out.println("Digite a quantidade do produto: ");
    	System.out.print("> ");
    	qtd = Keyboard.readInt();
    	
    	while (tipo != 0 && tipo != 1) {
	    	System.out.println("Escolha o tipo do produto: ");
	    	System.out.println("1) Comida");
	    	System.out.println("2) Prenda");
	    	System.out.print("> ");
	    	tipo = Keyboard.readInt();
	    	tipo--;
    	}
    	
    	insertProduto(festa, nome, fornecedor, custo, qtd, tipo);
    }
    
    private static int insertProduto(Festa festa, String nome, String fornecedor, float custo, int qtd, int tipo) {
    	int id = 0;
    	
    	ResultSet result = Database.runQuery("SELECT MAX(ID_PRODUTO) FROM PRODUTO;");
    	try {
    		result.next();
			id = result.getInt(1) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	int r = Database.runUpdate("INSERT INTO PRODUTO" + 
				" VALUES ("+id+",UPPER('"+nome+"'),'"+fornecedor+"',UPPER('"+festa.getNome()+"'),"+festa.getEdicao()+","+custo+","+qtd+","+tipo+");");
    	
    	return r;
    }
    
    
    private static String verifyCNPJ(String empresa) {
    	String cnpj = empresa;
    	while (!cnpj.matches("[0-9]{3}+[.][0-9]{3}+[.][0-9]{3}+[-][0-9]{2}+")) {
        	System.out.println("Digite o CNPJ no formato (XXX.XXX.XXX-XX):");
        	System.out.print("> ");
        	cnpj = Keyboard.readLine();
    	}
    	
    	return cnpj;
    }
    
    private static boolean verifyContrato(String empresa, Festa festa) {
    	ResultSet result = Database.runQuery("SELECT EMPRESA" + 
				" FROM CONTRATO" + 
				" WHERE (EMPRESA = '" + empresa + "') AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
		try {
			if (!result.isBeforeFirst() ) {
			    System.out.println("Contrato nao encontrado para a empresa de CNPJ " + empresa + " na festa selecionada.");
			    return false;
			}
		} catch (Exception e) {
			System.out.println("Contrato nao encontrado para a empresa de CNPJ " + empresa + " na festa selecionada.");
			return false;
		}
		
		return true;
    }
   
    
	private static void produtosCompradosPorEmpresa(Festa festa) {
		String empresa;
		
		System.out.println("Digite o CNPJ da empresa (XXX.XXX.XXX-XX): ");
        System.out.print("> ");
        empresa = Keyboard.readLine();
        empresa = verifyCNPJ(empresa);
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
