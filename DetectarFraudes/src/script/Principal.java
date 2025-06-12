package script;

import SQL.BancoDeDados;
import java.sql.SQLException;

public class Principal {
    public static void main(String[] args) {
        try {
            BancoDeDados banco = new BancoDeDados();
            ListaTransacao transacoes = banco.carregarTransacoesLigada();

            DetectorDeFraude detector = new DetectorDeFraude();
            Lista idsSuspeitos = detector.detectarSuspeitas(transacoes);

            Node atual = idsSuspeitos.getFirst();
            int total = 0;

            while (atual != null) {
                int id = atual.getData();
                Transacao t = banco.buscarTransacaoPorId(id);
                if (t != null) {
                    TransacaoSuspeita suspeita = new TransacaoSuspeita(t, true);
                    banco.inserirFraudeIndividual(suspeita);
                    total++;
                }
                atual = atual.getNext();
            }

            System.out.println("Total de transações suspeitas detectadas: " + total);

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}