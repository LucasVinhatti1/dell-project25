public class Batalha {
    private final Startup s1;
    private final Startup s2;
    private boolean finalizada = false;

    public Batalha(Startup s1, Startup s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Startup getS1() {
        return s1;
    }

    public Startup getS2() {
        return s2;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Startup processarResultado() {
        int p1 = s1.getPontuacao();
        int p2 = s2.getPontuacao();

        if (p1 == p2) {

            if (Math.random() < 0.5) {
                s1.adicionarPontos(2);
            } else {
                s2.adicionarPontos(2);
            }
        }

        Startup vencedora =(s1.getPontuacao() > s2.getPontuacao()) ? s1 : s2;
        vencedora.adicionarPontos(30);
        finalizada= true;
        return vencedora;
    }

    @Override
    public String toString() {
        return s1.getNome() + " vs " +s2.getNome();
    }
}
