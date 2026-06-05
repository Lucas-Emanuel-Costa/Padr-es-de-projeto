package br.com.mathquest.gui;

import br.com.mathquest.factory.FabricaFase;
import br.com.mathquest.factory.FabricaFaseMatematica;
import br.com.mathquest.model.Fase;
import br.com.mathquest.model.Jogador;
import br.com.mathquest.model.Pergunta;
import br.com.mathquest.strategy.Divisao;
import br.com.mathquest.strategy.Multiplicacao;
import br.com.mathquest.strategy.OperacaoMatematica;
import br.com.mathquest.strategy.Soma;
import br.com.mathquest.strategy.Subtracao;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MathQuestFrame extends JFrame {

    private static final String TELA_INICIO = "inicio";
    private static final String TELA_JOGO = "jogo";
    private static final String TELA_FINAL = "final";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel conteudo = new JPanel(cardLayout);
    private final Random random = new Random();

    private JTextField nomeField;
    private JComboBox<String> operacaoBox;
    private JLabel faseLabel;
    private JLabel progressoLabel;
    private JLabel pontuacaoLabel;
    private JLabel narradorLabel;
    private JLabel perguntaLabel;
    private JLabel mensagemLabel;
    private JLabel rivalLabel;
    private JTextField respostaField;
    private JButton responderButton;
    private JLabel finalTituloLabel;
    private JLabel finalMensagemLabel;
    private PersonagemPanel personagemPanel;

    private Jogador jogador;
    private OperacaoMatematica operacao;
    private FabricaFase fabricaFase;
    private Fase faseAtual;
    private Pergunta perguntaAtual;
    private int nivelAtual;
    private int perguntaDaFase;
    private int acertosNaFase;
    private boolean jogoFinalizado;

    private final String[] mensagensInicioFase = {
            "Nova fase! Respire fundo e mostre seu poder matematico.",
            "A trilha continua. Cada resposta certa deixa o objetivo mais perto.",
            "Prepare-se: o rival esta observando cada calculo.",
            "Mais uma etapa da jornada. Foque na conta e avance."
    };

    private final String[] mensagensAcerto = {
            "Muito bem! Voce esta pegando ritmo.",
            "Excelente! O caminho ate o objetivo ficou menor.",
            "Boa! O rival sentiu esse acerto.",
            "Mandou bem! Continue assim e o chefe vai tremer."
    };

    private final String[] mensagensErro = {
            "Calma, ainda da para virar. Observe a resposta e siga em frente.",
            "Quase! O importante e continuar pensando.",
            "Essa escapou, mas a aventura ainda nao acabou.",
            "Respire e tente a proxima com atencao."
    };

    private final String[] falasRivalNeutro = {
            "Vamos ver se voce sabe mesmo...",
            "Essa conta parece facil. Sera?",
            "Estou de olho nos seus acertos.",
            "Um erro e eu ganho terreno."
    };

    private final String[] falasRivalAcerto = {
            "Nao acredito... voce acertou!",
            "Ok, essa foi boa. Mas ainda nao acabou.",
            "Voce esta ficando perigoso.",
            "Hmph. Na proxima eu te pego."
    };

    private final String[] falasRivalErro = {
            "Ha! Eu sabia que voce ia escorregar.",
            "Essa foi minha chance de avancar.",
            "Obrigado pelo ponto perdido.",
            "Cuidado, o chefe adora esses erros."
    };

    public MathQuestFrame() {
        setTitle("Math Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);

        conteudo.add(criarTelaInicio(), TELA_INICIO);
        conteudo.add(criarTelaJogo(), TELA_JOGO);
        conteudo.add(criarTelaFinal(), TELA_FINAL);

        setContentPane(conteudo);
        cardLayout.show(conteudo, TELA_INICIO);
    }

    private JPanel criarTelaInicio() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(245, 248, 252));
        painel.setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("MATH QUEST", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 44));
        titulo.setForeground(new Color(28, 45, 74));
        painel.add(titulo, gbc);

        JLabel subtitulo = new JLabel("Resolva contas, avance fases e derrote o chefe final.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(new Color(75, 87, 103));
        painel.add(subtitulo, gbc);

        StickHeroPanel desenho = new StickHeroPanel();
        desenho.setPreferredSize(new Dimension(520, 180));
        painel.add(desenho, gbc);

        nomeField = new JTextField();
        nomeField.setFont(new Font("Arial", Font.PLAIN, 18));
        nomeField.setBorder(BorderFactory.createTitledBorder("Nome do jogador"));
        painel.add(nomeField, gbc);

        operacaoBox = new JComboBox<>(new String[]{
                "Mundo da Soma",
                "Mundo da Subtracao",
                "Mundo da Multiplicacao",
                "Mundo da Divisao"
        });
        operacaoBox.setFont(new Font("Arial", Font.PLAIN, 18));
        painel.add(operacaoBox, gbc);

        JButton iniciarButton = criarBotao("Comecar aventura");
        iniciarButton.addActionListener(this::iniciarJogo);
        painel.add(iniciarButton, gbc);

        return painel;
    }

    private JPanel criarTelaJogo() {
        JPanel raiz = new JPanel(new BorderLayout(24, 18));
        raiz.setBackground(new Color(245, 248, 252));
        raiz.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JPanel topo = new JPanel(new GridBagLayout());
        topo.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 16);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        faseLabel = criarInfoLabel();
        progressoLabel = criarInfoLabel();
        pontuacaoLabel = criarInfoLabel();

        gbc.gridx = 0;
        gbc.weightx = 1;
        topo.add(faseLabel, gbc);
        gbc.gridx = 1;
        topo.add(progressoLabel, gbc);
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        topo.add(pontuacaoLabel, gbc);
        raiz.add(topo, BorderLayout.NORTH);

        personagemPanel = new PersonagemPanel();
        personagemPanel.setPreferredSize(new Dimension(900, 280));
        raiz.add(personagemPanel, BorderLayout.CENTER);

        JPanel areaPergunta = new JPanel(new GridBagLayout());
        areaPergunta.setOpaque(false);
        GridBagConstraints perguntaGbc = new GridBagConstraints();
        perguntaGbc.gridx = 0;
        perguntaGbc.fill = GridBagConstraints.HORIZONTAL;
        perguntaGbc.insets = new Insets(6, 0, 6, 0);
        perguntaGbc.weightx = 1;

        narradorLabel = new JLabel("", SwingConstants.CENTER);
        narradorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        narradorLabel.setForeground(new Color(44, 111, 187));
        areaPergunta.add(narradorLabel, perguntaGbc);

        perguntaLabel = new JLabel("", SwingConstants.CENTER);
        perguntaLabel.setFont(new Font("Arial", Font.BOLD, 40));
        perguntaLabel.setForeground(new Color(28, 45, 74));
        areaPergunta.add(perguntaLabel, perguntaGbc);

        respostaField = new JTextField();
        respostaField.setFont(new Font("Arial", Font.BOLD, 24));
        respostaField.setHorizontalAlignment(SwingConstants.CENTER);
        respostaField.setBorder(BorderFactory.createTitledBorder("Sua resposta"));
        respostaField.addActionListener(this::responder);
        areaPergunta.add(respostaField, perguntaGbc);

        responderButton = criarBotao("Responder");
        responderButton.addActionListener(this::responder);
        areaPergunta.add(responderButton, perguntaGbc);

        mensagemLabel = new JLabel(" ", SwingConstants.CENTER);
        mensagemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mensagemLabel.setForeground(new Color(75, 87, 103));
        areaPergunta.add(mensagemLabel, perguntaGbc);

        rivalLabel = new JLabel("", SwingConstants.CENTER);
        rivalLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        rivalLabel.setForeground(new Color(154, 55, 65));
        areaPergunta.add(rivalLabel, perguntaGbc);

        raiz.add(areaPergunta, BorderLayout.SOUTH);
        return raiz;
    }

    private JPanel criarTelaFinal() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(245, 248, 252));
        painel.setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 0, 12, 0);

        finalTituloLabel = new JLabel("", SwingConstants.CENTER);
        finalTituloLabel.setFont(new Font("Arial", Font.BOLD, 40));
        finalTituloLabel.setForeground(new Color(28, 45, 74));
        painel.add(finalTituloLabel, gbc);

        finalMensagemLabel = new JLabel("", SwingConstants.CENTER);
        finalMensagemLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        finalMensagemLabel.setForeground(new Color(75, 87, 103));
        painel.add(finalMensagemLabel, gbc);

        StickHeroPanel desenho = new StickHeroPanel();
        desenho.setPreferredSize(new Dimension(520, 210));
        painel.add(desenho, gbc);

        JButton jogarNovamenteButton = criarBotao("Jogar novamente");
        jogarNovamenteButton.addActionListener(event -> cardLayout.show(conteudo, TELA_INICIO));
        painel.add(jogarNovamenteButton, gbc);

        return painel;
    }

    private void iniciarJogo(ActionEvent event) {
        String nome = nomeField.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do jogador.");
            nomeField.requestFocus();
            return;
        }

        jogador = new Jogador(nome);
        operacao = criarOperacaoSelecionada();
        fabricaFase = new FabricaFaseMatematica(operacao);
        nivelAtual = 1;
        jogoFinalizado = false;

        iniciarFase(fabricaFase.criarFaseNormal(nivelAtual));
        cardLayout.show(conteudo, TELA_JOGO);
    }

    private OperacaoMatematica criarOperacaoSelecionada() {
        int indice = operacaoBox.getSelectedIndex();

        switch (indice) {
            case 0:
                return new Soma();
            case 1:
                return new Subtracao();
            case 2:
                return new Multiplicacao();
            case 3:
                return new Divisao();
            default:
                return new Soma();
        }
    }

    private void iniciarFase(Fase fase) {
        faseAtual = fase;
        perguntaDaFase = 1;
        acertosNaFase = 0;
        narradorLabel.setText(fase.isChefe()
                ? "Chefe final! Agora cada resposta vale muito."
                : escolher(mensagensInicioFase));
        mensagemLabel.setText("Boa sorte, " + jogador.getNome() + "!");
        rivalLabel.setText(fase.isChefe()
                ? "Rival: O chefe nao costuma perdoar erros."
                : "Rival: " + escolher(falasRivalNeutro));
        atualizarCabecalho();
        gerarNovaPergunta();
    }

    private void gerarNovaPergunta() {
        perguntaAtual = gerarPergunta(faseAtual);
        perguntaLabel.setText(perguntaAtual.getEnunciado());
        respostaField.setText("");
        respostaField.requestFocus();
        responderButton.setEnabled(true);
        personagemPanel.setEstado(PersonagemPanel.Estado.NEUTRO, escolher(falasRivalNeutro));
        if (perguntaDaFase == faseAtual.getQuantidadePerguntas()) {
            narradorLabel.setText("Falta pouco para fechar esta fase. Capriche nessa!");
        } else if (faseAtual.getAcertosNecessarios() - acertosNaFase == 1) {
            narradorLabel.setText("Mais um acerto pode deixar voce muito perto do objetivo.");
        }
        atualizarCabecalho();
    }

    private Pergunta gerarPergunta(Fase fase) {
        int numero1 = random.nextInt(fase.getLimiteNumeros()) + 1;
        int numero2 = random.nextInt(fase.getLimiteNumeros()) + 1;

        if (fase.getOperacao().getNome().equals("Divisao")) {
            numero2 = random.nextInt(fase.getLimiteNumeros()) + 1;
            int resultado = random.nextInt(fase.getLimiteNumeros()) + 1;
            numero1 = numero2 * resultado;
        }

        return new Pergunta(numero1, numero2, fase.getOperacao());
    }

    private void responder(ActionEvent event) {
        if (jogoFinalizado || !responderButton.isEnabled()) {
            return;
        }

        int resposta;
        try {
            resposta = Integer.parseInt(respostaField.getText().trim());
        } catch (NumberFormatException ex) {
            mensagemLabel.setText("Digite apenas numeros.");
            respostaField.requestFocus();
            return;
        }

        responderButton.setEnabled(false);

        if (perguntaAtual.verificarResposta(resposta)) {
            acertosNaFase++;
            jogador.adicionarPontos(faseAtual.isChefe() ? 20 : 10);
            narradorLabel.setText(escolher(mensagensAcerto));
            mensagemLabel.setText(mensagemDeProgresso());
            String falaRival = escolher(falasRivalAcerto);
            rivalLabel.setText("Rival: " + falaRival);
            personagemPanel.setEstado(PersonagemPanel.Estado.ACERTOU, falaRival);
        } else {
            narradorLabel.setText(escolher(mensagensErro));
            mensagemLabel.setText("Errado. Resposta correta: " + perguntaAtual.getRespostaCorreta());
            String falaRival = escolher(falasRivalErro);
            rivalLabel.setText("Rival: " + falaRival);
            personagemPanel.setEstado(PersonagemPanel.Estado.ERROU, falaRival);
        }

        atualizarCabecalho();
        new Timer(900, this::avancarDepoisDaResposta).start();
    }

    private void avancarDepoisDaResposta(ActionEvent event) {
        ((Timer) event.getSource()).stop();

        if (perguntaDaFase < faseAtual.getQuantidadePerguntas()) {
            perguntaDaFase++;
            gerarNovaPergunta();
            return;
        }

        finalizarFase();
    }

    private void finalizarFase() {
        if (acertosNaFase < faseAtual.getAcertosNecessarios()) {
            mostrarFinal("Fim de jogo", "Voce fez " + acertosNaFase + " acertos e terminou com "
                    + jogador.getPontuacao() + " pontos.");
            return;
        }

        if (faseAtual.isChefe()) {
            mostrarFinal("Vitoria!", jogador.getNome() + " derrotou o chefe da " + operacao.getNome()
                    + " com " + jogador.getPontuacao() + " pontos.");
            return;
        }

        nivelAtual++;

        if (nivelAtual <= 10) {
            narradorLabel.setText("Fase vencida! Falta pouco para chegar ao grande desafio.");
            mensagemLabel.setText("Fase completa! Indo para a fase " + nivelAtual + ".");
            rivalLabel.setText("Rival: Voce passou... mas eu ainda nao desisti.");
            responderButton.setEnabled(false);
            new Timer(1200, event -> {
                ((Timer) event.getSource()).stop();
                iniciarFase(fabricaFase.criarFaseNormal(nivelAtual));
            }).start();
        } else {
            narradorLabel.setText("Todas as fases foram vencidas. O chefe final apareceu!");
            mensagemLabel.setText("Voce chegou ao chefe final!");
            rivalLabel.setText("Rival: Agora quero ver voce vencer o chefe.");
            responderButton.setEnabled(false);
            new Timer(1400, event -> {
                ((Timer) event.getSource()).stop();
                iniciarFase(fabricaFase.criarFaseChefe());
            }).start();
        }
    }

    private void mostrarFinal(String titulo, String mensagem) {
        jogoFinalizado = true;
        finalTituloLabel.setText(titulo);
        finalMensagemLabel.setText(mensagem);
        cardLayout.show(conteudo, TELA_FINAL);
    }

    private void atualizarCabecalho() {
        faseLabel.setText(faseAtual.getTitulo());
        progressoLabel.setText("Pergunta " + perguntaDaFase + " de " + faseAtual.getQuantidadePerguntas()
                + " | Acertos: " + acertosNaFase + "/" + faseAtual.getAcertosNecessarios());
        pontuacaoLabel.setText("Pontos: " + jogador.getPontuacao());
    }

    private String escolher(String[] mensagens) {
        return mensagens[random.nextInt(mensagens.length)];
    }

    private String mensagemDeProgresso() {
        int faltam = faseAtual.getAcertosNecessarios() - acertosNaFase;

        if (faltam <= 0) {
            return "Objetivo da fase alcancado! Agora termine com estilo.";
        }

        if (faltam == 1) {
            return "Voce esta indo bem. Falta so 1 acerto para bater a meta.";
        }

        return "Correto! Faltam " + faltam + " acertos para alcancar o objetivo.";
    }

    private JLabel criarInfoLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(new Color(28, 45, 74));
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 216, 226)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(new Color(44, 111, 187));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        return botao;
    }

    private static class StickHeroPanel extends JPanel {

        StickHeroPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = preparar(g);
            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(224, 231, 241));
            g2.fillRoundRect(20, h - 36, w - 40, 18, 18, 18);
            desenharBoneco(g2, w / 2 - 100, h - 48, new Color(44, 111, 187), true);
            desenharBoneco(g2, w / 2 + 100, h - 48, new Color(190, 65, 75), false);

            g2.setFont(new Font("Arial", Font.BOLD, 28));
            g2.setColor(new Color(28, 45, 74));
            g2.drawString("2 + 2", w / 2 - 38, 70);
            g2.dispose();
        }
    }

    private static class PersonagemPanel extends JPanel {

        private Estado estado = Estado.NEUTRO;
        private String falaRival = "Vamos ver se voce sabe mesmo...";

        PersonagemPanel() {
            setOpaque(false);
        }

        void setEstado(Estado estado, String falaRival) {
            this.estado = estado;
            this.falaRival = falaRival;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = preparar(g);
            int w = getWidth();
            int h = getHeight();

            g2.setPaint(new GradientPaint(0, 0, new Color(220, 236, 255), 0, h, new Color(246, 249, 252)));
            g2.fillRoundRect(0, 0, w, h, 8, 8);

            g2.setColor(new Color(118, 172, 91));
            g2.fillRect(0, h - 54, w, 54);
            g2.setColor(new Color(87, 130, 69));
            g2.fillRect(0, h - 54, w, 5);

            int heroiX = estado == Estado.ACERTOU ? w / 2 - 210 : w / 2 - 250;
            int chefeX = estado == Estado.ERROU ? w / 2 + 230 : w / 2 + 250;

            desenharBoneco(g2, heroiX, h - 70, new Color(44, 111, 187), true);
            desenharBoneco(g2, chefeX, h - 70, new Color(190, 65, 75), false);
            desenharBalao(g2, heroiX - 105, 34, textoDoBalao(), new Color(44, 111, 187));
            desenharBalao(g2, chefeX - 112, 105, falaRival, new Color(190, 65, 75));

            g2.dispose();
        }

        private String textoDoBalao() {
            switch (estado) {
                case ACERTOU:
                    return "Boa!";
                case ERROU:
                    return "Tente de novo!";
                default:
                    return "Resolva a conta!";
            }
        }

        enum Estado {
            NEUTRO,
            ACERTOU,
            ERROU
        }
    }

    private static Graphics2D preparar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        return g2;
    }

    private static void desenharBoneco(Graphics2D g2, int x, int y, Color cor, boolean heroi) {
        g2.setColor(cor);
        g2.drawOval(x - 20, y - 150, 40, 40);
        g2.drawLine(x, y - 110, x, y - 50);
        g2.drawLine(x, y - 92, x - 38, y - 70);
        g2.drawLine(x, y - 92, x + 38, y - 70);
        g2.drawLine(x, y - 50, x - 36, y);
        g2.drawLine(x, y - 50, x + 36, y);

        if (heroi) {
            g2.drawLine(x + 38, y - 70, x + 68, y - 108);
            g2.drawString("+", x + 78, y - 108);
        } else {
            g2.drawLine(x - 38, y - 70, x - 68, y - 108);
            g2.drawString("?", x - 92, y - 108);
        }
    }

    private static void desenharBalao(Graphics2D g2, int x, int y, String texto, Color corBorda) {
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, 220, 58, 18, 18);
        g2.setColor(corBorda);
        g2.drawRoundRect(x, y, 220, 58, 18, 18);
        g2.setColor(new Color(28, 45, 74));
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString(limitarTexto(texto), x + 16, y + 36);
    }

    private static String limitarTexto(String texto) {
        if (texto.length() <= 25) {
            return texto;
        }

        return texto.substring(0, 22) + "...";
    }
}
