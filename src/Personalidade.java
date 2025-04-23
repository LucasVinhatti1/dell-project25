public enum Personalidade {
    VISIONARIA("Visionária"),
    CONSERVADORA("Conservadora "),
    INOVADORA("Inovadora"),
    ENGENHEIRA("Engenheira");

    private final String descricao;

    Personalidade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
