import javax.swing.*;
import java.awt.*;
import java.util.*;

public class StartupRushGUI extends JFrame {
    private java.util.List <Startup> startups = new ArrayList<>();
    private java.util.List <Batalha> batalhas = new ArrayList<>();
    private JTextArea outputArea;
    private JButton cadastrarBtn, iniciarBtn, batalharBtn, relatorioBtn;
    private JPanel painelBotoes;
    private int rodadaAtual = 1;

    public StartupRushGUI() {
        super("Startup Rush");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        painelBotoes = new JPanel();
        cadastrarBtn = new JButton("Cadastrar Startups");
        iniciarBtn = new JButton("Iniciar Torneio");
        batalharBtn = new JButton("Administrar Batalhas");
        relatorioBtn = new JButton("Ver Relatório");

        painelBotoes.add(cadastrarBtn);
        painelBotoes.add(iniciarBtn);
        painelBotoes.add(batalharBtn);
        painelBotoes.add(relatorioBtn);

        add(painelBotoes, BorderLayout.SOUTH);

        cadastrarBtn.addActionListener(e -> cadastrarStartups());
        iniciarBtn.addActionListener(e -> iniciarTorneio());
        batalharBtn.addActionListener(e -> administrarBatalha());
        relatorioBtn.addActionListener(e -> mostrarRelatorio());

        setVisible(true);

        exibirRegras();
    }

    private void cadastrarStartups() {
        startups.clear();
        int qtd;
        do {
            String input = JOptionPane.showInputDialog("Quantas startups deseja cadastrar? (4,6 ou 8?)");
            if (input == null) return;
            try {
                qtd = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido. Digite 4,6 ou 8");
                return;
            }
        } while (qtd < 4|| qtd > 8 || qtd % 2 != 0);

        for (int i = 0; i < qtd;i++) {

            String nome;
            do {
                nome = JOptionPane.showInputDialog("Nome da startup " + (i + 1));
                if (nome == null || nome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Este campo não pode estar vazio.");
                }
            } while (nome == null || nome.trim().isEmpty());

            String slogan;
            do {
                slogan = JOptionPane.showInputDialog("Slogan da startup " + (i + 1));
                if (slogan == null || slogan.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Este campo não pode estar vazio.");
                }
            } while (slogan == null || slogan.trim().isEmpty());

            int ano;
            try {
                ano = Integer.parseInt(JOptionPane.showInputDialog("Ano de fundação da startup " + (i + 1)));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ano inválido, vamos usar 2025 como padrão.");
                ano = 2025;
            }

            Object[] opcoes = Personalidade.values();
            Personalidade p = (Personalidade) JOptionPane.showInputDialog(
                    null, "Escolha a personalidade:", "Personalidade",
                    JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            startups.add(new Startup(nome, slogan, ano, p));
        }
        outputArea.append("\nStartups cadastradas com sucesso!\n");
    }

    private void iniciarTorneio() {
        if (startups.size() < 4 || startups.size() % 2 != 0) {
            outputArea.append("\nNúmero inválido de startups. Cadastre entre 4,6 ou 8 startups.\n");
            return;
        }
        novaRodada();
    }

    private void novaRodada() {
        outputArea.append("\n--- Rodada " + rodadaAtual + " ---\n");
        batalhas.clear();
        Collections.shuffle(startups);
        for (int i = 0; i < startups.size(); i += 2) {
            batalhas.add(new Batalha(startups.get(i), startups.get(i + 1)));
        }
        rodadaAtual++;
    }

    private void administrarBatalha() {
        for (Batalha bat : batalhas) {
            if (!bat.isFinalizada()) {
                Startup s1 = bat.getS1();
                Startup s2 = bat.getS2();

                aplicarEventos(s1);
                aplicarEventos(s2);

                Startup vencedora = bat.processarResultado();
                outputArea.append("\nBatalha: " + bat + "\nVencedora: " + vencedora.getNome() + "\n");
            }
        }

        if (batalhas.stream().allMatch(Batalha::isFinalizada)) {
            startups = new ArrayList<>();
            for (Batalha b : batalhas) {
                if (b.isFinalizada()) {
                    Startup vencedora = b.getS1().getPontuacao() > b.getS2().getPontuacao() ? b.getS1() : b.getS2();
                    startups.add(vencedora);
                }
            }
            if (startups.size() > 1) novaRodada();
            else mostrarCampea();
        }
    }

    private void aplicarEventos(Startup s) {
        Set<Evento> eventosAplicados = new HashSet<>();
        for (Evento evento : Evento.values()) {
            if (eventosAplicados.contains(evento)) continue;

            int opcao = JOptionPane.showConfirmDialog(
                    null,
                    "A startup " + s.getNome() + " teve o evento: " + evento.getDescricao() + "?",
                    "Evento",
                    JOptionPane.YES_NO_OPTION);

            if (opcao == JOptionPane.YES_OPTION) {
                s.aplicarEvento(evento);
                eventosAplicados.add(evento);
            }
        }
    }

    private void mostrarCampea() {
        Startup campea = startups.get(0);
        JOptionPane.showMessageDialog(this, "A campeã do torneio STARTUP RUSH foi: " + campea.getNome() +
                "\nSlogan: \"" + campea.getSlogan() + "\"", "Campeã", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarRelatorio() {
        startups.sort(Comparator.comparingInt(Startup::getPontuacao).reversed());
        StringBuilder sb = new StringBuilder("\n--- Relatório Final ---\n");
        for (Startup s : startups) {
            sb.append(s.toString()).append("\n");
            for (Map.Entry<Evento, Integer> entry : s.getEstatisticas().entrySet()) {
                sb.append(" - ").append(entry.getKey().getDescricao())
                        .append(": ").append(entry.getValue()).append("x\n");
            }
            sb.append("----------------------------\n");
        }
        outputArea.append(sb.toString());
    }

    private void exibirRegras() {
        String regras = "REGRAS DO TORNEIO STARTUP RUSH:\n"
                + "- Devem ser 4,6 ou 8 startups.\n"
                + "- Cada startup começa com 70 pontos.\n"
                + "- Eventos positivos e negativos podem ocorrer em cada batalha.\n"
                + "- Eventos só podem ser aplicados uma vez por startup por rodada.\n"
                + "- A vencedora da batalha ganha 30 pontos.\n"
                + "- Em caso de empate, ocorre uma Shark Fight (+2 pts aleatórios).\n"
                + "- O torneio continua até restar apenas uma campeã.\n";

        JOptionPane.showMessageDialog(this, regras, "Regras do Startup Rush", JOptionPane.INFORMATION_MESSAGE);
    }
}
