import java.sql.ResultSet;
import java.sql.SQLException;

public class Barracas {
	
	public static void selecionarBarraca(Festa festa) {
		int id, tipo;
		
    	System.out.println("Digite o ID da barraca: ");
    	System.out.print("> ");
    	id = Keyboard.readInt();
    	tipo = verifyBarraca(festa, id);
    	if (tipo == -1) {
    		System.out.println("Barraca n�o encontrada!");
    		return;
    	}
    	
    	System.out.println("Barraca encontrada!");
    	
    	MenuSelecionarBarraca(festa, id, tipo);
	}
	
	private static void MenuSelecionarBarraca(Festa festa, int id, int tipo) {
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Gerenciar barracas >> Selecionar barraca");
        System.out.println("1) Inserir respons�vel por barraca");
        System.out.println("2) Inserir produto");
        switch (tipo) {
        	case 0:
                System.out.println("3) Cadastrar venda de comida");
                System.out.println("4) Voltar");
                System.out.print("> ");
                break;
        	case 1: default:
                System.out.println("3) Voltar");
                System.out.print("> ");
        }


        int i = Keyboard.readInt();
        i = (tipo != 0 && i == 3) ? ++i : i;	//se tipo nao for comida nao tem opcao de cadastrar comida
        switch (i){
            case 1:
            	inserirResponsavel(festa, id);
            	MenuSelecionarBarraca(festa, id, tipo);
                break;
            case 2:
            	Produtos.cadastrarProduto(festa, tipo);
            	MenuSelecionarBarraca(festa, id, tipo);
                break;
            case 3:
            	//cadastrar venda de comida
            	MenuSelecionarBarraca(festa, id, tipo);
                break;
            case 4:
            	break;
        }
	}
	
	public static void inserirResponsavel(Festa festa, int id_barraca) {
		String cpf;
		
    	System.out.println("Digite o CPF do responsável: ");
    	System.out.print("> ");
    	cpf = Keyboard.readLine();
    	verifyCPF(cpf);
    	if (!verifyValidCPF(festa, cpf, id_barraca)) {
    		return;
    	}

    	Database.runUpdate("INSERT INTO RESPONSAVEL_BARRACA" + 
				" VALUES ('"+cpf+"',"+id_barraca+");");
	}
	
    private static String verifyCPF(String n) {
    	String cpf = n;
    	while (!cpf.matches("[0-9]{3}+[.][0-9]{3}+[.][0-9]{3}+[-][0-9]{2}+")) {
        	System.out.println("Digite o CPF no formato (XXX.XXX.XXX-XX):");
        	System.out.print("> ");
        	cpf = Keyboard.readLine();
    	}
    	
    	return cpf;
    }
    
    private static boolean verifyValidCPF(Festa festa, String cpf, int id_barraca) {
    	
    	ResultSet result = Database.runQuery("SELECT M.CPF FROM MEMBRO_COMISSAO M, ORGANIZACAO O WHERE (M.CPF = '"+cpf+"') AND (M.CPF = O.MEMBRO) AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
    	try {
			if (result.isBeforeFirst()) {
				result = Database.runQuery("SELECT MEMBRO FROM RESPONSAVEL_BARRACA WHERE (MEMBRO = '"+cpf+"') AND (BARRACA = "+id_barraca+");");
				if (!result.isBeforeFirst()) {
					return true;
				} else {
					System.out.println("Organizador de CPF informado já responsável por esta barraca!");
				}
			} else {
	    		System.out.println("Nao foi encontrado organizador com o CPF informado na festa selecionada");
			}
		} catch (SQLException e) {
    		System.out.println("Nao foi encontrado organizador com o CPF informado na festa selecionada");
    		e.printStackTrace();
		}
    	
    	return false;
    }

	private static int verifyBarraca(Festa festa, int id) {
    	ResultSet result = Database.runQuery("SELECT TIPO FROM BARRACA WHERE (ID_BARRACA = "+id+") AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
    	try {
			if (result.isBeforeFirst() ) {
				result.next();
				return result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return -1;
	}
	
    public static void cadastrarBarraca(Festa festa) {
    	String nome, descricao;
    	int id, num = -1, tipo = -1;
    	
    	System.out.println("Digite o ID da barraca: ");
    	System.out.print("> ");
    	id = Keyboard.readInt();
    	if (!verifyValidID(id)) {
    		System.out.println("ID ja existente");
    		return;
    	}
    	
    	while (num < 0) {
	    	System.out.println("Digite o n�mero da barraca: ");
	    	System.out.print("> ");
	    	num = Keyboard.readInt();
	    	if (num < 0) {
	    		System.out.println("O n�mero da barraca nao pode ser negativo!");
	    	} else {
	    		if (!verifyValidNum(festa, num)) {
	    			System.out.println("N�mero da barraca ja existente na festa atual");
	    			return;
	    		}
	    	}
    	}
    	
    	System.out.println("Digite o nome da barraca: ");
    	System.out.print("> ");
    	nome = Keyboard.readLine();
    	if (nome.length() > 32) nome = nome.substring(0, 32);
    	
    	System.out.println("Digite a descricao da barraca: ");
    	System.out.print("> ");
    	descricao = Keyboard.readLine();
    	if (descricao.length() > 256) descricao = descricao.substring(0, 256);
    	
    	while (tipo != 0 && tipo != 1 && tipo != 2) {
	    	System.out.println("Escolha o tipo da barraca: ");
	    	System.out.println("1) Comida");
	    	System.out.println("2) Lazer");
	    	System.out.println("3) Recarga");
	    	System.out.print("> ");
	    	tipo = Keyboard.readInt();
	    	tipo--;
    	}
    	
    	insertBarraca(festa, id, num, nome.toUpperCase(), descricao.toUpperCase(), tipo);
    }
    
    private static int insertBarraca(Festa festa, int id, int num, String nome, String descricao, int tipo) {
    	float preco = -1;
    	int r = 0;
    	
    	r += Database.runUpdate("INSERT INTO BARRACA" + 
				" VALUES ("+id+","+num+",UPPER('"+festa.getNome()+"'),"+festa.getEdicao()+",'"+nome+"','"+descricao+"',"+tipo+");");
    	
    	if (tipo == 1) {
	    	while (preco < 0) {
		    	System.out.println("Digite o pre�o do lazer: ");
		    	System.out.print("> ");
		    	preco = Keyboard.readFloat();
		    	if (preco < 0) {
		    		System.out.println("O pre�o nao pode ser negativo!");
		    	}
	    	}
	    	r += Database.runUpdate("INSERT INTO LAZER" + 
					" VALUES ("+preco+","+id+");");
	    	
    	}

    	return r;
    }

    private static boolean verifyValidID(int id) {
    	ResultSet result = Database.runQuery("SELECT ID_BARRACA FROM BARRACA WHERE ID_BARRACA = "+id+";");
    	try {
			if (!result.isBeforeFirst() ) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }

    private static boolean verifyValidNum(Festa festa, int num) {
    	ResultSet result = Database.runQuery("SELECT ID_BARRACA FROM BARRACA WHERE (NUMERO = "+num+") AND (FESTA = UPPER('" + festa.getNome() + "')) AND (EDICAO = " + festa.getEdicao() + ");");
    	try {
			if (!result.isBeforeFirst() ) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
}
