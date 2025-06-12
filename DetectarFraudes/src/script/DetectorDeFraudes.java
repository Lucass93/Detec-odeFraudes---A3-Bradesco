package script;

public class DetectorDeFraudes {

        // lista ligada para armazenar transações suspeitas
        private Lista listaSuspeitas;

        public DetectorDeFraude() {
            this.listaSuspeitas = new Lista(); // inicializa a lista ligada
        }

        public Lista detectarSuspeitas(List<Transacao> transacoes) {
            float limiteRazaoPreco = 4.0f;
            float limiteDistanciaCasa = 100.0f;
            float limiteUltimaTrans = 30.0f;
            float limitePrecoExtremo = 20.0f;

            for (Transacao t : transacoes) {
                boolean eFraude = false;

                // condições para fraude
                if (!t.isUsouPin() && t.isPedidoOnline()) {
                    int condicoesAdicionaisSuspeitas = 0;
                    if (t.getRazaoPrecoMedioCompra() > limiteRazaoPreco) {
                        condicoesAdicionaisSuspeitas++;
                    }
                    if (t.getDistanciaDeCasa() > limiteDistanciaCasa) {
                        condicoesAdicionaisSuspeitas++;
                    }
                    if (t.getDistanciaUltimaTransacao() > limiteUltimaTrans) {
                        condicoesAdicionaisSuspeitas++;
                    }
                    if (!t.isUsouChip()) {
                        condicoesAdicionaisSuspeitas++;
                    }
                    if (condicoesAdicionaisSuspeitas >= 2) {
                        eFraude = true;
                    }
                } else if (t.getRazaoPrecoMedioCompra() > limitePrecoExtremo) {
                    eFraude = true;
                } else if (t.getDistanciaDeCasa() > 1000.0f) {
                    eFraude = true;
                } else if (t.getDistanciaUltimaTransacao() > 500.0f) {
                    eFraude = true;
                }

                if (eFraude) {
                    // Adiciona a transação suspeita na lista ligada
                    listaSuspeitas.insertFim(t.getId()); // Supondo que Transacao tenha um metodo getId()
                }
            }
            return listaSuspeitas; // Retorna a lista de transações suspeitas
        }

        public Lista getListaSuspeitas() {
            return listaSuspeitas; //Metodo para acessar a lista de suspeitas
        }
    }
}
