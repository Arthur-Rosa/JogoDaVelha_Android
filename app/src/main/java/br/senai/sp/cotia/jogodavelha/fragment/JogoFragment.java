package br.senai.sp.cotia.jogodavelha.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.senai.sp.cotia.jogodavelha.R;
import br.senai.sp.cotia.jogodavelha.databinding.FragmentJogoBinding;

public class JogoFragment extends Fragment {
    // variavel para acessar os elementos da View
    private FragmentJogoBinding binding;
    // vetor de botões para referenciar os botões
    private Button[] botoes;
    // matriz de string do tabuuleiro
    private String[][] tabuleiro;
    // variaveis para as bolinha
    private String simbJog1, simbJog2, simbolo;
    // variavel para indicar qual jogador inicia a partida
    private Random random;
    // variavel para controlar numero de jogadas
    private int numJogadas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // instanciando o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        // instanciar o vetor
        botoes = new Button[9];

        // instanciar o tabuleiro
        tabuleiro = new String[3][3];

        // preenche matriz com string vazia

        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        // define o simbolos dos jogadores 1 e 2
        simbJog1 = "X";
        simbJog2 = "O";

        // instanciar o random
        random = new Random();

        // sorteara quem começa o jogo
        sorteia();

        // atualiza a vez
        atualizaVez();

        // associar o vetor aos botões

        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        // associa o listener aos botões
        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }

        // retorna root do binding
        return binding.getRoot();
    }

    private void sorteia() {
        // se gerar um valor verdadeiro jogador 1 começa
        // caso contrario jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;
        } else {
            simbolo = simbJog2;
        }
    }

    private void reseta() {
        for (Button bt : botoes) {
            bt.setClickable(true);
            bt.setBackgroundColor(getResources().getColor(R.color.black));
            bt.setText("");
        }

        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        numJogadas = 0;

    }

    private void atualizaVez() {
        if (simbolo.equals(simbJog1)) {
            binding.linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
            binding.linearLayout2.setBackgroundColor(getResources().getColor(R.color.black));

            binding.text1.setTextColor(getResources().getColor(R.color.black));
            binding.text2.setTextColor(getResources().getColor(R.color.white));
            binding.placarUm.setTextColor(getResources().getColor(R.color.black));
            binding.placarDois.setTextColor(getResources().getColor(R.color.white));
        } else {
            binding.text1.setTextColor(getResources().getColor(R.color.white));
            binding.text2.setTextColor(getResources().getColor(R.color.black));
            binding.placarUm.setTextColor(getResources().getColor(R.color.white));
            binding.placarDois.setTextColor(getResources().getColor(R.color.black));

            binding.linearLayout.setBackgroundColor(getResources().getColor(R.color.black));
            binding.linearLayout2.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    private boolean venceu() {
        // verifica se venceu nas linhas
        for (int li = 0; li < 3; li++) {
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }
        // verifica se venceu nas colunas
        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }

        // verifica nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;
    }

    private View.OnClickListener listenerBotoes = btPress -> {
        // incrementa numero de jogadas
        numJogadas++;

        // obtem o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        // extrai a posição
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        // extrai linha e coluna da String posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        // preencher a posição da matriz com o simbolo da vez
        tabuleiro[linha][coluna] = simbolo;
        // faz um casting de view para button
        Button botao = (Button) btPress;
        // "seta" o simbolo do botao pressionado
        botao.setText(simbolo);
        // troca o background para branco
        botao.setBackgroundColor(Color.WHITE);
        // troca cor da letra para preto
        botao.setTextColor(Color.BLACK);
        // desabilita botao que foi jogado
        botao.setClickable(false);

        // verifica se venceu
        if (numJogadas >= 5 && venceu()) {
            // informa que houve um vencedor
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();
            // reseta
            reseta();
        } else if (numJogadas == 9) {
            // informa que deu velha
            Toast.makeText(getContext(), R.string.deuvelha, Toast.LENGTH_LONG).show();
            // reseta
            reseta();
        } else {
            // inverte o simbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;

            // atualiza vez
            atualizaVez();
        }
    };
}