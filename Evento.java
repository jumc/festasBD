import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Evento {
		private int edicao, tipo, consumacao;
		private String nome, playlist;
		private Festa festa;
		
		public Evento(Festa festa) {
			this.festa=festa;
		}

		private void getInfo(){
	        do{
	            System.out.println("Digite o nome da Festa: ");
	            System.out.print("> ");
	            nome = Keyboard.readLine();
	            System.out.println("Digite a edicao da Festa: ");
	            System.out.print("> ");
	            edicao = Keyboard.readInt();
	            } while(verifyFesta() == true);
	        System.out.println("Digite a consumacao da Festa: ");
	        System.out.print("> ");
	        consumacao = Keyboard.readInt();
	        System.out.println("Digite o tipo da Festa: ");
	        System.out.print("> ");
	        tipo = Keyboard.readInt();
	        System.out.println("Insira a playlist musical da Festa: ");
	        System.out.print("> ");
	        playlist = Keyboard.readLine();
	        
	    }
		
		  private boolean verify(ResultSet result){
		        try {
		            if (result.isBeforeFirst() ) {
		                return false;
		            }
		            return true;
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return false;
		        }
		    }
		
		private boolean verifyFesta(){
	        // Se a festa existe com a mesma edição retorna true
	        ResultSet result = Database.runQuery("SELECT * FROM FESTA WHERE NOME = UPPER('" + nome + "') AND EDICAO =" + edicao +";");
	        if(!verify(result) == false){
	            System.out.println("Festa nao cadastrada.");
	            return false;
	        }else
	        return true;
		}
		
		public void inserirEvento(){
	        
	            getInfo();
	            //verifica tipo
	            if(tipo==1)
	            {
	                int n = Database.runUpdate("INSERT INTO FESTA(NOME, EDICAO, CONSUMACAO, TIPO, PLAYLIST) VALUES (UPPER('" + nome + "'), " + edicao + ", " + consumacao + ", " + tipo + ",UPPER('" + playlist + "'));");
	                int t = Database.runUpdate("INSERT INTO FESTA_JUNINA(FESTA, EDICAO) VALUES (UPPER('" + nome + "'), " + edicao + ");");
	                	if (n == 1 && t == 1) {
	                		System.out.println("Festa junina inserida com sucesso.");
	                	}
	                	else {
	                			System.out.println("Falha ao inserir Festa junina.");
	                	}
	                
	            }
	            else if(tipo == 0)
	            {	
	            	int n = Database.runUpdate("INSERT INTO FESTA(NOME, EDICAO, CONSUMACAO, TIPO, PLAYLIST) VALUES (UPPER('" + nome + "'), " + edicao + ", " + consumacao + ", " + tipo + ",UPPER('" + playlist + "'));");
	                int t = Database.runUpdate("INSERT INTO HALLOWEEN(FESTA, EDICAO) VALUES (UPPER('" + nome + "'), " + edicao + ");");
	            	
	            		if (n == 1 && t == 1) {
	            			System.out.println("Festa halloween inserida com sucesso.");
	            		}
	            		else {
	            			System.out.println("Falha ao inserir Festa halloween.");
	            			}
	            }
	            else 
	            {
	            	System.out.println("O tipo de festa informado e invalido.");
	            }
	    }

		public void ExcluirEvento()
		{
			
        	System.out.println("Festas:");
    		System.out.println("TIPO\tEDICAO\tNOME");
    		ResultSet result = Database.runQuery("SELECT NOME, EDICAO, TIPO FROM FESTA");
    		try {
    			    			if (!result.isBeforeFirst() ) {
    			    System.out.println("Nenhuma festa encontrada.");
    			    return;
    			}
    	        ResultSetMetaData rsmd = result.getMetaData();
    	        int columnsNumber = rsmd.getColumnCount();
    	        
    	        		int contador=0;
    	        while (result.next()) {
    	            	contador++;
    	                
    	                if(result.getInt(3)==1)System.out.print("JUNINA");
    	                else System.out.print("HALLOW");
    	                System.out.print("\t" + (result.getInt(2)));
    	                System.out.print("\t" + result.getString(1));
    	        
    	                  	            
    	            System.out.println("");
    	        }
    	        
    			
    		} catch (SQLException e) {
    			System.out.println("Erro Festa nao localizada.");
    		}
    		System.out.println("Digite o nome da Festa a ser removida: ");
            System.out.print("> ");
            nome = Keyboard.readLine();
            System.out.println("Digite a edicao da Festa:" +nome+"");
            System.out.print("> ");
            edicao = Keyboard.readInt();
            int n = Database.runUpdate("DELETE FROM FESTA WHERE NOME=UPPER('"+nome+"') AND EDICAO ="+edicao+"");
            if (n!=0) {
    			System.out.println("Festa excluída com sucesso.");
    		}
    		else {
    		System.out.println("Falha ao excluir Festa.");
    		}
		}
		
}