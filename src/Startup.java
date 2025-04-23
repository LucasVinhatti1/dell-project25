import java.util.EnumMap;

public class Startup {
    private String nome;
    private String slogan;
    private int anoFund;
    private int pontuacao;
    private Personalidade persona;
    private EnumMap<Evento, Integer> estatisticas;

    public Startup(String nome, String slogan, int anoFund, Personalidade persona) {
        this.nome = nome;
        this.slogan = slogan;
        this.anoFund = anoFund;
        this.persona = persona;
        this.pontuacao = 70;
        this.estatisticas = new EnumMap <> (Evento.class);
        for (Evento e : Evento.values()) {
            estatisticas.put(e, 0);
        }
    }

    public String getNome() {
        return nome;
    }

    public String getSlogan() {
        return slogan;
    }

    public int getAnoFundacao() {
        return anoFund;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public Personalidade getPersonalidade() {
        return persona;
    }

    public EnumMap<Evento, Integer> getEstatisticas() {
        return estatisticas;
    }

    public void aplicarEvento(Evento evento) {
        int impacto = evento.getImpacto();

        if (evento == Evento.PITCH_CONVINCENTE && persona == Personalidade.VISIONARIA) impacto += 2;
        if (evento == Evento.BOA_TRACAO && persona == Personalidade.INOVADORA) impacto += 1;
        if (evento == Evento.PRODUTO_COM_BUGS && persona == Personalidade.ENGENHEIRA) impacto -= 1;

        pontuacao += impacto;
        estatisticas.put(evento, estatisticas.get (evento) + 1);
    }

    public void adicionarPontos(int pontos) {
        pontuacao += pontos;
    }

    @Override
    public String toString() {
        return nome + " (" + pontuacao + " pts, Fundada em " + anoFund + ", " + persona + ")";
    }
}
