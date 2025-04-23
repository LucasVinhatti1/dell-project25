public enum Evento {
    PITCH_CONVINCENTE("Pitch convincente", 6),
    PRODUTO_COM_BUGS("Produto com bugs", -4),
    BOA_TRACAO("Boa tração de usuários", 3),
    INVESTIDOR_IRRITADO("Investidor irritado", -6),
    FAKE_NEWS("Fake news no pitch", -8);

    private final String descricao;

    private final int impacto;

    Evento(String descricao, int impacto) {

        this.descricao = descricao;
        this.impacto = impacto;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getImpacto() {

        return impacto;
    }
}
