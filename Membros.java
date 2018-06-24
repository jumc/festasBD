import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Membros {
	private String cpf_aux,cpf, email, nome, telefone;
	private Festa festa;
	int campo;
	
	public void Membros(Festa festa) {
		this.festa=festa;
		
	}
	public void getInfo()
	{
			do {
		 	System.out.println("Digite o CPF: ");
            System.out.print("> ");
            cpf = Keyboard.readLine();
			}while(verifyMembro()==true);
			System.out.println("Digite o Email: ");
            System.out.print("> ");
            email = Keyboard.readLine();
            System.out.println("Digite o Nome: ");
            System.out.print("> ");
            nome = Keyboard.readLine();
            System.out.println("Digite o Telefone: ");
            System.out.print("> ");
            telefone = Keyboard.readLine();
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
	
	private boolean verifyMembro(){
     // Se a festa existe com a mesma edição retorna true
     ResultSet result = Database.runQuery("SELECT * FROM MEMBRO_COMISSAO WHERE CPF='"+cpf+"';");
     if(!verify(result) == false){
         System.out.println("Membro nao cadastrado.");
         return false;
     }else
     return true;
	}
	
	public void inserirMembro()
	{
		
	        
            getInfo();
        
            
                int n = Database.runUpdate("INSERT INTO MEMBRO_COMISSAO(CPF, EMAIL, NOME, TELEFONE) VALUES ('"+cpf+"',UPPER('"+email+"'),UPPER('" + nome + "'),'"+telefone+"');");
                       if (n == 1) {
                		System.out.println("Membro inserido com sucesso.");
                	}
                	else {
                			System.out.println("Falha ao inserir Membro.");
                	}

	}
	
	public void listarMembros()
	{
		System.out.println("Pessoas:");
		
		System.out.println("Nº\tCPF\t\tEMAIL\t\t\tNOME\t\t\tTELEFONE");
		ResultSet result = Database.runQuery("SELECT CPF, EMAIL, NOME,TELEFONE FROM MEMBRO_COMISSAO");
		try {
			    			if (!result.isBeforeFirst() ) {
			    System.out.println("Nenhum membro registrado.");
			    return;
			}
	        	        
	        		int contador=1;
	        while (result.next()) {
	            	
	            	System.out.print(contador);
	            	System.out.print("\t"+result.getString(1));
	                System.out.print("\t"+result.getString(2));
	                System.out.print("\t"+result.getString(3));
	                System.out.print("\t\t"+result.getString(4));
	                contador++;
	                  	            
	            System.out.println("");
	        }
	        
			
		} catch (SQLException e) {
			System.out.println("Erro membros nao localizados.");
		}
		
	}
	
	public void excluirMembro()
	{
		listarMembros();
		System.out.println("Digite o CPF da pessoa que deseja excluir: ");
        System.out.print("> ");
        cpf = Keyboard.readLine();
		int n = Database.runUpdate("DELETE FROM MEMBRO_COMISSAO WHERE CPF='"+cpf+"'");
		if (n!=0) {
			System.out.println("Pessoa excluída com sucesso.");
			
			}
		else {
			System.out.println("Falha ao excluir Pessoa.");
			}
	}
	
	public void alterarMembro() 
	{
		listarMembros();
		do {
		System.out.println("Digite o CPF da pessoa que deseja alterar: ");
		System.out.print("> ");
        cpf = Keyboard.readLine();
		}while(verifyMembro()!=true);
        System.out.println("Selecione o campo que deseja alterar: ");
        System.out.println("1) CPF");
        System.out.println("2) EMAIL");
        System.out.println("3) NOME");
        System.out.println("4) TELEFONE");
        System.out.println("5) Voltar");
        System.out.print("> "); 
        campo = Keyboard.readInt();
        int n;
        switch (campo){
        case 1:
        	System.out.println("Informe o novo CPF: ");
        	System.out.print("> ");
        	cpf_aux = Keyboard.readLine();
        	n = Database.runUpdate("UPDATE MEMBRO_COMISSAO SET CPF=UPPER('"+cpf_aux+"') WHERE CPF='"+cpf+"';");
			if (n!=0) 
				System.out.println("CPF editado com sucesso.");
			else 
				System.out.println("Falha ao editar CPF.");
				
            break;
        case 2:
        	System.out.println("Informe o novo Email: ");
        	System.out.print("> ");
        	email = Keyboard.readLine();
        	n = Database.runUpdate("UPDATE MEMBRO_COMISSAO SET EMAIL=UPPER('"+email+"') WHERE cpf='"+cpf+"';");
			if (n!=0) 
				System.out.println("Email editado com sucesso.");
			else 
				System.out.println("Falha ao editar Email.");
				
            break;
        case 3:
        	System.out.println("Informe o novo Nome: ");
        	System.out.print("> ");
        	nome = Keyboard.readLine();
        	n = Database.runUpdate("UPDATE MEMBRO_COMISSAO SET NOME=UPPER('"+nome+"') WHERE cpf='"+cpf+"';");
			if (n!=0) 
				System.out.println("Nome editado com sucesso.");
			else 
				System.out.println("Falha ao editar Nome.");
				
            break;
        case 4:
        	System.out.println("Informe o novo Telefone: ");
        	System.out.print("> ");
        	telefone = Keyboard.readLine();
        	n = Database.runUpdate("UPDATE MEMBRO_COMISSAO SET TELEFONE=UPPER('"+telefone+"') WHERE cpf='"+cpf+"';");
			if (n!=0) 
				System.out.println("Telefone editado com sucesso.");
			else 
				System.out.println("Falha ao editar Telefone.");
				
            break;
            
        case 5:
        	
        	break;
        }
	}
	
}
