

public class Menu {

    public static void MenuPrincipal(){
        System.out.print("\n" +
                "    _                         _           _       ___       _           \n" +
                "   /_\\  __ ___ ______ ___ _ _(_)__ _   __| |___  | __|__ __| |_ __ _ ___\n" +
                "  / _ \\/ _/ -_|_-<_-</ _ \\ '_| / _` | / _` / -_) | _/ -_|_-<  _/ _` (_-<\n" +
                " /_/ \\_\\__\\___/__/__/\\___/_| |_\\__,_| \\__,_\\___| |_|\\___/__/\\__\\__,_/__/\n" +
                "                                                                        \n");
        System.out.println("SELECIONE O QUE DESEJA FAZER APERTANDO O NUMERO CORRESPONDENTE \n");
        System.out.println("1) Gerenciar festas");
        System.out.println("2) Gerenciar pessoas");
        System.out.println("3) Estatísticas finaceiras");
        System.out.println("4) Sair");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                MenuFesta();
                break;
            case 2:
                MenuPessoas();
                break;
            case 3:
                MenuBalanco();
                break;
            case 4:
            	System.exit(0);
        }
    }

    public static void MenuFesta(){
        System.out.println("\n>> Gerenciar festas ");
        System.out.println("1) Selecionar festa");
        System.out.println("2) Criar festa");
        System.out.println("3) Voltar");
        System.out.print("> ");
        
        Festa festa = new Festa();
        
        int i = Keyboard.readInt();
        switch (i){
            case 1:
                
                Evento.selecionarFesta(festa);
                MenuFestaSelecionada(festa);
                break;
            case 2:
            	Evento evento = new Evento(festa);
                evento.inserirEvento();
                MenuFesta();
                break;
            case 3:
                MenuPrincipal();
                break;
        }
    }

    public static void MenuFestaSelecionada(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa");
        System.out.println("1) Alterar festa");
        System.out.println("2) Remover festa");
        System.out.println("3) Inserir membro da organizacao");
        System.out.println("4) Inserir aluguel");
        System.out.println("5) Equipamentos emprestados");
        System.out.println("6) Gerenciar produtos");
        System.out.println("7) Recargas");
        System.out.println("8) Gerenciar concursos");
        System.out.println("9) Gerenciar barracas");
        System.out.println("10) Voltar");
        System.out.print("> ");

        Evento evento = new Evento(festa);
        int i = Keyboard.readInt();
        switch (i){
            case 1:
            	evento.AlterarEvento(festa);
            	MenuFestaSelecionada(festa);
                break;
            case 2:
                if (evento.ExcluirEvento(festa) > 0)
                	System.out.println("Festa excluida!");
                else
                	System.out.println("Nao foi possivel excluir a festa!");
                MenuFesta();
                break;
            case 3:
            	Organizacao org = new Organizacao(festa);
            	org.inserirMembroOrganizacao();
            	MenuFestaSelecionada(festa);
                break;
            case 4:
                Aluguel aluguel = new Aluguel(festa);
                aluguel.inserirAluguel();
                MenuFestaSelecionada(festa);
                break;
            case 5:
                MenuEmprestimos(festa);
                MenuFestaSelecionada(festa);
                break;
            case 6:
            	Produtos.MenuGerenciarProdutos(festa);
            	MenuFestaSelecionada(festa);
                break;
            case 7:
                MenuRecarga(festa);
                break;
            case 8:
                MenuConcursos(festa);
                MenuFestaSelecionada(festa);
                break;
            case 9:
            	MenuGerenciarBarracas(festa);
            	MenuFestaSelecionada(festa);
            	break;
            case 10:
                MenuFesta();
                break;
        }
}

    public static void MenuEmprestimos(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Equipamentos emprestados");
        System.out.println("1) Visualizar emprestimos");
        System.out.println("2) Cadastrar emprestimos");
        System.out.println("3) Voltar");
        System.out.print("> ");

        Emprestimos empr = new Emprestimos(festa);

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                empr.visualizaEmprestimos();
                MenuFestaSelecionada(festa);
                break;
            case 2:
                empr.inserirEmprestimo();
                MenuFestaSelecionada(festa);
                break;
            case 3:
                MenuFestaSelecionada(festa);
                break;
        }
    }

    public static void MenuBalanco(){
        System.out.println("\n>> Estatisticas financeiras");
        System.out.println("1) Saldo final");
        System.out.println("2) Despesas por tipo de servico");
        System.out.println("3) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                Balanco.saldoFinal();
                MenuBalanco();
                break;
            case 2:
                Balanco.despesasPorCategoria();
                MenuBalanco();
                break;
            case 3:
            	MenuPrincipal();
                break;
            default:
                MenuBalanco();
                break;
        }
    }

    public static void MenuRecarga(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Recargas");
        System.out.println("1) Inserir recarga");
        System.out.println("2) Recarga m�dia da festa");
        System.out.println("3) Recarga m�dia para o seu tipo de festa (" +
        		(festa.getTipo() == 0 ? "Halloween)" : "Junina)") );
        System.out.println("4) Volume de recargas da sua festa, por faixa de tempo");
        System.out.println("5) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
        	case 1:
        		Recargas.inserirRecarga(festa);
        		MenuRecarga(festa);
        		break;
            case 2:
                Recargas.recargaMedia(festa);
                MenuRecarga(festa);
                break;
            case 3:
                Recargas.recargaMediaTipo(festa.getTipo());
                MenuRecarga(festa);
                break;
            case 4:
                Recargas.recargaPorTempo(festa);
                MenuRecarga(festa);
                break;
            case 5:
                MenuFestaSelecionada(festa);
                break;
        }
    }

    public static void MenuConcursos(Festa festa){
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Gerenciar concursos");
        System.out.println("1) Cadastrar coordenador de concurso de adivinhacao");
        System.out.println("2) Cadastrar coordenador de concurso de quem come mais");
        System.out.println("3) Cadastrar coordenador de concurso de fantasia");
        System.out.println("4) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
                Concurso conc1 = new Concurso(festa, "ADV");
                conc1.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 2:
                Concurso conc2 = new Concurso(festa, "COMIDA");
                conc2.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 3:
                Concurso conc3 = new Concurso(festa, "FANTASIA");
                conc3.inserirCoordenador();
                MenuConcursos(festa);
                break;
            case 4:
                MenuFestaSelecionada(festa);
        }
    }
    
    public static void MenuGerenciarBarracas(Festa festa) {
        System.out.println("\n>> Gerenciar festas >> Selecionar festa >> Gerenciar barracas");
        System.out.println("1) Cadastrar barraca");
        System.out.println("2) Selecionar barraca");
        System.out.println("3) Voltar");
        System.out.print("> ");

        int i = Keyboard.readInt();
        switch (i){
            case 1:
            	Barracas.cadastrarBarraca(festa);
                MenuGerenciarBarracas(festa);
                break;
            case 2:
            	Barracas.selecionarBarraca(festa);
                MenuGerenciarBarracas(festa);
                break;
            case 3:
                break;
        }
    }

    public static void MenuPessoas(){
        System.out.println("\n>> Gerenciar pessoas");
        System.out.println("1) Cadastrar pessoa");
        System.out.println("2) Listar pessoas");
        System.out.println("3) Excluir pessoa");
        System.out.println("4) Alterar pessoa");
        System.out.println("5) Voltar");
        System.out.print("> ");

        Membros mem = new Membros();
        int i = Keyboard.readInt();
        switch (i){
            case 1:
            	mem.inserirMembro();
            	MenuPessoas();
                break;
            case 2:
                mem.listarMembros();
                MenuPessoas();
                break;
            case 3:
            	mem.excluirMembro();
            	MenuPessoas();
                break;
            case 4:
            	mem.alterarMembro();
            	MenuPessoas();
                break;
            case 5:
            	MenuPrincipal();
            	break;
        }
    }

    
}
